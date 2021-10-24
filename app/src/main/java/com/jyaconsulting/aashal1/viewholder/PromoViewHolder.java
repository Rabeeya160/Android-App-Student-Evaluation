package com.jyaconsulting.aashal1.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jyaconsulting.aashal1.R;

public class PromoViewHolder extends RecyclerView.ViewHolder {
    public TextView PromoQty,PromoProduct;
    public PromoViewHolder(View itemView) {
        super(itemView);

        PromoQty=itemView.findViewById(R.id.promoQty);
        PromoProduct=itemView.findViewById(R.id.promoSKU);
    }
}
