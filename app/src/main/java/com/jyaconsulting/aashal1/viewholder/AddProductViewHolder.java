package com.jyaconsulting.aashal1.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jyaconsulting.aashal1.R;

public class AddProductViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_product_amount,tv_product_type,tv_product_subType;
    public AddProductViewHolder(View itemView) {
        super(itemView);
        tv_product_amount=itemView.findViewById(R.id.product_amount);
        tv_product_type=itemView.findViewById(R.id.tv_product_type);
        tv_product_subType=itemView.findViewById(R.id.product_subType);
    }
}
