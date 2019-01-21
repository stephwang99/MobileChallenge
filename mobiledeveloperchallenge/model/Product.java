package com.shopify.mobiledeveloperchallenge.model;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("inventory")
    private int inventory;
    @SerializedName("img")
    private String img;
    @SerializedName("collection")
    private String collection;

    public Product(String id, String title, int inventory, String img, String collection){
        this.id = id;
        this.title = title;
        this.inventory = inventory;
        this.img = img;
        this.collection = collection;
    }

    public String getTitle(){
        return this.title;
    }
    public String getCollection(){
        return this.collection;
    }
    public String getInventory(){
        return this.inventory + "";
    }
    public String getImg(){
        return this.img;
    }

}
