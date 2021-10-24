package com.jyaconsulting.aashal1.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jyaconsulting.aashal1.OrderBookingActivity;
import com.jyaconsulting.aashal1.R;
import com.jyaconsulting.aashal1.Utils;
import com.jyaconsulting.aashal1.database.AppDataBase;
import com.jyaconsulting.aashal1.model.OrderModel;
import com.jyaconsulting.aashal1.viewholder.ModifyOrderViewHolder;

import java.util.List;

//import com.jyaconsulting.aashal1.viewholder.ReviewOrderViewHolder;

public class ModifyOrderAdapter extends RecyclerView.Adapter<ModifyOrderViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<OrderModel> data;
    private ModifyOrderViewHolder holder;
    private OrderModel current;
    private String SKU, Quantity, Amount, Id;
    private int global_position;
    Activity activity;

    public ModifyOrderAdapter(Context context, List<OrderModel> data, Activity activity) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ModifyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*This Method inflate our adapter Layout*/
        View view = inflater.inflate(R.layout.modify_order_layout, parent, false);
        holder = new ModifyOrderViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ModifyOrderViewHolder holder, int position) {
        /*This method will populate our recylerView with data also it will show position of each item*/
        /*access all your ViewHolder widget here */
//        this.position = position;
        final int Position = position;
        current = data.get(position);
        SKU = current.getItem();
        Quantity = current.getOrderProductQuantity();
        Id = current.getItemId();
//        Amount=current.getOrderPrice();

        holder.SKU.setText(SKU);
        holder.quantity.setText(Quantity);
        holder.Id.setText(Id);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(activity);
                dialog.setTitle("Delete Order?");
                dialog.setMessage("Are you sure you want to delete this Order?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        String skuID = holder.Id.getText().toString();
                        AppDataBase db = new AppDataBase(context, "");
                        db.open();
                        db.delSelectedOrder(skuID);
                        if (Position > 0) {
                            data.remove(Position);
                            notifyItemRemoved(Position);
                            String cart = Utils.getPreferences("cartItem", context);
                            int cartCount = Integer.parseInt(cart) - 1;
                            Utils.savePreferences("cartItem", String.valueOf(cartCount), context);
                            //this line below gives you the animation and also updates the
                            //list items after the deleted item
                            notifyItemRangeChanged(Position, getItemCount());
//                            notifyDataSetChanged();
                        } else {
                            data.remove(Position);
                            notifyItemRemoved(Position);
                            notifyItemRangeChanged(Position, getItemCount());
                            String outletId=Utils.getPreferences("pjpID",context);
                            if (db.getAllOrder(outletId).size() > 0) {
                                String cart = Utils.getPreferences("cartItem", context);
                                int cartCount = Integer.parseInt(cart) - 1;
                                Utils.savePreferences("cartItem", String.valueOf(cartCount), context);
                                db.close();
                            } else {
                                Intent intent = new Intent(context, OrderBookingActivity.class);
                                Utils.savePreferences("cartItem", "", context);
                                intent.putExtra("selectedProduct", "null");
                                context.startActivity(intent);
                                db.close();
                            }
                        }
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub
                        paramDialogInterface.dismiss();

                    }
                });
                dialog.show();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qty = holder.quantity.getText().toString();
                holder.quantity.setVisibility(View.GONE);
                holder.edit.setVisibility(View.GONE);
                holder.save.setVisibility(View.VISIBLE);
                holder.etQty.setVisibility(View.VISIBLE);
                holder.etQty.setText(qty);
            }
        });
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.edit.setVisibility(View.VISIBLE);
                holder.quantity.setVisibility(View.VISIBLE);
                holder.quantity.setText(holder.etQty.getText().toString());
                String newQty = holder.quantity.getText().toString();
                holder.etQty.setVisibility(View.GONE);
                holder.save.setVisibility(View.GONE);

                AppDataBase db = new AppDataBase(context, "");
                db.open();
                db.updateOrderQty(newQty, holder.Id.getText().toString());
                db.close();
            }
        });
//        holder.skuAmount.setText(Amount);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
