package com.jyaconsulting.aashal1.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jyaconsulting.aashal1.CommunicationManager;
import com.jyaconsulting.aashal1.R;
import com.jyaconsulting.aashal1.model.OutletModel;
import com.jyaconsulting.aashal1.utilinterface.CMResponse;
import com.jyaconsulting.aashal1.viewholder.OutletViewHolder;

import org.json.JSONException;

import java.util.List;

public class OutletAdapter extends RecyclerView.Adapter<OutletViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<OutletModel> data;
    private OutletViewHolder holder;
    private OutletModel current;
    private int global_position;
    Activity activity1;
    CommunicationManager cm;
    CMResponse get_group_member_list = new CMResponse() {
        @Override
        public void consumeResponse(String response, boolean issuccess) throws JSONException {

        }
    };

    @NonNull
    @Override
    public OutletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.outlet_layout, parent, false);
        holder = new OutletViewHolder(view);
        cm = new CommunicationManager(activity1);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OutletViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
