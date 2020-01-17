package main.java.economy_bot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Worker {

    private int money;
    //private boolean working;
    //private boolean trading;
    //private ItemType itemCreated;
    private Item[] items;
    private ArrayList<Deal> acceptedDeals;
    private ArrayList<Deal> rejectedDeals;
    private ItemConversionUtils.ConversionInterface conversionFunction;

    public Worker(int money, /*boolean working, boolean trading, ItemType itemCreated,*/ Item[] items, ItemConversionUtils.ConversionInterface conversionInterface) {
        this.money = money;
        //this.working = working;
        //this.trading = trading;
        //this.itemCreated = itemCreated;
        this.items = items;
        this.acceptedDeals = new ArrayList<>();
        this.rejectedDeals = new ArrayList<>();
        this.conversionFunction = conversionInterface;
    }

    public void act() {

        items = this.conversionFunction.Create(items);
        money -= 50;

    }

    public void trade(Market market) {

        // sort out pricing based on accepted and rejected deals from last iteration
        for (ItemType currentItemType : ItemType.values()) {
            List<Deal> acceptedDealsOfType = acceptedDeals
                    .stream()
                    .filter(offer -> offer.itemType.equals(currentItemType))
                    .collect(Collectors.toList());

            List<Deal> rejectedDealsOfType = rejectedDeals
                    .stream()
                    .filter(offer -> offer.itemType.equals(currentItemType))
                    .collect(Collectors.toList());

            // if the market has a surplus of items
            //      if more of this workers deals were rejected than accepted then
            //      the pricing was too high and they need to lower it
            //
            // if the market has a negative surplus of items or 0
            //      if the workers deals were accepted more than rejected, they can increase the
            //      price as there isn't enough in the market

            if (market.getAmountSurplus().get(currentItemType) != null) {

                if (market.getAmountSurplus().get(currentItemType) > 0) {

                    if (rejectedDealsOfType.size() > acceptedDealsOfType.size()) {

                        for (Item item : items) {

                            if (item.getItemType().equals(currentItemType)) {

                                int newValue = item.getPricePerUnit() - 1;

                                if (newValue < 1) {
                                    newValue = 1;
                                }

                                item.setPricePerUnit(newValue);

                            }

                        }

                    }

                } else {

                    if (rejectedDealsOfType.size() < acceptedDealsOfType.size()) {

                        for (Item item : items) {

                            if (item.getItemType().equals(currentItemType)) {

                                item.setPricePerUnit(item.getPricePerUnit() + 1);

                            }

                        }

                    }

                }

            }

        }

        // clear info of last iteration deals
        this.acceptedDeals.clear();
        this.rejectedDeals.clear();

        int summedSpending = 0;

        // puts up how much resource they want to buy and sell and at what price per unit
        for (Item item : items) {

            // if amount is less that desired amount, and money is more than 0, they want to buy
            int diffAmount = item.getIdealAmount() - item.getAmount();
            if (diffAmount > 0) {

                for (int i = 0; i < diffAmount; i++) {

                    // check buyer has enough money got all of them
                    if (money >= item.getPricePerUnit() + summedSpending) {

                        market.getPurchaseOffers().add(new Offer(this, item.getItemType(), item.getPricePerUnit()));

                        summedSpending += item.getPricePerUnit();

                    }

                }

            } else if (diffAmount < 0) {

                for (int i = 0; i < -diffAmount; i++) {

                    // check they have enough to sell
                    if (item.getAmount() > (i + 1)) {

                        market.getSellingOffers().add(new Offer(this, item.getItemType(), item.getPricePerUnit()));

                    }

                }

            }

        }

    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public ArrayList<Deal> getRejectedDeals() {
        return rejectedDeals;
    }

    /*

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public boolean isTrading() {
        return trading;
    }

    public void setTrading(boolean trading) {
        this.trading = trading;
    }

    public ItemType getItemCreated() {
        return itemCreated;
    }

    */

    public void acceptDeal(Deal deal, boolean buy) {

        acceptedDeals.add(deal);

        for (int itemIndex = 0; itemIndex < items.length; itemIndex++) {

            if (items[itemIndex].getItemType().equals(deal.itemType)) {

                if (buy) {

                    money -= deal.agreedPrice;

                    items[itemIndex].setAmount(items[itemIndex].getAmount() + 1);

                } else {

                    money += deal.agreedPrice;

                    items[itemIndex].setAmount(items[itemIndex].getAmount() - 1);

                }

            }

        }

    }

}
