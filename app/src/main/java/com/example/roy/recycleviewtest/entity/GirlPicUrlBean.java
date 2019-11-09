package com.example.roy.recycleviewtest.entity;

import java.io.Serializable;

public class GirlPicUrlBean implements Serializable {
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    private String picUrl;
}
