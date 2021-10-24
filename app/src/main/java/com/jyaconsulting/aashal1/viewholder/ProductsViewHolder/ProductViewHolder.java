package com.jyaconsulting.aashal1.viewholder.ProductsViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyaconsulting.aashal1.R;
import com.jyaconsulting.aashal1.VerticalText;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public VerticalText ProductType;
    public TextView catagaoryId;
    public LinearLayout productLayout;
    public ProductViewHolder(View itemView) {
        super(itemView);
        productLayout=itemView.findViewById(R.id.layoutproduct);
        ProductType=itemView.findViewById(R.id.Product_Type);
        catagaoryId=itemView.findViewById(R.id.catagory_id);
    }
}
