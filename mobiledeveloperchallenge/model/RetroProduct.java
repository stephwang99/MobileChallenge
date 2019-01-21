package com.shopify.mobiledeveloperchallenge.model;

import com.google.gson.annotations.SerializedName;

public class RetroProduct {

    @SerializedName("id")
    private String id;
    @SerializedName("collection_id")
    private Integer collection_id;
    @SerializedName("product_id")
    private Integer product_id;
    @SerializedName("featured")
    private boolean featured;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("position")
    private Integer position;
    @SerializedName("sort_value")
    private String sort_value;

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String image;

    public RetroProduct(String id, String title, String img){//Integer collection_id, Integer product_id, boolean featured, String created_at, String updated_at, Integer position, String sort_value) {
            this.id = id;
            this.title = title;
            this.image = img;
           /* this.collection_id = collection_id;
            this.product_id = product_id;
            this.featured = featured;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.position = position;
            this.sort_value = sort_value;*/
    }
        public String getId() {
            return id;
        }
        public String getTitle() {
            return this.title;
        }
        public String getImage(){return this.image;}

    }