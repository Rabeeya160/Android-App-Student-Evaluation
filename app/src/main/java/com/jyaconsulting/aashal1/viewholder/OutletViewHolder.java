package com.jyaconsulting.aashal1.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyaconsulting.aashal1.R;

public class OutletViewHolder extends RecyclerView.ViewHolder {

    public TextView outletName, outletAddress, outletPhone, outletOwner;
    public LinearLayout detailLayout;

    public OutletViewHolder(View itemView) {
        super(itemView);

        outletName = itemView.findViewById(R.id.tv_outlet_name);
        outletAddress = itemView.findViewById(R.id.tv_outlet_address);
        outletPhone = itemView.findViewById(R.id.tv_outlet_phone);
        outletOwner = itemView.findViewById(R.id.tv_owner_name);
        detailLayout = itemView.findViewById(R.id.layout_outletDetail);
    }
}
