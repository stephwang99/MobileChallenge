package com.shopify.mobiledeveloperchallenge.network;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GetDataService {
    @GET("/admin/{page}")
    //@GET("/admin/custom_collections.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6")
    //Call<Object> getAllCollections();
    Call<Object> getAllCollections(@Path("page") String address, @QueryMap Map<String, String> options);
}