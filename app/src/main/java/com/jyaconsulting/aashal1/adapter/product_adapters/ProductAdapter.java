package com.jyaconsulting.aashal1.adapter.product_adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jyaconsulting.aashal1.OrderBookingActivity;
import com.jyaconsulting.aashal1.R;
import com.jyaconsulting.aashal1.model.ProductModel;
import com.jyaconsulting.aashal1.viewholder.ProductsViewHolder.ProductViewHolder;

import java.io.Serializable;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> implements Serializable {

    private Context context;
    private LayoutInflater inflater;
    private List<ProductModel> data;
    private ProductViewHolder holder;
    private ProductModel current;
    private String Product, cata_id;
    private int global_position;

    public ProductAdapter(Context context, List<ProductModel> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*This Method inflate our adapter Layout*/
        View view = inflater.inflate(R.layout.catagory_layout, parent, false);
        holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        /*This method will populate our recylerView with data also it will show position of each item*/
        /*access all your ViewHolder widget here */
//        this.position = position;
        current = data.get(position);
        cata_id = current.getProductID();
        Product = current.getProduct();
        holder.ProductType.setText(Product);
        holder.catagaoryId.setText(cata_id);

        holder.ProductType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderBookingActivity.class);
                intent.putExtra("selectedProduct", holder.catagaoryId.getText().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
