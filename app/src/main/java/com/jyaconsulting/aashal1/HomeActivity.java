package com.jyaconsulting.aashal1;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.gson.JsonArray;
import com.jyaconsulting.aashal1.database.AppDataBase;
import com.jyaconsulting.aashal1.model.NewOutletModel;
import com.jyaconsulting.aashal1.model.NewOutletProduct;
import com.jyaconsulting.aashal1.model.OrderModel;
import com.jyaconsulting.aashal1.model.PromoModel;
import com.jyaconsulting.aashal1.model.UserInfoModel;
import com.jyaconsulting.aashal1.utilinterface.CMResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Activity activity;
    String encode, Address, OutletName, OwnerName, ChannelTypeCode, CNIC, Telno, Mobile, Mobile2, Alternateno,
            Faxno, PurchaserName, PurchaserMobile, Latitutde, Longitude, MacAddress, CountryCode,
            CityCode, RegionCode, DistributorCode, AreaCode;
    String[] OutLetIds;
    private CardView outlet, customer, visit, delivery, sync, logout, order, changePassword;
    private TextView salesman, distributor, area, region, newOutlets;
    int dsc, outletId, OutletId[], id[], GrandTotal, j = 0, endOrder = -1;   //endOrder is used to check that if value of j repeated to get all promotion then now go to add order
    double lati, longi;
    List<NewOutletModel> outletArray;
    List<NewOutletProduct> outLetProduct;
    String ApiRequest = "";
    String mPermission = "android.permission.ACCESS_FINE_LOCATION";
    CommunicationManager cm;

    CMResponse addOutLet = new CMResponse() {
        @Override
        public void consumeResponse(String response, boolean isSuccess) throws JSONException {
            if (isSuccess == true) {
                if (response != null) {
                    Toast.makeText(context, j + 1 + " Outlet Added Successfully", Toast.LENGTH_SHORT).show();
                    AppDataBase db = new AppDataBase(context, "");
                    db.open();
                    db.delNewOutLet(OutLetIds[j]);
                    db.close();
                    j = j + 1;
                }
            } else {
                Utils.showToast(context, response);
            }
        }
    };


    CMResponse addSaleorder = new CMResponse() {
        @Override
        public void consumeResponse(String response, boolean isSuccess) throws JSONException {
            if (isSuccess == true) {
                if (ApiRequest.equalsIgnoreCase("Add")) {
                    String id = Utils.getPreferences("pjpID", context);
                    String Day = Utils.getPreferences("Day", context);
                    AppDataBase db = new AppDataBase(context, "");
                    db.open();
                    db.updatePJPDay("Order Has Been Taken", id, Day);
                    db.close();
                    Toast.makeText(context, j + 1 + " Order Added Successfully", Toast.LENGTH_SHORT).show();
                    incrementEndOrder();
                } else {
                    JSONArray jsonArray = new JSONArray(response);
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
                                    db.insertPromotion(String.valueOf(obj.getInt("Id")), obj.getString("Name"),
                                            String.valueOf(obj.getInt("Quantity")), String.valueOf(outletId));
                                }
                                db.close();
                            } else {
                                if (promotion == true) {
                                    AppDataBase db = new AppDataBase(context, "");
                                    db.open();
                                    db.insertPromotion(String.valueOf(obj.getInt("Id")), obj.getString("Name"),
                                            String.valueOf(obj.getInt("Quantity")), String.valueOf(outletId));
                                    db.close();
                                }
                            }
                        }
                    }
                    incrementJ();
                }
            } else {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void incrementEndOrder() {
        j++;
        if (endOrder + 1 >= j) {
            AppDataBase dataBase = new AppDataBase(context, "");
            dataBase.open();
            dataBase.delOrder(String.valueOf(OutletId[j - 1]));
//            dataBase.delPromo();
            dataBase.close();
            Utils.savePreferences("GTotal", "", context);
            Utils.savePreferences("cartItem", "0", context);
        }
    }

    private void incrementJ() {
        j++;
        if (j > endOrder) {
            Utils.savePreferences("order", "", context); // If All Promotion checked set order empty
            addOrder();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = HomeActivity.this;
        activity = this;
        cm = new CommunicationManager(activity);
        locationManagement();

        if (!Utils.getPreferences("CurrentDay", context).equals(Utils.getPreferences("TodayIs", context))) {
            Utils.savePreferences("NewOutlets", "0", context);
        }

        linkViews();
    }

    private void linkViews() {
        visit = findViewById(R.id.card_visit);
        changePassword = findViewById(R.id.card_ChangePassword);
        sync = findViewById(R.id.card_sync);
        logout = findViewById(R.id.card_logout);
        outlet = findViewById(R.id.card_outlet);
        order = findViewById(R.id.card_order);
        customer = findViewById(R.id.card_customer);
        delivery = findViewById(R.id.card_delivery);
        salesman = findViewById(R.id.tv_salesMan);
        distributor = findViewById(R.id.tv_distributorName);
        area = findViewById(R.id.tvArea);
        region = findViewById(R.id.tvRegion);
        newOutlets = findViewById(R.id.tvAddedOutlets);

        AppDataBase db=new AppDataBase(context,"");
        db.open();
        ArrayList<UserInfoModel> userInfo=db.getUserInfo();
        UserInfoModel current=userInfo.get(0);

        String outlets = Utils.getPreferences("NewOutlets", context);
        if (outlets != null && !outlets.isEmpty()) {
            newOutlets.setText(outlets);
        }

//        salesman.setText(Utils.getPreferences(Constants.SALES_MAN, context));
//        distributor.setText(Utils.getPreferences(Constants.DISTRIBUTER_NAME, context));
//        area.setText(Utils.getPreferences(Constants.AREA, context));
//        region.setText(Utils.getPreferences(Constants.REGION, context));

        salesman.setText(current.getUserName());
        distributor.setText(current.getDistName());
        area.setText(current.getAreaName());
        region.setText(current.getRegName());

//        encode = Utils.getPreferences("encode", context);
        encode=current.getAccount();
        db.close();


        visit.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        sync.setOnClickListener(this);
        logout.setOnClickListener(this);
        outlet.setOnClickListener(this);
        order.setOnClickListener(this);
        customer.setOnClickListener(this);
        delivery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.card_outlet:
                outletForm();
                break;
            case R.id.card_customer:
                intent = new Intent(context, OutletListActivity.class);
                startActivity(intent);
                break;
            case R.id.card_order:
                SelectSaleType();

                break;
            case R.id.card_logout:
                Logout();
                break;
            case R.id.card_ChangePassword:
                intent = new Intent(context, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.card_sync:
                AppDataBase db = new AppDataBase(context, "");
                db.open();
                int orderCount = db.getAllOrder().size();
                int outletCount = db.getAllNewOutlet().size();
                db.close();
                if (orderCount > 0 && outletCount > 0) {
                    showOptions();
                } else if (orderCount > 0) {
                    addOrder();
                } else if (outletCount > 0) {
                    addOutLets();
                } else {
                    Utils.showToast(context, "No Data Exist");
                }
                break;

        }

    }

    private void showOptions() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Select to Sync");
        dialog.setMessage("What do you want to upload first select the option?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Sync Outlets", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                addOutLets();
            }
        });
        dialog.setNegativeButton("Sync Orders", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                addOrder();
//                paramDialogInterface.dismiss();

            }
        });
