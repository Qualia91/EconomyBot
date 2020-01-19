package com.nick.wood.economy_bot.model;

public class Pair<T, U> {

    public T key;
    public U value;

    public Pair(T key, U value) {
        this.key = key;
        this.value = value;
    }
}
