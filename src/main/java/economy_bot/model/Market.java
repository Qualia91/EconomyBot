package main.java.economy_bot.model;

import java.util.*;
import java.util.stream.Collectors;

public class Market {

    private final ArrayList<Offer> sellingOffers;
    private final ArrayList<Offer> purchaseOffers;
    private final HashMap<ItemType, Integer> amountSurplus;
    private final HashMap<ItemType, Integer> marketAverages;
    private final ArrayList<Worker> workers;


    public Market(ArrayList<Worker> workers) {

        this.sellingOffers = new ArrayList<>();
        this.purchaseOffers = new ArrayList<>();
        this.amountSurplus = new HashMap<>();
        this.marketAverages = new HashMap<>();
        this.workers = workers;

    }

    public void meetDemand() {

        if (workers.size() < 10) {

            for (Map.Entry<ItemType, Integer> surplusPair : amountSurplus.entrySet()) {

                // if there is a big need for an item, create a new worker to meet demand
                if (surplusPair.getValue() < -10) {

                    workers.add(WorkerCreatorUtilities.CREATE_WORKER(surplusPair.getKey()));

                    System.out.println("Worker added: " + workers.size());

                }

            }

        }

        workers.removeIf(worker -> worker.getMoney() < 10);

    }

    public void resolveOffers() {

        amountSurplus.clear();

        for (ItemType currentItemType : ItemType.values()) {

            ArrayList<Pair<Offer, Offer>> acceptedOffers = new ArrayList<>();

            List<Offer> sellOffers = sellingOffers
                    .stream()
                    .filter(offer -> offer.itemType.equals(currentItemType))
                    .sorted(Comparator.comparingInt(sellOfferOne -> sellOfferOne.price))
                    .collect(Collectors.toList());

            List<Offer> buyOffers = purchaseOffers
                    .stream()
                    .filter(offer -> offer.itemType.equals(currentItemType))
                    .sorted(Comparator.comparingInt(sellOfferOne -> sellOfferOne.price))
                    .collect(Collectors.toList());

            amountSurplus.put(currentItemType, sellOffers.size() - buyOffers.size());

            Iterator<Offer> sellIterator = sellOffers.iterator();
            while (sellIterator.hasNext()) {
                Offer sellOffer = sellIterator.next();
                Iterator<Offer> buyIterator = buyOffers.iterator();
                while (buyIterator.hasNext()) {
                    Offer buyOffer = buyIterator.next();
                    if (buyOffer.price >= sellOffer.price) {
                        buyIterator.remove();
                        sellIterator.remove();
                        acceptedOffers.add(new Pair<>(buyOffer, sellOffer));
                        break;
                    }
                }
            }

            // tell the accepted offer workers the good news
            for (Pair<Offer, Offer> acceptedOffer : acceptedOffers) {

                int agreedPrice = acceptedOffer.key.price;

                Deal deal = new Deal(acceptedOffer.key.itemType, agreedPrice);

                acceptedOffer.key.worker.acceptDeal(deal, true);

                acceptedOffer.value.worker.acceptDeal(deal, false);
            }

            for (Offer sellOffer : sellOffers) {
                sellOffer.worker.getRejectedDeals().add(new Deal(sellOffer.itemType, sellOffer.price));
            }

            for (Offer buyOffer : buyOffers) {
                buyOffer.worker.getRejectedDeals().add(new Deal(buyOffer.itemType, buyOffer.price));
            }

        }

        purchaseOffers.clear();
        sellingOffers.clear();

    }

    public ArrayList<Offer> getSellingOffers() {
        return sellingOffers;
    }

    public ArrayList<Offer> getPurchaseOffers() {
        return purchaseOffers;
    }

    public void calculateMarketAverages() {

        for (ItemType currentType : ItemType.values()) {

            int sumValue = 0;
            int numOfValues = 0;

            for (Worker worker : workers) {

                for (Item item : worker.getItems()) {

                    if (item.getItemType().equals(currentType)) {

                        sumValue += item.getPricePerUnit();
                        numOfValues++;

                    }

                }

            }

            if (numOfValues == 0) {
                marketAverages.put(currentType, 1);
            } else {
                marketAverages.put(currentType, sumValue / numOfValues);
            }

        }

    }

    public Map<ItemType, Integer> getMarketAverages() {
        return marketAverages;
    }

    public HashMap<ItemType, Integer> getAmountSurplus() {
        return amountSurplus;
    }
}
