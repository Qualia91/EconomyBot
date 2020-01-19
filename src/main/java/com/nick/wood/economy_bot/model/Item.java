package com.nick.wood.economy_bot.model;

public class Item {

    private ItemType itemType;
    private int amount;
    private int pricePerUnit;
    private int idealAmount;

    public Item(ItemType itemType, int amount, int pricePerUnit, int idealAmount) {
        this.itemType = itemType;
        this.amount = amount;
        this.pricePerUnit = pricePerUnit;
        this.idealAmount = idealAmount;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(int pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public int getIdealAmount() {
        return idealAmount;
    }

    public void setIdealAmount(int idealAmount) {
        this.idealAmount = idealAmount;
    }
}
