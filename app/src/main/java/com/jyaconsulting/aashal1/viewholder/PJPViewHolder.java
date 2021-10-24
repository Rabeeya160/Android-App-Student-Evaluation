package com.jyaconsulting.aashal1.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyaconsulting.aashal1.R;

public class PJPViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout outletDetail;
    public TextView outletName, outletPhone, outletAddress, outletOwner, pjpID, pjpStatus, startDate, alterNate;

    public PJPViewHolder(View itemView) {
        super(itemView);

        outletDetail = itemView.findViewById(R.id.layout_outletDetail);
        outletName = itemView.findViewById(R.id.tv_outlet_name);
        outletAddress = itemView.findViewById(R.id.tv_outlet_address);
        outletOwner = itemView.findViewById(R.id.tv_owner_name);
        outletPhone = itemView.findViewById(R.id.tv_outlet_phone);
        pjpID = itemView.findViewById(R.id.pjpID);
        pjpStatus = itemView.findViewById(R.id.tv_pjp_status);
        startDate = itemView.findViewById(R.id.tv_pjp_startDate);
        alterNate = itemView.findViewById(R.id.tv_pjp_AlterNate);
    }
}
