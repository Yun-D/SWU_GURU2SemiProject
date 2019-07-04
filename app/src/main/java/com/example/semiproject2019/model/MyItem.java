package com.example.semiproject2019.model;

import java.io.Serializable;

public class MyItem implements Serializable {
    private int imgId;
    private String title;
    private String subTitle;
    private String price;

    public MyItem() {}

    public MyItem(int imgId, String title, String subTitle, String price) {
        this.imgId = imgId;
        this.title = title;
        this.subTitle = subTitle;
        this.price = price;
    }

    @Override
    public String toString() {
        return "MyItem{" +
                "imgId=" + imgId +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
