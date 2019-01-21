package com.shopify.mobiledeveloperchallenge;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shopify.mobiledeveloperchallenge.adapter.CustomAdapter;
import com.shopify.mobiledeveloperchallenge.model.RetroProduct;
import com.shopify.mobiledeveloperchallenge.network.GetDataService;
import com.shopify.mobiledeveloperchallenge.network.RetrofitClientInstance;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    TextView txt_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_title = findViewById(R.id.title);
        txt_title.setBackgroundColor(Color.parseColor("#666666"));
        txt_title.setTextColor(Color.parseColor("#f2f7fa"));
        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> temp = new HashMap<>();
        temp.put("page", "1");
        temp.put("access_token", "c32313df0d0ef512ca64d5b336a0d7c6");
        Call<Object> call = service.getAllCollections("custom_collections.json", temp);

        call.enqueue(new Callback<Object>() {
            public void onResponse(Call<Object> call, Response<Object> response) {
                generateDataList(response.body());
            }
            public void onFailure(Call<Object> call, Throwable t) {
                //Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println("Error: " + t);
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(Object obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        try{
            JSONObject jsonObject = new JSONObject(json);
            System.out.println("hiii" + jsonObject.toString());

            ArrayList<RetroProduct> list = new ArrayList<>();
            for(int i = 0; i < jsonObject.getJSONArray("custom_collections").length(); i++){
                JSONObject temp = new JSONObject(jsonObject.getJSONArray("custom_collections").get(i).toString());
                Object img = temp.get("image");
                JSONObject jsonObject_temp = new JSONObject(img.toString());
                list.add(new RetroProduct(temp.get("id").toString(), temp.get("title").toString(), jsonObject_temp.get("src").toString()));
                //System.out.println(list.get(i));
            }
            recyclerView = findViewById(R.id.customRecyclerView);
            adapter = new CustomAdapter(MainActivity.this, list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        }
        catch (Throwable t){
            System.out.println("Error: "  + t);
        }


    }

}