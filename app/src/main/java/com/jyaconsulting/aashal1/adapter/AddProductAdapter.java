package com.jyaconsulting.aashal1.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jyaconsulting.aashal1.R;
import com.jyaconsulting.aashal1.model.AddProductModel;
import com.jyaconsulting.aashal1.viewholder.AddProductViewHolder;

import java.util.List;

public class AddProductAdapter extends RecyclerView.Adapter<AddProductViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<AddProductModel> data;
    private AddProductViewHolder holder;
    private AddProductModel current;
    private int global_position;
    Activity activity1;

    public AddProductAdapter(Context context, List<AddProductModel> data, Activity activity) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.activity1 = activity;
    }

    @NonNull
    @Override
    public AddProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.add_product_layout, parent, false);
        holder = new AddProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddProductViewHolder holder, int position) {
        current = data.get(position);
        String productType = current.getProductType();
        String productSubType = current.getProductSubType();
        String amount = current.getAmount();

        holder.tv_product_type.setText(productType);
        holder.tv_product_subType.setText(productSubType);
        holder.tv_product_amount.setText(amount);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
