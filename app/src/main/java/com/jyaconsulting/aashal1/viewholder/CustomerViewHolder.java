package com.jyaconsulting.aashal1.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jyaconsulting.aashal1.R;

public class CustomerViewHolder extends RecyclerView.ViewHolder {
    public TextView customer_name,customer_address,customer_phone;
    public CustomerViewHolder(View itemView) {
        super(itemView);
        customer_name=itemView.findViewById(R.id.customer_Name);
        customer_address=itemView.findViewById(R.id.customer_address);
        customer_phone=itemView.findViewById(R.id.customer_phone);

    }
}
