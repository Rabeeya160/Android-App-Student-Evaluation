package com.jyaconsulting.aashal1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.jyaconsulting.aashal1.adapter.ModifyOrderAdapter;
import com.jyaconsulting.aashal1.database.AppDataBase;

public class ModifyOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Activity activity;
    private RecyclerView recycler_modify_order;
    private Button AddMore, SaveChanges;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_order);
        context = ModifyOrderActivity.this;
        activity=this;
        linkViews();
    }

    private void linkViews() {
        recycler_modify_order = findViewById(R.id.recyler_modify_order);
        AddMore = findViewById(R.id.btn_order_add);
        SaveChanges = findViewById(R.id.btn_save_changes);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(recycler_modify_order.getContext(), 1, GridLayoutManager.VERTICAL, false);
        recycler_modify_order.setLayoutManager(gridLayoutManager);
        AppDataBase db = new AppDataBase(context, "");
        db.open();
       String outletId= Utils.getPreferences("pjpID",context);
        ModifyOrderAdapter adapter = new ModifyOrderAdapter(context, db.getAllOrder(outletId),activity);
        db.close();
        recycler_modify_order.setAdapter(adapter);


        AddMore.setOnClickListener(this);
        SaveChanges.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        AppDataBase db;
        switch (view.getId()) {
            case R.id.btn_save_changes:
                intent = new Intent(context, ReviewOrderActivity.class);
                Utils.savePreferences("GTotal", "", context);
                db = new AppDataBase(context, "");
                db.open();
                db.delPromo();
                db.close();
                startActivity(intent);
                break;
            case R.id.btn_order_add:
                intent = new Intent(context, OrderBookingActivity.class);
                intent.putExtra("selectedProduct", "null");
                Utils.savePreferences("GTotal", "", context);
                db = new AppDataBase(context, "");
                db.open();
                db.delPromo();
                db.close();
                startActivity(intent);
        }

    }
}
