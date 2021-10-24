package com.jyaconsulting.aashal1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jyaconsulting.aashal1.R;
import com.jyaconsulting.aashal1.model.PJPModel;
import com.jyaconsulting.aashal1.viewholder.CustomerViewHolder;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<PJPModel> data;
    private CustomerViewHolder holder;
    private PJPModel current;
    private String customerName,Address,phone;
    private int global_position;

    public CustomerAdapter(Context context, List<PJPModel> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }
    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*This Method inflate our adapter Layout*/
        View view = inflater.inflate(R.layout.customer_layout, parent, false);
        holder = new CustomerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        current=data.get(position);
        customerName=current.getOuletName();
        phone=current.getPhone();
        Address=current.getAddress();

        holder.customer_name.setText(customerName);
        holder.customer_address.setText(Address);
        holder.customer_phone.setText(phone);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
