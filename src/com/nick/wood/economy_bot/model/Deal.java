package com.nick.wood.economy_bot.model;

public class Deal {

    ItemType itemType;
    int agreedPrice;

    public Deal(ItemType itemType, int agreedPrice) {
        this.itemType = itemType;
        this.agreedPrice = agreedPrice;
    }

}
