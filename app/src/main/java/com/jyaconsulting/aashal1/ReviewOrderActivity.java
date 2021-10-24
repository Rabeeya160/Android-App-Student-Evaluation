package com.jyaconsulting.aashal1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.pdf.StringUtils;
import com.jyaconsulting.aashal1.adapter.ReviewOrderAdapter;
import com.jyaconsulting.aashal1.database.AppDataBase;
import com.jyaconsulting.aashal1.model.OrderModel;
import com.jyaconsulting.aashal1.model.UserInfoModel;
import com.jyaconsulting.aashal1.utilinterface.CMResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Activity activity;
    private Button btn_proceed_order, btn_review_order;
    public static TextView tv_total_amount;
    private RecyclerView recyler_review_order;
    private ReviewOrderAdapter adapter;
    CommunicationManager cm;
    String ApiRequest = "",encode;
    int id[], GrandTotal, j = 0;
    CMResponse getPriceandPromotion = new CMResponse() {
        @Override
        public void consumeResponse(String response, boolean isSuccess) throws JSONException {
            if (isSuccess == true) {
                if (response != null) {
                    JSONArray jsonArray = new JSONArray(response);
                    if (ApiRequest.equalsIgnoreCase("GetPromo")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String Id = obj.getString("Id");
                            String ParentItem = obj.getString("ParentItem");
                            String ParentQty = obj.getString("ParentItemQuantity");
                            String ChildItem = obj.getString("ChildItem");
                            String ChildQty = obj.getString("ChildItemQuantity");
                            String From = obj.getString("StartDate");
                            String To = obj.getString("EndDate");
                            String EndDate = To.substring(0, 8);
                            String End = To.substring(8, 10);
                            String Month = To.substring(5, 7);
                            int incEnd = Integer.parseInt(End) + 1;
                            if (Month.equals("01") || Month.equals("03") || Month.equals("05") || Month.equals("07") || Month.equals("08")
                                    || Month.equals("10") || Month.equals("12")) {
                                if (incEnd > 31) {
                                    incEnd = 00;
                                }
                            } else if (Month.equals("04") || Month.equals("06") || Month.equals("09") || Month.equals("")) {
                                if (incEnd > 30) {
                                    incEnd = 00;
                                }
                            } else {
                                if (incEnd > 28) {
                                    incEnd = 00;
                                }
                            }
                            Utils.savePreferences("PromoMonth", Month, context);
                            Utils.savePreferences("PromoDate", String.valueOf(incEnd), context);
                            AppDataBase dataBase = new AppDataBase(context, "");
                            dataBase.open();
                            dataBase.insertItemPromotion(Id, ParentItem, ParentQty, ChildItem, ChildQty, From, To);
                        }
                        CheckPromotionAndPrice();
                    } else {

                        for (int i = 0; i <= jsonArray.length() - 1; i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            boolean promotion = obj.getBoolean("IsPromotionalItem");
                            if (promotion == false) {
                                if (id[j] == obj.getInt("Id")) {
                                    int Price = obj.getInt("UnitPrice");
                                    if (Utils.getPreferences("GTotal", context).isEmpty()) {
                                        GrandTotal = obj.getInt("NetPrice");
                                        Utils.savePreferences("GTotal", String.valueOf(GrandTotal), context);
//                                j++;
                                    } else {
                                        String Total = Utils.getPreferences("GTotal", context);
                                        GrandTotal = Integer.parseInt(Total) + obj.getInt("NetPrice");
                                        Utils.savePreferences("GTotal", String.valueOf(GrandTotal), context);
//                                j++;
                                    }
                                    AppDataBase db = new AppDataBase(context, "");
                                    db.open();
                                    if (Price != 0) {
                                        db.updateOrder(String.valueOf(Price), String.valueOf(id[j]));
                                    } else {
                                        String outletId=Utils.getPreferences("pjpID",context);
                                        db.insertPromotion(String.valueOf(obj.getInt("Id")), obj.getString("Name"),
                                                String.valueOf(obj.getInt("Quantity")),outletId);
                                    }
                                    db.close();
                                } else {
                                    if (promotion == true) {
                                        String outletId=Utils.getPreferences("pjpID",context);
                                        AppDataBase db = new AppDataBase(context, "");
                                        db.open();
                                        db.insertPromotion(String.valueOf(obj.getInt("Id")), obj.getString("Name"),
                                                String.valueOf(obj.getInt("Quantity")),outletId);
                                        db.close();
                                    }
                                }
                            }
                        }
//                        linkViews();
                    }
                }
            } else {
                btn_review_order = findViewById(R.id.btn_order_modify);
                btn_review_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ModifyOrderActivity.class);
                        AppDataBase db = new AppDataBase(context, "");
                        db.open();
                        db.delPromo();
                        db.close();
                        intent.putExtra("selectedProduct", "null");
                        startActivity(intent);
                    }
                });
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                linkViews();
                Utils.savePreferences("order", "offline", context);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);

        context = ReviewOrderActivity.this;
        activity = this;
        cm = new CommunicationManager(activity);

        AppDataBase db = new AppDataBase(context, "");
        db.open();
        ArrayList<UserInfoModel> arr=db.getUserInfo();
        UserInfoModel current=arr.get(0);
        encode=current.getAccount();
        db.close();

        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String dateToStr1 = format1.format(today);
        String CurrentDate = dateToStr1.substring(8, 10);
        String CurrentMonth = dateToStr1.substring(5, 7);
        String EndDate = Utils.getPreferences("PromoDate", context);
        String EndMonth = Utils.getPreferences("PromoMonth", context);
        int EndDateToInt = 0, EndMonthToInt = 0, CurrentDateToInt = 0, CurrentMonthToInt = 0;
        if (!EndDate.isEmpty()) {
            EndDateToInt = Integer.parseInt(EndDate);
            EndMonthToInt = Integer.parseInt(EndMonth);
            CurrentDateToInt = Integer.parseInt(CurrentDate);
            CurrentMonthToInt = Integer.parseInt(CurrentMonth);
        }

        AppDataBase dataBase = new AppDataBase(context, "");
        dataBase.open();
        //TODO Start == Condition To Check if Promotion Date Is End Then Delete Promotion ==
        if (CurrentMonthToInt > EndMonthToInt) {
            dataBase.delAllPromo();
        } else if (CurrentMonth == EndMonth && CurrentDateToInt > EndDateToInt) {
            dataBase.delAllPromo();
        }
        // TODO == End Condition ==

        //TODO Start == Condition to check if Promotion is not Available then check the promo from online ==
        int count = dataBase.AllPromotion().size();
        if (count > 0) {
            Utils.savePreferences("order", "offline", context);
            CheckPromotionAndPrice();
        } else {
            GetPromotion();
        }
        //TODO == End Condition ==

    }

    private void GetPromotion() {
        ApiRequest = "GetPromo";
        String link = Utils.getPreferences("link", context);
//        String encode = Utils.getPreferences("encode", context);
        String authorizationString = encode;
        cm.getRequestJsonArr(link, "api/MobileMasterData/GetAllPromotions", authorizationString, getPriceandPromotion);
    }

    private void CheckPromotionAndPrice() {
//        ApiRequest = "GetPriceAndPromo";
        AppDataBase dataBase = new AppDataBase(context, "");
        dataBase.open();
        String outletId=Utils.getPreferences("pjpID",context);
        List<OrderModel> arrayOrder = dataBase.getAllOrder(outletId);
        id = new int[arrayOrder.size()];
        for (int i = 0; i <= arrayOrder.size() - 1; i++) {
//            String link = Utils.getPreferences("link", context);
            OrderModel current = arrayOrder.get(i);
            id[i] = Integer.parseInt(current.getItemId());
            int qty = Integer.parseInt(current.getOrderProductQuantity());
            AppDataBase db = new AppDataBase(context, "");
            db.open();
            db.Promotion(String.valueOf(id[i]), qty);
            db.close();

            linkViews();

//            String encode = Utils.getPreferences("encode", context);
//            String authorizationString = "Basic " + Base64.encodeToString((encode).getBytes(), Base64.DEFAULT);
//            cm.getRequestJsonArr(link, "api/Item/GetProductDetailForSaleOrder/?id=" + id[i] + "&quantity=" + qty, authorizationString, getPriceandPromotion);
        }
    }

    private void linkViews() {
//        j++;

        btn_proceed_order = findViewById(R.id.btn_order_proceed);
        btn_review_order = findViewById(R.id.btn_order_modify);
        tv_total_amount = findViewById(R.id.tv_total_amount);
        recyler_review_order = findViewById(R.id.recyler_review_order);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyler_review_order.getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyler_review_order.setLayoutManager(gridLayoutManager);

        AppDataBase appDataBase = new AppDataBase(context, "");
        appDataBase.open();
        String outletId=Utils.getPreferences("pjpID",context);
        adapter = new ReviewOrderAdapter(context, appDataBase.getAllOrder(outletId));
        recyler_review_order.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        appDataBase.close();

//        tv_total_amount.setText(String.valueOf(Utils.getPreferences("GTotal", context)));
        j++;


        btn_review_order.setOnClickListener(this);
        btn_proceed_order.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_order_proceed:
                intent = new Intent(context, PromoActivity.class);
                intent.putExtra("Gtotal", tv_total_amount.getText().toString());
                Utils.savePreferences("GTotal", tv_total_amount.getText().toString(), context);
                startActivity(intent);
                break;
            case R.id.btn_order_modify:
                intent = new Intent(context, ModifyOrderActivity.class);
                AppDataBase db = new AppDataBase(context, "");
                db.open();
                db.delPromo();
                db.close();
                intent.putExtra("selectedProduct", "null");
                startActivity(intent);
                break;
        }

    }
}
