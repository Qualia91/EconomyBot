package com.nick.wood.economy_bot.model;

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

    public Economy() {

        workers = WorkerCreatorUtilities.CREATE_WORKERS(10);

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

    }

}
