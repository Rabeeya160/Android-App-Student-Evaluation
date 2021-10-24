package com.jyaconsulting.aashal1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jyaconsulting.aashal1.adapter.PromoAdapter;
import com.jyaconsulting.aashal1.database.AppDataBase;
import com.jyaconsulting.aashal1.model.OrderModel;
import com.jyaconsulting.aashal1.model.PromoModel;
import com.jyaconsulting.aashal1.model.UserInfoModel;
import com.jyaconsulting.aashal1.utilinterface.CMResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PromoActivity extends AppCompatActivity implements View.OnClickListener,
        LocationListener {
    private Context context;
    private EditText refrenceNumber;
    private String SaleType = "";
    private Activity activity;
    private Button save, increase, decrease;
    private RecyclerView recyler_promo;
    private TextView tvDiscount;
    private PromoAdapter adapter;
    private EditText et_DscPer, et_DscPri;
    private Spinner spinner_gst, spinner_wht;
    FloatingActionButton fab;
    private ArrayList<String> GST, WHT;
    String gstValue, whtValue, path, signature_pdf_ = "Invoice", authorizationString;
    String requestType,encode;
    int gstPrice, whtPrice;
    double GrandTotal, dsc, GT, MaxDcs = 0;
    Intent get_intent;
    CommunicationManager cm;
    Location location;
    double lati, longi;

    //Get Discount
    CMResponse getDiscount = new CMResponse() {
        @Override
        public void consumeResponse(String response, boolean isSuccess) throws JSONException {
            if (isSuccess == true) {
                if (response != null) {

                    MaxDcs = Double.parseDouble(response);
                    Utils.savePreferences("MaxDsc", response, context);
                } else {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            } else {
                if (Utils.getPreferences("MaxDsc", context).isEmpty()) {
                    MaxDcs = 10;
                } else {
                    MaxDcs = Double.parseDouble(Utils.getPreferences("MaxDsc", context));
                }
            }
        }
    };

    CMResponse addSaleorder = new CMResponse() {
        @Override
        public void consumeResponse(String response, boolean isSuccess) throws JSONException {
            if (isSuccess == true) {
                if (requestType.equalsIgnoreCase("Post")) {
                    Intent intent = new Intent(context, OrderSummaryActivity.class);
                    if (Utils.getPreferences("SaleType", context).equalsIgnoreCase("Spot")) {
                        JSONObject object = new JSONObject(response);
                        String invoiceReferenceNumber = object.getString("invoiceReferenceNumber");
                        intent.putExtra("ReferenceNumber", invoiceReferenceNumber);
                    }

                    String id = Utils.getPreferences("pjpID", context);
                    AppDataBase db = new AppDataBase(context, "");
                    db.open();
                    String Day = Utils.getPreferences("Day", context);
                    db.updatePJPDay("Order Has Been Taken", id, Day);
                    db.close();
                    double GT = Double.parseDouble(Utils.getPreferences("GTotal", context));
                    double GTDSC = GT * dsc / 100;
                    GT = GT - GTDSC;
//                    String dc=String.format("%.2f",dsc);
                    intent.putExtra("Discount", String.format("%.2f", dsc));
                    intent.putExtra("Gtotal", String.valueOf(GT));
                    intent.putExtra("GST", String.valueOf(0));
                    intent.putExtra("WHT", String.valueOf(0));
                    intent.putExtra("GrossTotal", String.valueOf(GrandTotal));
                    startActivity(intent);
                    Toast.makeText(context, "Order Has Been Taken Successfully", Toast.LENGTH_SHORT).show();
                } else if (requestType.equalsIgnoreCase("Get")) {
                    JSONArray jsonArray = new JSONArray(response.toString());
                    JSONObject obj;
                    AppDataBase db = new AppDataBase(context, "");
                    db.open();
                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String Entity = jsonObject.getString("Entity");
                        if (Entity.equalsIgnoreCase("Gst")) {
                            obj = jsonObject.getJSONObject("Object");
                            int gstId = obj.getInt("GSTId");
                            int gstValue = obj.getInt("GSTValue");
                            db.insertTaxes(String.valueOf(gstId), "GST", String.valueOf(gstValue));
                        } else if (Entity.equalsIgnoreCase("Wht")) {
                            obj = jsonObject.getJSONObject("Object");
                            int gstId = obj.getInt("WHTId");
                            int gstValue = obj.getInt("WHTValue");
                            db.insertTaxes(String.valueOf(gstId), "WHT", String.valueOf(gstValue));
                        }
                    }
                    linkViews();
                }
            } else {
                Utils.savePreferences("order", "offline", context);
                Toast.makeText(context, response + "\n So Order Add to Localdatabase", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, OrderSummaryActivity.class);
                String orderId = Utils.getPreferences("pjpID", context);
                AppDataBase dataBase = new AppDataBase(context, "");
                dataBase.open();
                dataBase.updateLocation(lati, longi, tvDiscount.getText().toString(), orderId);
                Utils.savePreferences("dsc", tvDiscount.getText().toString(), context);

                intent.putExtra("Discount", String.valueOf(dsc));
                intent.putExtra("Gtotal", String.valueOf(GT));
                intent.putExtra("GST", String.valueOf(0));
                intent.putExtra("WHT", String.valueOf(0));
                intent.putExtra("GrossTotal", String.valueOf(GrandTotal));

                startActivity(intent);
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);

        context = PromoActivity.this;
        activity = this;

        AppDataBase db = new AppDataBase(context, "");
        db.open();
        ArrayList<UserInfoModel> arr=db.getUserInfo();
        UserInfoModel current=arr.get(0);
        encode=current.getAccount();
        db.close();

        cm = new CommunicationManager(activity);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        checkReadWritePermission();

        //Get Discount if Discount not available
        if (MaxDcs == 0) {
            getDiscount();
        }
        linkViews();
        getLatestLocation(0.0, 0.0);
//        }


    }

    private void getDiscount() {
        String link = Utils.getPreferences("link", context);
//        String encode = Utils.getPreferences("encode", context);
        String authorizationString = encode;

        cm.getStringRequest(link, "api/MobileTransaction/GetDiscount/ ", authorizationString, getDiscount);
    }

    private void getGstWht() {
        String link = Utils.getPreferences("link", context);
//        String encode = Utils.getPreferences("encode", context);
        String authorizationString = encode;

        cm.getRequestJsonArr(link, "api/MobileMasterData/GetData/ ", authorizationString, addSaleorder);
    }

    private void linkViews() {
        save = findViewById(R.id.btn_save_order);
        increase = findViewById(R.id.btn_increase);
        decrease = findViewById(R.id.btn_decrease);
        tvDiscount = findViewById(R.id.tvDiscount);
        recyler_promo = findViewById(R.id.recyler_promo);
        fab = findViewById(R.id.refresh_GST);
        et_DscPer = findViewById(R.id.et_DscPer);
        et_DscPri = findViewById(R.id.et_DscPri);
//        spinner_gst = findViewById(R.id.spinner_gst);
//        spinner_wht = findViewById(R.id.spinner_wht);
        refrenceNumber = findViewById(R.id.et_reference_number);

        save.setEnabled(true);

        SaleType = Utils.getPreferences("SaleType", context);
        if (SaleType.equalsIgnoreCase("Pre")) {
            refrenceNumber.setVisibility(View.GONE);
        } else if (SaleType.equalsIgnoreCase("Spot")) {
            refrenceNumber.setVisibility(View.VISIBLE);
        }
//        AppDataBase db = new AppDataBase(context, "");
//        db.open();
//        WHT = db.WHT();
//        GST = db.GST();
//        db.close();
//        spinner_wht.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, WHT));
//        spinner_gst.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, GST));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyler_promo.getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyler_promo.setLayoutManager(gridLayoutManager);

        AppDataBase appDataBase = new AppDataBase(context, "");
        appDataBase.open();
        String outletId=Utils.getPreferences("pjpID",context);
        adapter = new PromoAdapter(context, appDataBase.getAllPromo(outletId));
        recyler_promo.setAdapter(adapter);
        appDataBase.close();


        save.setOnClickListener(this);
        decrease.setOnClickListener(this);
        increase.setOnClickListener(this);
        fab.setOnClickListener(this);


    }

    public Location getLatestLocation(double lat, double lng) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return null;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 100, this);
        locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 2000, 100, this);


        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) {

            longi = locationGPS.getLongitude();
            lati = locationGPS.getLatitude();
        }

        long NetLocationTime = 0;

        if (null != locationNet) {
            longi = locationNet.getLongitude();
            lati = locationNet.getLatitude();
        }

        if (0 < GPSLocationTime - NetLocationTime) {
            return locationGPS;
        } else {
            return locationNet;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save_order:
                save.setEnabled(false);
                addOrder();
//                get_intent = getIntent();
//                AppDataBase db = new AppDataBase(context, "");
//                db.open();
//                if (db.getAllOrder().size() > 0) {
//                    //Discount in Grand Total
//                    db.close();
//                    addOrder();
//                } else {
//                    db.close();
//                    Toast.makeText(context, "No Product in Cart", Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.btn_increase:
                String str = tvDiscount.getText().toString();
                if (Integer.parseInt(str) < 5) {
                    int discount = Integer.parseInt(str) + 1;
                    tvDiscount.setText(String.valueOf(discount));
                }
                break;
            case R.id.btn_decrease:
                String str1 = tvDiscount.getText().toString();
                if (Integer.parseInt(str1) > 0) {
                    int discount1 = Integer.parseInt(str1) - 1;
                    tvDiscount.setText(String.valueOf(discount1));
                }
                break;
            case R.id.refresh_GST:
                requestType = "Get";
                getGstWht();
                break;
        }
    }

    private void addOrder() {
        ConnectivityManager CM = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = CM.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected == true) {
            Utils.savePreferences("order", "online", context);
        }
        GT = Double.parseDouble(Utils.getPreferences("GTotal", context));
        GrandTotal = GT;
        String DscAmount = et_DscPri.getText().toString();
        String DscPerAge = et_DscPer.getText().toString();
        if (DscAmount.isEmpty()) {
            if (DscPerAge.isEmpty()) {
                dsc = 0.0;
            } else {
                dsc = Double.parseDouble(DscPerAge);
            }
        } else {
            dsc = (Double.parseDouble(DscAmount) / GT) * 100;
        }
