package com.jyaconsulting.aashal1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jyaconsulting.aashal1.adapter.PromoAdapter;
import com.jyaconsulting.aashal1.adapter.ReviewOrderAdapter;
import com.jyaconsulting.aashal1.database.AppDataBase;
import com.jyaconsulting.aashal1.model.UserInfoModel;
import com.jyaconsulting.aashal1.utilinterface.CMResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderSummaryActivity extends AppCompatActivity {
    private Context context;
    private Activity activity;
    private TextView tv_percentage, tv_grand_total, tv_gst, tv_wht, tv_gross_total, tv_ReferenceNumber;
    private RecyclerView summary_promo, recyler_summary;
    private PromoAdapter promoAdapter;
    private EditText etReceivedAmount;
    private Button btnReceivedAmount;
    private ReviewOrderAdapter orderAdapter;
    private String ApiRequest,encode;
    CommunicationManager cm;
    CMResponse sendOutletStatus = new CMResponse() {
        @Override
        public void consumeResponse(String response, boolean isSuccess) throws JSONException {
            if (isSuccess == true) {
                if (ApiRequest.equalsIgnoreCase("SentPayment")) {
                    Toast.makeText(context, "Order has been taken successfully", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(context, HomeActivity.class);
                    startActivity(intent);
                } else {
                    String id = Utils.getPreferences("pjpID", context);
                    String Day = Utils.getPreferences("Day", context);
                    AppDataBase db = new AppDataBase(context, "");
                    db.open();
                    db.updatePJPDay("Order Has Been Taken", id, Day);
                    db.close();
                    Toast.makeText(context, "Order has been taken successfully", Toast.LENGTH_SHORT);
                }
            } else {

                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        context = OrderSummaryActivity.this;
        activity = this;
        cm = new CommunicationManager(activity);

        AppDataBase db = new AppDataBase(context, "");
        db.open();
        ArrayList<UserInfoModel> arr=db.getUserInfo();
        UserInfoModel current=arr.get(0);
        encode=current.getAccount();
        db.close();

        linkViews();
    }

    private void sendReason() {
        try {
            ApiRequest = "Status";
            JSONObject object;
            object = new JSONObject();
            String link = Utils.getPreferences("link", context);
//            String encode = Utils.getPreferences("encode", context);
            object.put("OutletId", Integer.parseInt(Utils.getPreferences("pjpID", context)));
            object.put("Reason", "\'Order\' Has Been Taken");
            String authorizationString = encode;
            cm.postJSONObjectRequest(link, "api/MobileTransaction/PostOutletStatus", authorizationString, object, sendOutletStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void linkViews() {
        tv_grand_total = findViewById(R.id.tv_Grand_total);
        tv_percentage = findViewById(R.id.tv_percentage);
        tv_gross_total = findViewById(R.id.tv_gross_total);
        tv_gst = findViewById(R.id.tv_gst);
        tv_wht = findViewById(R.id.tv_wht);
        summary_promo = findViewById(R.id.summary_promo);
        recyler_summary = findViewById(R.id.recyler_summary);
        etReceivedAmount = findViewById(R.id.et_receivedAmount);
        btnReceivedAmount = findViewById(R.id.btn_receivedPayment);
        tv_ReferenceNumber = findViewById(R.id.tv_refrence);

        Intent intent = getIntent();

        if (Utils.getPreferences("SaleType", context).equalsIgnoreCase("Spot")) {
            tv_ReferenceNumber.setVisibility(View.GONE);
            etReceivedAmount.setVisibility(View.GONE);
            btnReceivedAmount.setVisibility(View.GONE);
            tv_ReferenceNumber.setText(intent.getStringExtra("ReferenceNumber"));
        } else if (Utils.getPreferences("SaleType", context).equalsIgnoreCase("Pre")) {
            tv_ReferenceNumber.setVisibility(View.GONE);
            etReceivedAmount.setVisibility(View.GONE);
            btnReceivedAmount.setVisibility(View.GONE);
        }
        String GrandTotal = intent.getStringExtra("Gtotal");
        double GT = Double.parseDouble(GrandTotal);
        GrandTotal = String.format("%.2f", GT);
        tv_grand_total.setText(GrandTotal);
        tv_percentage.setText(intent.getStringExtra("Discount") + "%");
        tv_gst.setText(intent.getStringExtra("GST") + "%");
        tv_wht.setText(intent.getStringExtra("WHT") + "%");
        tv_gross_total.setText(intent.getStringExtra("GrossTotal"));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(summary_promo.getContext(), 1, GridLayoutManager.VERTICAL, false);
        summary_promo.setLayoutManager(gridLayoutManager);

        AppDataBase appDataBase = new AppDataBase(context, "");
        appDataBase.open();
        String Id=Utils.getPreferences("pjpID",context);
        promoAdapter = new PromoAdapter(context, appDataBase.getAllPromo(Id));
        summary_promo.setAdapter(promoAdapter);
//        adapter.notifyDataSetChanged();
        appDataBase.close();

        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(recyler_summary.getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyler_summary.setLayoutManager(gridLayoutManager1);

        appDataBase = new AppDataBase(context, "");
        appDataBase.open();
        String outletId=Utils.getPreferences("pjpID",context);
        orderAdapter = new ReviewOrderAdapter(context, appDataBase.getAllOrder(outletId));
        recyler_summary.setAdapter(orderAdapter);
//        adapter.notifyDataSetChanged();
        appDataBase.close();
        if (!Utils.getPreferences("order", context).isEmpty()) {
            if (!Utils.getPreferences("order", context).equalsIgnoreCase("offline")) {
                appDataBase.open();
                appDataBase.delOrder(outletId);
//                appDataBase.delPromo();
                appDataBase.close();
                Utils.savePreferences("GTotal", "", context);
                Utils.savePreferences("cartItem", "0", context);
            }
            else {
                Utils.savePreferences("cartItem", "0", context);
                Utils.savePreferences("GTotal", "", context);
                String id = Utils.getPreferences("pjpID", context);
                String Day = Utils.getPreferences("Day", context);
                AppDataBase db = new AppDataBase(context, "");
                db.open();
                db.updatePJPDay("Order Has Been Taken", id, Day);
                db.close();
            }
        } else {
            appDataBase.open();
            appDataBase.delOrder(outletId);
//            appDataBase.delPromo();
            appDataBase.close();
            Utils.savePreferences("GTotal", "", context);
            Utils.savePreferences("cartItem", "0", context);
        }


        btnReceivedAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiRequest = "SentPayment";
                String Reference = tv_ReferenceNumber.getText().toString();
                String amount = etReceivedAmount.getText().toString();
//api/"Sales/UpdateInvoicePayment?invoiceId="Reference+"&amount="+amount
                String link = Utils.getPreferences("link", context);
//                String encode = Utils.getPreferences("encode", context);
                String authorizationString = encode;
                cm.postStringRequest(link, "api/Sales/UpdateInvoicePayment?invoiceId=" + Reference + "&amount=" + amount, authorizationString, sendOutletStatus);
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent a = new Intent(context, HomeActivity.class);
        startActivity(a);

    }
}