//            AlertDialog alertDialog = dialog.create();

        // show it
        dialog.show();
    }

    private void addOutLets() {
        AppDataBase db = new AppDataBase(context, "");
        db.open();
        outletArray = db.getAllNewOutlet();
        OutLetIds = new String[db.getAllNewOutlet().size()];
        if (outletArray.size() > 0) {
            for (int i = 0; i < outletArray.size(); i++) {
                NewOutletModel current = outletArray.get(i);
                OutLetIds[i] = current.getId();
                OutletName = current.getName();
                Address = current.getAddress();
                ChannelTypeCode = current.getChannel();
                OwnerName = current.getOwner();
                CNIC = current.getCNIC();
                Telno = current.getTel();
                Mobile = current.getMobile();
                Mobile2 = current.getMobile2();
                Alternateno = current.getAltNo();
                Faxno = current.getFax();
                PurchaserName = current.getPurchaser();
                PurchaserMobile = current.getPurchaserMobile();
                Latitutde = current.getLat();
                Longitude = current.getLongi();
                MacAddress = current.getMac();
                CountryCode = current.getCountry();
                CityCode = current.getCity();
                RegionCode = current.getRegion();
                AreaCode = current.getArea();
                DistributorCode = current.getDistributor();

                outLetProduct = db.getAllNewOutletProduct(OutLetIds[i]);
                JSONArray json = new JSONArray();
                for (int j = 0; j < outLetProduct.size(); j++) {
                    NewOutletProduct currentItem = outLetProduct.get(j);
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("ItmsGrpId", Integer.parseInt(currentItem.getGroupId()));
                        obj.put("ItmsSubGrpId", Integer.parseInt(currentItem.getSubGroupId()));
                        obj.put("Quantity", 0);
                        obj.put("Amount", Integer.parseInt(currentItem.getAmount()));
                        json.put(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    JSONObject obj = new JSONObject();
                    String link = Utils.getPreferences("link", context);
                    obj.put("Address", Address);
                    obj.put("OwnerName", OwnerName);
                    obj.put("OutletName", OutletName);
                    obj.put("ChannelTypeId", Integer.parseInt(ChannelTypeCode));
                    obj.put("CNIC", CNIC);
                    obj.put("Telno", "");
                    obj.put("Mobile", Mobile);
                    obj.put("Mobile2", Mobile2);
                    obj.put("Alternateno", Alternateno);
                    obj.put("Faxno", Faxno);
                    obj.put("PurchaserName", PurchaserName);
                    obj.put("PurchaserMobile", PurchaserMobile);
                    obj.put("Latitutde", Latitutde);
                    obj.put("Longitude", Longitude);
                    obj.put("MacAddress", MacAddress);
                    obj.put("CountryId", Integer.parseInt(CountryCode));
                    obj.put("CityId", Integer.parseInt(CityCode));
                    obj.put("RegionId", Integer.parseInt(RegionCode));
                    obj.put("DistributorId", Integer.parseInt(DistributorCode));
                    obj.put("AreaId", Integer.parseInt(AreaCode));
                    obj.put("products", json);
                    String encryptLogin = Utils.getPreferences("encode", context);
                    String authorizationString = "Basic " + Base64.encodeToString((encryptLogin).getBytes(), Base64.DEFAULT);
                    cm.postJSONObjectRequest(link, "api/MobileTransaction/AddOutlet", authorizationString, obj, addOutLet);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void Logout() {
//        AppDataBase dataBase = new AppDataBase(context, "");
//        dataBase.open();
//        dataBase.delPjp();
//        dataBase.close();

        Intent intent = new Intent(context, LoginActivity.class);
        String temp = encode;
        Utils.savePreferences("temp_encode", temp, context);
//        Utils.savePreferences("encode", "", context);
        startActivity(intent);
    }

    private void SelectSaleType() {

//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        dialog.setTitle("Please Select Sale Type?");
////        dialog.setMessage("If you want to go for pre sale Press 'Pre Sale' or\nPress 'Spot Sale' to go for spot sale.");
//        dialog.setCancelable(false);
//        dialog.setPositiveButton("Pre Sell", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//                // TODO Auto-generated method stub
//                Intent intent = new Intent(context, OutletActivity.class);
//                Utils.savePreferences("SaleType", "Pre", context);
////                intent.putExtra("encode",encode);
//                startActivity(intent);
//
//            }
//        });
//        dialog.setNegativeButton("Spot Sale", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//                // TODO Auto-generated method stub
//                Intent intent = new Intent(context, OutletActivity.class);
//                Utils.savePreferences("SaleType", "Pre", context);
////                intent.putExtra("encode",encode);
//                startActivity(intent);
//
//
//            }
//        });
//
//        // show it
//        dialog.show();

//        Intent intent = new Intent(context, SaleTypeActivity.class);
//        startActivity(intent);

        Intent intent = new Intent(context, OutletActivity.class);
        Utils.savePreferences("SaleType", "Pre", context);
        startActivity(intent);
    }

    private void outletForm() {

        Intent intent = new Intent(context, OutletFromActivity.class);
        startActivity(intent);
    }

    private void CheckPromotionAndPrice() {
        ApiRequest = "Promotion";
        AppDataBase dataBase = new AppDataBase(context, "");
        dataBase.open();
        List<OrderModel> arrayOrder = dataBase.getAllOrder();
        id = new int[arrayOrder.size()];
        for (int i = 0; i <= arrayOrder.size() - 1; i++) {
            endOrder++;
            String link = Utils.getPreferences("link", context);
            OrderModel current = arrayOrder.get(i);
            id[i] = Integer.parseInt(current.getItemId());
            int qty = Integer.parseInt(current.getOrderProductQuantity());
            String encode = Utils.getPreferences("encode", context);
            String authorizationString = "Basic " + Base64.encodeToString((encode).getBytes(), Base64.DEFAULT);
            cm.getRequestJsonArr(link, "api/Item/GetProductDetailForSaleOrder/?id=" + id[i] + "&quantity=" + qty, authorizationString, addSaleorder);
        }
    }

    private void addOrder() {
        j = 0;
        endOrder = -1;
        ApiRequest = "Add";
        AppDataBase db = new AppDataBase(context, "");
        db.open();
        ArrayList<String> outletArr = db.getAllOutlet();
        OutletId = new int[db.getAllOutlet().size()];
//        OutLetId=outletArr.toArray(OutLetIds);
        List<OrderModel> order = db.getAllOrder(outletArr.get(0));
        if (order.size() > 0) {
            for (int j = 0; j < outletArr.size(); j++) {
                JSONObject object = new JSONObject();
                String link = Utils.getPreferences("link", context);
//                String authorizationString = "Basic " + Base64.encodeToString((encode).getBytes(), Base64.DEFAULT);
                String authorizationString=encode;
                endOrder++;
                OutletId[j] = Integer.parseInt(outletArr.get(j));
                order = db.getAllOrder(String.valueOf(OutletId[j]));
                List<PromoModel> promo = db.getAllPromo(String.valueOf(OutletId[j]));
                for (int k = 0; k < order.size(); k++) {
                    JSONArray json = new JSONArray();
                    for (int i = 0; i <= order.size() - 1; i++) {
                        OrderModel current = order.get(i);
                        outletId = Integer.parseInt(current.getOutletId());
                        if (current.getDiscount().isEmpty()) {
                            dsc = 0;
                        } else {
                            dsc = Integer.parseInt(current.getDiscount());
                        }
                        if (current.getLati().isEmpty()) {
                            longi = 0.0;
                            lati = 0.0;
                        } else {
                            longi = Double.parseDouble(current.getLongi());
                            lati = Double.parseDouble(current.getLati());
                        }
                        int proid = Integer.parseInt(current.getItemId());
                        int qty = Integer.parseInt(current.getOrderProductQuantity());
                        try {
                            JSONObject objOrderProduct = new JSONObject();
                            objOrderProduct.put("ItemId", proid);
                            objOrderProduct.put("Quantity", qty);
                            objOrderProduct.put("IsItemOnPromotion", "false");
                            json.put(objOrderProduct);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int i = 0; i <= promo.size() - 1; i++) {
                        PromoModel current = promo.get(i);
                        int promoid = Integer.parseInt(current.getPromoId());
                        int qty = Integer.parseInt(current.getPromoQuantity());
                        try {
                            JSONObject objPromoProduct = new JSONObject();
                            objPromoProduct.put("ItemId", promoid);
                            objPromoProduct.put("Quantity", qty);
                            objPromoProduct.put("IsItemOnPromotion", "true");
                            json.put(objPromoProduct);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//            String encode = Utils.getPreferences("encode", context);
                    }
                    String encode = Utils.getPreferences("encode", context);
//                int outletId = Integer.parseInt(Utils.getPreferences("pjpID", context));
//                int outletId = Integer.parseInt();
                    String remarks = "Add Order";
                    try {
                        object.put("OutletId", outletId);
                        object.put("Remarks", remarks);
                        object.put("Latitutde", lati);
                        object.put("Longitude", longi);
                        object.put("DiscountAmount", dsc);
                        object.put("JYA_SLODR_DTL", json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                cm.postJSONObjectRequest(link, "api/Sales/PostSalesOrder", authorizationString, object, addSaleorder);
            }
        } else {
            Toast.makeText(context, "No Pending Order Exist", Toast.LENGTH_SHORT).show();
        }
    }

    public void locationManagement() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("GPS Setting");
            dialog.setMessage("GPS is not enabled. Enable your GPS?");
            dialog.setCancelable(false);
            dialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    paramDialogInterface.dismiss();

                }
            });

            // show it
            dialog.show();
        }
    }

    @Override
    public void onBackPressed() {


        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
