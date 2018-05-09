package com.hefeibus.www.hefeibus.entity;

public class Item {
    private String name;
    private int isLine;

    public Item(String name, int isLine) {
        this.name = name;
        this.isLine = isLine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsLine() {
        return isLine;
    }

    public void setIsLine(int isLine) {
        this.isLine = isLine;
    }
}
