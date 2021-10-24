package com.jyaconsulting.aashal1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jyaconsulting.aashal1.database.AppDataBase;
import com.jyaconsulting.aashal1.model.UserInfoModel;
import com.jyaconsulting.aashal1.utilinterface.CMResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectedOutletActivity extends AppCompatActivity implements View.OnClickListener {
    private Activity activity;
    private Context context;
    String status,encode;
    private Button btn_take_order, temp_close, perm_close, stock, not_interested, noCash, wharehouse;
    private TextView tv_selected_outlet, outlet_address, outlet_phone, outlet_owner;
    CommunicationManager cm;
    CMResponse sendOutletStatus = new CMResponse() {
        @Override
        public void consumeResponse(String response, boolean isSuccess) throws JSONException {
            if (isSuccess == true) {
                String id = Utils.getPreferences("pjpID", context);
                String Day = Utils.getPreferences("Day", context);
                AppDataBase db = new AppDataBase(context, "");
                db.open();
                db.updatePJPDay(status, id, Day);
                db.close();
                Toast.makeText(context, "Status Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, OutletActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_outlet);
        context = SelectedOutletActivity.this;
        activity = this;

        AppDataBase db = new AppDataBase(context, "");
        db.open();
        ArrayList<UserInfoModel> arr=db.getUserInfo();
        UserInfoModel current=arr.get(0);
        encode=current.getAccount();
        db.close();

        linkViews();
        cm = new CommunicationManager(activity);
    }

    private void linkViews() {
        btn_take_order = findViewById(R.id.btn_take_order);
        tv_selected_outlet = findViewById(R.id.tv_selected_outlet);
        outlet_address = findViewById(R.id.outlet_address);
        outlet_owner = findViewById(R.id.outlet_owner);
        outlet_phone = findViewById(R.id.outlet_phone);
        temp_close = findViewById(R.id.outlet_temp_close);
        perm_close = findViewById(R.id.outlet_perm_close);
        stock = findViewById(R.id.outlet_stock);
        not_interested = findViewById(R.id.outlet_not_intereste);
        noCash = findViewById(R.id.outlet_no_cash);
        wharehouse = findViewById(R.id.outlet_wharehouse);

        //Set Text in TextViews
        outlet_phone.setText(Utils.getPreferences("OutletPhone", context));
        tv_selected_outlet.setText(Utils.getPreferences("outletName", context));
        outlet_address.setText(Utils.getPreferences("OutletAddress", context));
        outlet_owner.setText(Utils.getPreferences("OwnerName", context));

        temp_close.setOnClickListener(this);
        perm_close.setOnClickListener(this);
        stock.setOnClickListener(this);
        not_interested.setOnClickListener(this);
        noCash.setOnClickListener(this);
        wharehouse.setOnClickListener(this);
        btn_take_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        String link,encode;
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_take_order:
                takeOrder();
                break;
            case R.id.outlet_not_intereste:
                sendReason("Not interested in our products");
                break;
            case R.id.outlet_no_cash:
                sendReason("No Cash");
                break;
            case R.id.outlet_perm_close:
                sendReason("Outlet Permanently Closed");
                break;
            case R.id.outlet_temp_close:
                sendReason("Outlet Temporarily Closed");
                break;
            case R.id.outlet_stock:
                sendReason("Sufficient Stock");
                break;
            case R.id.outlet_wharehouse:
                sendReason("Buying from Wholesale");
                break;
        }

    }

    private void takeOrder() {
        /*Add outletId into local-db for offline sync. This id is used in HomeActivity
         * While offline syncing the order from the local db to live Server*/
        AppDataBase dataBase = new AppDataBase(context, "");
        dataBase.open();
        dataBase.insertOutlet(Utils.getPreferences("pjpID", context));
        dataBase.close();
        if (Utils.getPreferences("SaleType", context).equalsIgnoreCase("Pre")) {
            Intent intent = new Intent(context, MerchandizingActivity.class);
            startActivity(intent);
        } else if (Utils.getPreferences("SaleType", context).equalsIgnoreCase("Spot")) {
            Intent intent = new Intent(context, OrderBookingActivity.class);
            intent.putExtra("selectedProduct", "null");
            startActivity(intent);
        }
    }

    private void sendReason(String Reason) {
        try {
            status = Reason;
            JSONObject object;
            object = new JSONObject();
            String link = Utils.getPreferences("link", context);
//            String encode = Utils.getPreferences("encode", context);
            object.put("OutletId", Integer.parseInt(Utils.getPreferences("pjpID", context)));
            object.put("Reason", Reason);
            String authorizationString = encode;
            cm.postJSONObjectRequest(link, "api/MobileTransaction/PostOutletStatus", authorizationString, object, sendOutletStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(context, OutletActivity.class);
        startActivity(a);
    }
}
