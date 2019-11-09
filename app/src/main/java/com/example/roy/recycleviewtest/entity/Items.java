package com.example.roy.recycleviewtest.entity;

import org.litepal.crud.LitePalSupport;

public class Items extends LitePalSupport {
    private String itemValue;

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }
}
