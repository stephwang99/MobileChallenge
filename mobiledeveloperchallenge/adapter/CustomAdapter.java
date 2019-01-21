package com.shopify.mobiledeveloperchallenge.adapter;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.shopify.mobiledeveloperchallenge.ProductActivity;
import com.shopify.mobiledeveloperchallenge.R;
import com.shopify.mobiledeveloperchallenge.model.RetroProduct;
import com.shopify.mobiledeveloperchallenge.network.GetDataService;
import com.shopify.mobiledeveloperchallenge.network.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<RetroProduct> dataList;
    private Activity context;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String COLLECTION = "com.example.myfirstapp.COLLECTION";
    public static final String IMAGE = "com.example.myfirstapp.IMAGE";
    public static String collection_name = "";
    public static String image_name = "";

    public CustomAdapter(Activity context, List<RetroProduct> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        Button txtTitle;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txtTitle = mView.findViewById(R.id.title);
            txtTitle.setBackgroundColor(Color.parseColor("#96bf48"));
            txtTitle.setTextColor(Color.parseColor("#f5f5f5"));

            txtTitle.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //txtTitle.setBackgroundColor(Color.parseColor("#666666"));
                    collection_name = txtTitle.getText().toString();
                    GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                    Map<String, String> temp = new HashMap<>();
                    for(int i = 0; i < dataList.size(); i++){
                        if(dataList.get(i).getTitle().equals(txtTitle.getText())){
                            temp.put("collection_id", dataList.get(i).getId());
                            image_name = dataList.get(i).getImage();
                            break;
                        }
                    }
                    temp.put("page", "1");
                    temp.put("access_token", "c32313df0d0ef512ca64d5b336a0d7c6");
                    Call<Object> call = service.getAllCollections("collects.json", temp);
                    call.enqueue(new Callback<Object>() {
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            generateDataList(response.body());
                        }
                        public void onFailure(Call<Object> call, Throwable t) {
                            System.out.println("Error: " + t);
                        }
                    });
                }
            });

        }

    }

    private void generateDataList(Object obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        try{
            JSONObject jsonObject = new JSONObject(json);
            //System.out.println(jsonObject.toString());
            //System.out.println(jsonObject.getJSONArray("collects").get(0).toString());
            // JSONObject temp = new JSONObject(jsonObject.getJSONArray("custom_collections").get(0).toString());
            // System.out.println("hiii!! "  + temp.get("id").toString());

            //ArrayList<RetroProduct> list = new ArrayList<>();
            String ids = "";
            for(int i = 0; i < jsonObject.getJSONArray("collects").length(); i++){
                JSONObject temp = new JSONObject(jsonObject.getJSONArray("collects").get(i).toString());
                //list.add(new RetroProduct(temp.get("product_id").toString()));
                //System.out.println("hii!" + list.get(i));
                ids += temp.get("product_id").toString();
                ids+= ",";
            }
            ids = ids.substring(0, ids.length() - 1);
            //System.out.println(ids);
            //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.class);
            //recyclerView.setLayoutManager(layoutManager);
            //recyclerView.setAdapter(adapter);
            //this.dataList = list;
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Map<String, String> temp = new HashMap<>();
            temp.put("ids", ids);
            temp.put("page", "1");
            temp.put("access_token", "c32313df0d0ef512ca64d5b336a0d7c6");
            Call<Object> call = service.getAllCollections("products.json", temp);
            call.enqueue(new Callback<Object>() {
                public void onResponse(Call<Object> call, Response<Object> response) {
                    //System.out.println(response.body().toString());
                    //System.out.println(context.getPackageName());
                    Intent intent = new Intent(context, ProductActivity.class);
                    Gson gson = new Gson();
                    String json = gson.toJson(response.body());
                    intent.putExtra(EXTRA_MESSAGE, json);
                    intent.putExtra(COLLECTION, collection_name);
                    intent.putExtra(IMAGE, image_name);
                    context.startActivity(intent);
                }
                public void onFailure(Call<Object> call, Throwable t) {
                    System.out.println("Error: " + t);
                }
            });

        }
        catch (Throwable t){
            System.out.println("Error: " + t);
        }


    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtTitle.setText(dataList.get(position).getTitle());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));

    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
