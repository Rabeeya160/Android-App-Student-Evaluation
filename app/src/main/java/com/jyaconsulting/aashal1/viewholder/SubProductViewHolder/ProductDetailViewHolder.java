package com.jyaconsulting.aashal1.viewholder.SubProductViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyaconsulting.aashal1.R;

public class ProductDetailViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout detail;
    public TextView tvSubProduct,tvPacking,tvProductId,tvSubProductId,tvPackingId,tvCatagoryId,tvSubCatagoryId,tvItemPrice;
    public EditText EtQuantity;
    public ProductDetailViewHolder(View itemView) {
        super(itemView);

        detail=itemView.findViewById(R.id.detail);
        tvSubProduct=itemView.findViewById(R.id.tvSubProduct);
        tvSubProductId=itemView.findViewById(R.id.tvSubProductId);
        tvProductId=itemView.findViewById(R.id.tvProductId);
        tvPacking=itemView.findViewById(R.id.tvPacking);
        tvPackingId=itemView.findViewById(R.id.tvPackingId);
        tvCatagoryId=itemView.findViewById(R.id.tvCatagoryId);
        tvSubCatagoryId=itemView.findViewById(R.id.tvSubCatagoryId);
        EtQuantity=itemView.findViewById(R.id.EtQuantity);
        tvItemPrice=itemView.findViewById(R.id.tvItemPrice);
    }
}
