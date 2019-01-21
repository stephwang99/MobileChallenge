package com.shopify.mobiledeveloperchallenge.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.shopify.mobiledeveloperchallenge.R;
import com.shopify.mobiledeveloperchallenge.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.CustomViewHolder>{
    private List<Product> dataList;
    private Activity context;
    public ProductAdapter(Activity context, List<Product> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        TextView txtTitle;
        TextView txtInventory;
        TextView txtCollection;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txtTitle = mView.findViewById(R.id.title);
            coverImage = mView.findViewById(R.id.coverImage);
            txtInventory = mView.findViewById(R.id.inventory);
            txtCollection = mView.findViewById(R.id.collection);
        }

    }


    @Override
    public ProductAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_layout, parent, false);
        return new ProductAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.CustomViewHolder holder, int position) {
        holder.txtTitle.setText(dataList.get(position).getTitle());
        holder.txtCollection.setText(dataList.get(position).getCollection());
        holder.txtInventory.setText("Inventory: " + dataList.get(position).getInventory());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getImg())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);

    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
