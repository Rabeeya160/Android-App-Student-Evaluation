package com.jyaconsulting.aashal1.viewholder.SubProductViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jyaconsulting.aashal1.R;

public class ItemListViewHolder extends RecyclerView.ViewHolder {
    public ImageView iv_item_img;
    private int mHeight=250;
    public TextView tv_ItemName;
    public LinearLayout layout_item_view;
    public ItemListViewHolder(View itemView) {
        super(itemView);
//        iv_item_img=itemView.findViewById(R.id.item_list_layout);
//        tv_ItemName=itemView.findViewById(R.id.tv_itemName);
//        layout_item_view=itemView.findViewById(R.id.layout_item_view);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, mHeight);
        itemView.setLayoutParams(params);
    }
}
