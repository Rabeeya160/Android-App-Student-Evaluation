package com.jyaconsulting.aashal1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jyaconsulting.aashal1.R;
import com.jyaconsulting.aashal1.model.PromoModel;
import com.jyaconsulting.aashal1.viewholder.PromoViewHolder;

import java.util.List;

public class PromoAdapter extends RecyclerView.Adapter<PromoViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<PromoModel> data;
    private PromoViewHolder holder;
    private PromoModel current;
    private String Product,Qty;
    private int global_position;

    public PromoAdapter(Context context, List<PromoModel> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }
    @NonNull
    @Override
    public PromoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*This Method inflate our adapter Layout*/
        View view = inflater.inflate(R.layout.promo_layout, parent, false);
        holder = new PromoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PromoViewHolder holder, int position) {
        /*This method will populate our recylerView with data also it will show position of each item*/
        /*access all your ViewHolder widget here */
//        this.position = position;
        current=data.get(position);
        Qty=current.getPromoQuantity();
        Product=current.getPromoProduct();

        holder.PromoProduct.setText(Product);
        holder.PromoQty.setText(Qty);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
