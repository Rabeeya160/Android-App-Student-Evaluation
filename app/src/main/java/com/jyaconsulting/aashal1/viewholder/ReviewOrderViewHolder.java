package com.jyaconsulting.aashal1.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jyaconsulting.aashal1.R;

public class ReviewOrderViewHolder extends RecyclerView.ViewHolder {
    public TextView quantity,SKU,skuAmount;
    public ReviewOrderViewHolder(View itemView) {
        super(itemView);

        SKU=itemView.findViewById(R.id.tvSKU);
        quantity=itemView.findViewById(R.id.SKU_quantity);
        skuAmount=itemView.findViewById(R.id.SKU_Amount);

    }
}
