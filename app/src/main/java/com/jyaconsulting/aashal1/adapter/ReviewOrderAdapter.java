package com.jyaconsulting.aashal1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jyaconsulting.aashal1.R;
import com.jyaconsulting.aashal1.Utils;
import com.jyaconsulting.aashal1.model.OrderModel;
import com.jyaconsulting.aashal1.viewholder.ReviewOrderViewHolder;

import java.util.List;

import static com.jyaconsulting.aashal1.ReviewOrderActivity.tv_total_amount;

public class ReviewOrderAdapter extends RecyclerView.Adapter<ReviewOrderViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<OrderModel> data;
    private ReviewOrderViewHolder holder;
    private OrderModel current;
    private String SKU, Quantity, Amount;
    private int global_positio;
    double gTotal = 0;

    public ReviewOrderAdapter(Context context, List<OrderModel> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;

        for (int i = 0; i < data.size(); i++) {
            current = data.get(i);
            gTotal = gTotal + Double.parseDouble(current.getOrderProductQuantity()) * Double.parseDouble(current.getOrderPrice());
        }
        if (tv_total_amount != null) {
            tv_total_amount.setText(String.valueOf(gTotal));
        }
    }

    @NonNull
    @Override
    public ReviewOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*This Method inflate our adapter Layout*/
        View view = inflater.inflate(R.layout.review_order_layout, parent, false);
        holder = new ReviewOrderViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewOrderViewHolder holder, int position) {
        /*This method will populate our recylerView with data also it will show position of each item*/
        /*access all your ViewHolder widget here */
//        this.position = position;
        current = data.get(position);
        SKU = current.getItem();
        Quantity = current.getOrderProductQuantity();
        Amount = current.getOrderPrice();

//        gTotal =gTotal+Double.parseDouble(Quantity)*Double.parseDouble(Amount);
//        Utils.savePreferences("GTotal",String.valueOf(gTotal),context);
//        tv_total_amount.setText(String.valueOf(gTotal));
        holder.SKU.setText(SKU);
        holder.quantity.setText(Quantity);
        holder.skuAmount.setText(Amount);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
