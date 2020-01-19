package com.nick.wood.economy_bot.model;

public class ItemConversionUtils {

    private static final int TOOL_BREAK_PROB = 50;
    private static final int TABLE_BREAK_PROB = 30;

    public interface ConversionInterface {
        Item[] Create(Item[] items);
    }

    public static Item[] CREATE_TABLE(Item[] items) {

        boolean hasWood = false;
        boolean hasTools = false;

        for (Item item : items) {

            if (item.getItemType().equals(ItemType.WOOD)) {

                if (item.getAmount() >= 4) {

                    hasWood = true;

                }

            }

            if (item.getItemType().equals(ItemType.TOOLS)) {

                if (item.getAmount() >= 1) {

                    hasTools = true;

                }

            }

        }

        if (hasTools && hasWood) {

            for (Item item : items) {

                if (item.getItemType().equals(ItemType.WOOD)) {

                    item.setAmount(item.getAmount() - 4);

                }

                if (item.getItemType().equals(ItemType.TOOLS)) {

                    int percent = Economy.RAND.nextInt(100 + 1);

                    if (percent < TOOL_BREAK_PROB) {

                        item.setAmount(item.getAmount() - 1);

                    }

                }

                if (item.getItemType().equals(ItemType.TABLE)) {

                    item.setAmount(item.getAmount() + 1);

                }

            }

        }

        return items;

    }

    public static Item[] CREATE_TOOLS(Item[] items) {

        boolean hasWood = false;
        boolean hasMetal = false;
        boolean hasTable = false;

        for (Item item : items) {

            if (item.getItemType().equals(ItemType.WOOD)) {

                if (item.getAmount() >= 4) {

                    hasWood = true;

                }

            }

            if (item.getItemType().equals(ItemType.TABLE)) {

                if (item.getAmount() >= 1) {

                    hasTable = true;

                }

            }

            if (item.getItemType().equals(ItemType.METAL)) {

                if (item.getAmount() >= 4) {

                    hasMetal = true;

                }

            }

        }

        if (hasMetal && hasWood && hasTable) {

            for (Item item : items) {

                if (item.getItemType().equals(ItemType.WOOD)) {

                    item.setAmount(item.getAmount() - 4);

                }

                if (item.getItemType().equals(ItemType.METAL)) {

                    item.setAmount(item.getAmount() - 4);

                }

                if (item.getItemType().equals(ItemType.TABLE)) {

                    int percent = Economy.RAND.nextInt(100 + 1);

                    if (percent < TABLE_BREAK_PROB) {

                        item.setAmount(item.getAmount() - 1);

                    }

                }

                if (item.getItemType().equals(ItemType.TOOLS)) {

                    item.setAmount(item.getAmount() + 1);

                }

            }

        }

        return items;

    }

    public static Item[] CREATE_WOOD(Item[] items) {

        boolean hasTool = false;

        for (Item item : items) {

            if (item.getItemType().equals(ItemType.TOOLS)) {

                if (item.getAmount() >= 1) {

                    hasTool = true;

                }

            }

        }

        if (hasTool) {

            for (Item item : items) {

                if (item.getItemType().equals(ItemType.TOOLS)) {

                    int percent = Economy.RAND.nextInt(100 + 1);

                    if (percent < TOOL_BREAK_PROB) {

                        item.setAmount(item.getAmount() - 1);

                    }

                }

                if (item.getItemType().equals(ItemType.WOOD)) {

                    item.setAmount(item.getAmount() + 20);

                }

            }

        }

        return items;

    }

    public static Item[] CREATE_METAL(Item[] items) {

        boolean hasTool = false;

        for (Item item : items) {

            if (item.getItemType().equals(ItemType.TOOLS)) {

                if (item.getAmount() >= 1) {

                    hasTool = true;

                }

            }

        }

        if (hasTool) {

            for (Item item : items) {

                if (item.getItemType().equals(ItemType.TOOLS)) {

                    int percent = Economy.RAND.nextInt(100 + 1);

                    if (percent < TOOL_BREAK_PROB) {

                        item.setAmount(item.getAmount() - 1);

                    }

                }

                if (item.getItemType().equals(ItemType.METAL)) {

                    item.setAmount(item.getAmount() + 10);

                }

            }

        }

        return items;

    }

}