//        dsc = Double.parseDouble(tvDiscount.getText().toString());

        double GTDSC = GT * dsc / 100;
        GT = GT - GTDSC;

        if (dsc > MaxDcs) {
            save.setEnabled(true);
            android.app.AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Discount Exceeded!");
            dialog.setMessage("Your given Discount is greater than allowed discount.\n Re-Enter Discount.");
            dialog.setCancelable(false);
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    et_DscPer.setText("");
                    et_DscPri.setText("");
                }
            });
            // show it
            dialog.show();
        } else {
            if (Utils.getPreferences("order", context).equalsIgnoreCase("offline")) {
                Intent intent = new Intent(context, OrderSummaryActivity.class);

                //Get Selected Outlet ID
                String orderId = Utils.getPreferences("pjpID", context);
                AppDataBase dataBase = new AppDataBase(context, "");
                dataBase.open();

                //Update Order and Save Latitude, Longitude and Discount into Order Table against particular Order
                dataBase.updateLocation(lati, longi, tvDiscount.getText().toString(), orderId);
                Utils.savePreferences("dsc", tvDiscount.getText().toString(), context);
                intent.putExtra("Discount", String.valueOf(dsc));
                intent.putExtra("Gtotal", String.valueOf(GT));
                intent.putExtra("GST", String.valueOf(0));
                intent.putExtra("WHT", String.valueOf(0));
                intent.putExtra("GrossTotal", String.valueOf(GrandTotal));
                startActivity(intent);
            } else {
                requestType = "Post";
//
//        gstValue = spinner_gst.getSelectedItem().toString();
//        whtValue = spinner_wht.getSelectedItem().toString();


//        if (gstValue.equalsIgnoreCase("--Select GST--")) {
//            gstPrice = 0;
//        } else {
//            gstPrice = Integer.parseInt(gstValue);
//        }
//        if (whtValue.equalsIgnoreCase("--Select WHT--")) {
//            whtPrice = 0;
//        } else {
//            whtPrice = Integer.parseInt(whtValue);
//        }
                Utils.savePreferences("discount", tvDiscount.getText().toString(), context);
                AppDataBase db = new AppDataBase(context, "");
                db.open();
                String outletId = Utils.getPreferences("pjpID", context);
                List<OrderModel> order = db.getAllOrder(outletId);
                List<PromoModel> promo = db.getAllPromo(outletId);

                //        List<OrderModel> arrayOrder = db.getAllOrder();
//        db.close();

                JSONArray json = new JSONArray();

                for (int i = 0; i <= order.size() - 1; i++) {
                    OrderModel current = order.get(i);
                    int proid = Integer.parseInt(current.getItemId());
                    int qty = Integer.parseInt(current.getOrderProductQuantity());
                    try {
                        JSONObject objOrderProduct = new JSONObject();
                        objOrderProduct.put("ItemId", proid);
                        objOrderProduct.put("Quantity", qty);
                        objOrderProduct.put("IsItemOnPromotion", false);
                        json.put(objOrderProduct);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i <= promo.size() - 1; i++) {
                    PromoModel current = promo.get(i);
                    int promoid = Integer.parseInt(current.getPromoId());
                    int qty = Integer.parseInt(current.getPromoQuantity());
                    if (qty != 0) {
                        try {
                            JSONObject objPromoProduct = new JSONObject();
                            objPromoProduct.put("ItemId", promoid);
                            objPromoProduct.put("Quantity", qty);
                            objPromoProduct.put("IsItemOnPromotion", true);
                            json.put(objPromoProduct);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                String link = Utils.getPreferences("link", context);
//                String encode = Utils.getPreferences("encode", context);
                authorizationString = encode;
                int outlet = Integer.parseInt(Utils.getPreferences("pjpID", context));
                String remarks = "Add Order";
                JSONObject object = new JSONObject();
                try {
                    if (SaleType.equalsIgnoreCase("Spot")) {
                        String Refrence = refrenceNumber.getText().toString();
                        object.put("SpotSaleReference", Refrence);
                    }
                    object.put("OutletId", outlet);
                    object.put("Remarks", remarks);
                    object.put("Latitutde", lati);
                    object.put("Longitude", longi);
                    object.put("DiscountPercentage", dsc);
                    object.put("JYA_SLODR_DTL", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cm.postJSONObjectRequest(link, "api/Sales/PostSalesOrder", authorizationString, object, addSaleorder);

            }
        }
    }

    private void checkReadWritePermission() {
////        Log.v(TAG,"Permission is granted");
//            //File write logic here
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//            //File write logic here
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        getLatestLocation(lat, lng);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
