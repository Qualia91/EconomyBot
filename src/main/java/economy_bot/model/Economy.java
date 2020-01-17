package main.java.economy_bot.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Economy implements Runnable {

    public static final Random RAND = new Random();

    private final double FPS = 1;

    private final ArrayList<Worker> workers;

    private final Market market;

    private final Environment environment;

    public Economy() {

        try {
            FileWriter pw = new FileWriter("C:\\Users\\nickw\\Documents\\data.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        workers = WorkerCreatorUtilities.CREATE_WORKERS(10);

        environment = new Environment();

//        workers = new ArrayList<>();
//
//        Item[] itemsOne = new Item[4];
//        itemsOne[0] = new Item(ItemType.WOOD, 20, 8, 5);
//        itemsOne[1] = new Item(ItemType.TABLE, 0, 20, 0);
//        itemsOne[2] = new Item(ItemType.TOOLS, 0, 8, 20);
//        itemsOne[3] = new Item(ItemType.METAL, 0, 8, 20);
//        Item[] itemsTwo = new Item[4];
//        itemsTwo[0] = new Item(ItemType.WOOD, 100, 42, 10);
//        itemsTwo[1] = new Item(ItemType.TABLE, 0, 20, 20);
//        itemsTwo[2] = new Item(ItemType.TOOLS, 0, 28, 0);
//        itemsTwo[3] = new Item(ItemType.METAL, 100, 28, 10);
//
//        workers.add(new Worker(100, itemsOne, ItemConversionUtils::CREATE_TABLE));
//        workers.add(new Worker(100, itemsTwo, ItemConversionUtils::CREATE_TOOLS));

        market = new Market(workers);

    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();

        double nanoSecondConversion = 1000000000 / FPS;

        double deltaSeconds = 0.0;

        while (true) {

            long now = System.nanoTime();

            deltaSeconds += (now - lastTime) * nanoSecondConversion;

            while (deltaSeconds >= 1) {

                update();

                deltaSeconds = 0.0;

            }

            lastTime = now;

        }
    }

    private void update() {

        market.calculateMarketAverages();

        for (Worker worker : workers) {

            worker.act();

        }

        for (Worker worker : workers) {

            worker.trade(market);

        }

        market.resolveOffers();

        market.meetDemand();

        try {
            writeEconomyState();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeEconomyState() throws IOException {

        FileWriter pw = new FileWriter("C:\\Users\\nickw\\Documents\\data.csv",true);

        for (Map.Entry<ItemType, Integer> itemTypeIntegerEntry : market.getMarketAverages().entrySet()) {

            pw.append(String.valueOf(itemTypeIntegerEntry.getValue()));
            pw.append(",");

        }

        pw.append("\n");
        pw.flush();
        pw.close();

    }

}
