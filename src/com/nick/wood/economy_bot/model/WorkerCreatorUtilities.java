package com.nick.wood.economy_bot.model;

import java.util.ArrayList;

import static com.nick.wood.economy_bot.model.Economy.RAND;
import static com.nick.wood.economy_bot.model.ItemType.*;

public class WorkerCreatorUtilities {

    public static ArrayList<Worker> CREATE_WORKERS(int number) {

        ArrayList<Worker> workers = new ArrayList<>();

        for (int i = 0; i < number; i++) {

            switch (RAND.nextInt(4)) {
                case 0: // TABLE BUILDER
                    workers.add(CREATE_TABLE_BUILDER());
                    break;
                case 1: // TOOL BUILDER
                    workers.add(CREATE_TOOL_BUILDER());
                    break;
                case 2: // WOOD CUTTER
                    workers.add(CREATE_WOOD_CUTTER());
                    break;
                case 3: // METAL MINER
                    workers.add(CREATE_METAL_MINER());
                    break;
            }


        }

        return workers;

    }

    private static Worker CREATE_TABLE_BUILDER() {

        Item[] items = new Item[4];
        items[0] = new Item(WOOD, 0, RAND.nextInt(45) + 5, RAND.nextInt(10) + 10);
        items[1] = new Item(TABLE, 0, RAND.nextInt(45) + 5, 0);
        items[2] = new Item(TOOLS, 0, RAND.nextInt(45) + 5, RAND.nextInt(10) + 10);
        items[3] = new Item(METAL, 0, RAND.nextInt(45) + 5, 0);
        return new Worker(RAND.nextInt(500) + 1000, items, ItemConversionUtils::CREATE_TABLE);

    }

    private static Worker CREATE_TOOL_BUILDER() {

        Item[] items = new Item[4];
        items[0] = new Item(WOOD, 0, RAND.nextInt(45) + 5, RAND.nextInt(10) + 10);
        items[1] = new Item(TABLE, 0, RAND.nextInt(45) + 5, RAND.nextInt(10) + 10);
        items[2] = new Item(TOOLS, 0, RAND.nextInt(45) + 5, 0);
        items[3] = new Item(METAL, 0, RAND.nextInt(45) + 5, RAND.nextInt(10) + 10);
        return new Worker(RAND.nextInt(500) + 1000, items, ItemConversionUtils::CREATE_TOOLS);

    }

    private static Worker CREATE_WOOD_CUTTER() {

        Item[] items = new Item[4];
        items[0] = new Item(WOOD, 0, RAND.nextInt(45) + 5, 0);
        items[1] = new Item(TABLE, 0, RAND.nextInt(45) + 5, RAND.nextInt(10) + 10);
        items[2] = new Item(TOOLS, RAND.nextInt(10) + 5, RAND.nextInt(45) + 5, RAND.nextInt(10) + 10);
        items[3] = new Item(METAL, 0, RAND.nextInt(45) + 5, 0);
        return new Worker(RAND.nextInt(500) + 1000, items, ItemConversionUtils::CREATE_WOOD);

    }

    private static Worker CREATE_METAL_MINER() {

        Item[] items = new Item[4];
        items[0] = new Item(WOOD, 0, RAND.nextInt(45) + 5, 0);
        items[1] = new Item(TABLE, 0, RAND.nextInt(45) + 5, RAND.nextInt(10) + 10);
        items[2] = new Item(TOOLS, RAND.nextInt(10) + 5, RAND.nextInt(45) + 5, RAND.nextInt(10) + 10);
        items[3] = new Item(METAL, 0, RAND.nextInt(45) + 5, 0);
        return new Worker(RAND.nextInt(500) + 1000, items, ItemConversionUtils::CREATE_METAL);

    }

    public static Worker CREATE_WORKER(ItemType itemType) {

        Worker newWorker = null;

        switch (itemType) {

            case WOOD:
                newWorker = CREATE_WOOD_CUTTER();
                break;
            case TABLE:
                newWorker = CREATE_TABLE_BUILDER();
                break;
            case TOOLS:
                newWorker = CREATE_TOOL_BUILDER();
                break;
            case METAL:
                newWorker = CREATE_METAL_MINER();
                break;
            default:
                break;
        }

        return newWorker;

    }
}
