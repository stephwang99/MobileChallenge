package com.shopify.mobiledeveloperchallenge;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shopify.mobiledeveloperchallenge.adapter.CustomAdapter;
import com.shopify.mobiledeveloperchallenge.adapter.ProductAdapter;
import com.shopify.mobiledeveloperchallenge.model.Product;
import com.shopify.mobiledeveloperchallenge.model.RetroProduct;

import org.json.JSONObject;

import java.util.ArrayList;


public class ProductActivity extends AppCompatActivity {

    private ProductAdapter adapter;
    private RecyclerView recyclerView;
    TextView txt_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        txt_title = findViewById(R.id.title);
        txt_title.setBackgroundColor(Color.parseColor("#666666"));
        txt_title.setTextColor(Color.parseColor("#f2f7fa"));

        Intent intent = getIntent();
        String message = intent.getStringExtra(CustomAdapter.EXTRA_MESSAGE);
        String collection = intent.getStringExtra(CustomAdapter.COLLECTION);
        String image = intent.getStringExtra(CustomAdapter.IMAGE);

        try{

            JSONObject jsonObject = new JSONObject(message);
            ArrayList<Product> list = new ArrayList<>();
            for(int i = 0; i < jsonObject.getJSONArray("products").length(); i++){
                JSONObject temp = new JSONObject(jsonObject.getJSONArray("products").get(i).toString());
                int inventory_count = 0;
                for(int j = 0; j < temp.getJSONArray("variants").length(); j++){
                    JSONObject variant = new JSONObject(temp.getJSONArray("variants").get(j).toString());
                    inventory_count += Integer.parseInt(variant.get("inventory_quantity").toString());
                }

                list.add(new Product(temp.get("id").toString(), temp.get("title").toString(), inventory_count, image,collection));
            }

            recyclerView = findViewById(R.id.customRecyclerView);
            adapter = new ProductAdapter(ProductActivity.this, list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
        catch (Throwable t){
            System.out.println("Error: " + t);
        }



    }
}
