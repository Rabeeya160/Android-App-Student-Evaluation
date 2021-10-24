package com.jyaconsulting.aashal1.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyaconsulting.aashal1.R;

public class ModifyOrderViewHolder extends RecyclerView.ViewHolder {
    public TextView quantity,SKU,Id;
    public EditText etQty;
    public ImageView delete,edit,save;
    public ModifyOrderViewHolder(View itemView) {
        super(itemView);

        SKU=itemView.findViewById(R.id.tvSKUModify);
        quantity=itemView.findViewById(R.id.tv_SKU_quantity);
        etQty=itemView.findViewById(R.id.et_SKU_quantity);
        delete=itemView.findViewById(R.id.delete_order);
        edit=itemView.findViewById(R.id.edit_order);
        save=itemView.findViewById(R.id.save_new);
        Id=itemView.findViewById(R.id.tvSKUId);
        //        skuAmount=itemView.findViewById(R.id.SKU_Amount);

    }
}
