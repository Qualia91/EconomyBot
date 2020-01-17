package main.java.economy_bot.model;

public class Offer {

    public Worker worker;
    public ItemType itemType;
    public int price;

    public Offer(Worker worker, ItemType itemType, int price) {
        this.worker = worker;
        this.itemType = itemType;
        this.price = price;
    }

}
