package com.jyaconsulting.aashal1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jyaconsulting.aashal1.database.AppDataBase;
import com.jyaconsulting.aashal1.model.UserInfoModel;
import com.jyaconsulting.aashal1.utilinterface.CMResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Activity activity;
    private LinearLayout productList, outletList, productLayout;
    private Spinner catagory, sub_catagory;
    private EditText et_amount;
    private Button btn_add_product, submit;
    ArrayList<String> productType, productSubType, proCode, SubProCode, FkSubProCode;
    String[] productID, productName, subProductID, subProductName, fkSubProCode;
    ArrayList<String> AddedProduct, AddedSubProduct;

    //Variables to Store Data for a new outlet
    String Address, OutletName, OwnerName, ChannelTypeCode, CNIC, Telno, Mobile, Mobile2, Alternateno,
            Faxno, PurchaserName, PurchaserMobile, Latitutde, Longitude, MacAddress, CountryCode,
            CityCode, RegionCode, DistributorCode, AreaCode;
    private Spinner SpProductType;
    private Spinner SpProductSubType;
    private String encode;
    JSONArray json;
    CommunicationManager cm;
    CMResponse addOutLet = new CMResponse() {
        @Override
        public void consumeResponse(String response, boolean isSuccess) throws JSONException {
            String strOutlet = Utils.getPreferences("NewOutlets", context);

            SimpleDateFormat sdf=new SimpleDateFormat("EEEE");
            Date day=new Date();
            String CurrentDay=sdf.format(day);
            Utils.savePreferences("CurrentDay",CurrentDay,context);

            if (strOutlet.isEmpty()) {
                Utils.savePreferences("NewOutlets", "0", context);
            } else {
                int outlet = Integer.parseInt(strOutlet);
                outlet = outlet + 1;
                Utils.savePreferences("NewOutlets", String.valueOf(outlet), context);
            }
            if (isSuccess == true) {
                Toast.makeText(context, "Outlet Added Successfully", Toast.LENGTH_SHORT).show();
//                AppDataBase db = new AppDataBase(context, "");
//                db.open();
//                db.delSpinnerProduct();
//                db.delSubProdut();
//                db.close();

                Utils.savePreferences(Constants.OUTLET_NAME, "", context);
                Utils.savePreferences(Constants.OUTLET_ADDRESS, "", context);
                Utils.savePreferences(Constants.OWNER_NAME, "", context);
                Utils.savePreferences(Constants.CNIC, "", context);
                Utils.savePreferences(Constants.PHONE, "", context);
                Utils.savePreferences(Constants.PHONE_OPTIONAL, "", context);
                Utils.savePreferences(Constants.PURCHASER, "", context);
                Utils.savePreferences(Constants.PURCHASER_PHONE, "", context);

                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
            } else {

                Utils.savePreferences(Constants.OUTLET_NAME, "", context);
                Utils.savePreferences(Constants.OUTLET_ADDRESS, "", context);
                Utils.savePreferences(Constants.OWNER_NAME, "", context);
                Utils.savePreferences(Constants.CNIC, "", context);
                Utils.savePreferences(Constants.PHONE, "", context);
                Utils.savePreferences(Constants.PHONE_OPTIONAL, "", context);
                Utils.savePreferences(Constants.PURCHASER, "", context);
                Utils.savePreferences(Constants.PURCHASER_PHONE, "", context);

                submit.setEnabled(true);
                AppDataBase db = new AppDataBase(context, "");
                db.open();

                long outletId = db.insertNewOutlets(OutletName, Address, ChannelTypeCode, OwnerName, CNIC, Telno, Mobile, Mobile2, Alternateno,
                        Faxno, PurchaserName, PurchaserMobile, Latitutde, Longitude, MacAddress, CountryCode, CityCode, RegionCode,
                        AreaCode, DistributorCode);

                for (int i = 0; i < json.length(); i++) {
                    JSONObject obj = json.getJSONObject(i);
                    String Group = obj.getString("ItmsGrpId");
                    String SubGroup = obj.getString("ItmsSubGrpId");
                    String Quantity = obj.getString("Quantity");
                    String Amount = obj.getString("Amount");

                    db.insertNewOutletProducts(Group, SubGroup, Quantity, Amount, String.valueOf(outletId));
                }
                db.close();
                Toast.makeText(context, "Outlet Added To Your Mobile Storage", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        context = AddProductActivity.this;
        activity = this;
        cm = new CommunicationManager(activity);
        json = new JSONArray();

        AppDataBase db = new AppDataBase(context, "");
        db.open();
        ArrayList<UserInfoModel> arr=db.getUserInfo();
        UserInfoModel current=arr.get(0);
        encode=current.getAccount();
        linkViews();
        if (db.SpinnerProductName().size() > 1) {
            populateSpinner();
        } else {
            getJsonArray();
        }
//        addProduct();
        db.close();
    }

    private void addProduct() {
        int productCheck = 0;
        productLayout = findViewById(R.id.productLayout);
        String subType = SpProductSubType.getSelectedItem().toString();
        String productType = SpProductType.getSelectedItem().toString();
        String Amount = et_amount.getText().toString();

        if (!productType.equalsIgnoreCase("--Select Product Type--")) {
            if (!subType.equalsIgnoreCase("--Select Product Subtype--")) {
                AddedSubProduct.add(subType);
                for (int k = 0; k < AddedSubProduct.size() - 1; k++) {
                    if (subType.equalsIgnoreCase(AddedSubProduct.get(k)) && AddedSubProduct.size() > 1) {
                        productCheck++;
                    }
                }
            }
        }


        if (!productType.equalsIgnoreCase("--Select Product Type--")) {
            if (!subType.equalsIgnoreCase("--Select Product Subtype--")) {

                if (productCheck > 0) {
                    Utils.showToast(context, "This Item Already Exist in the List");
                } else {
                    productCheck = 0;
                    if (!Amount.isEmpty()) {
                        String ProductCode = null, SubproductCode = null;

                        //Create A JsonObject
                        try {
                            JSONObject obj = new JSONObject();
                            for (int i = 0; i <= productName.length - 1; i++) {
                                if (ProductCode == null) {
                                    if (productType.equals(productName[i])) {
                                        ProductCode = productID[i];
                                        if (!ProductCode.equalsIgnoreCase("--Select Product Type--")) {
                                            obj.put("ItmsGrpId", Integer.parseInt(ProductCode));
                                        } else {
                                            ProductCode = null;
                                        }
                                    }
                                }
                            }
                            for (int i = 0; i <= subProductName.length - 1; i++) {
                                if (SubproductCode == null) {
                                    if (subType.equals(subProductName[i])) {
                                        SubproductCode = subProductID[i];
                                        if (!SubproductCode.equalsIgnoreCase("--Select Product Code--")) {
//
//                                                AddedSubProduct.add(SubproductCode);
                                            obj.put("ItmsSubGrpId", Integer.parseInt(SubproductCode));
                                        } else {
                                            SubproductCode = null;
                                        }
                                    }
                                }
                            }
                            if (ProductCode != null && SubproductCode != null) {
                                obj.put("Quantity", 0);
                                obj.put("Amount", Integer.parseInt(Amount));

                                //Add JSONObject to JSONArray
                                json.put(obj);
                            } else if (ProductCode == null) {
                                Utils.showToast(context, "Select a Product");
                            } else if (SubproductCode == null) {
                                Utils.showToast(context, "Select a Sub Product");
                            } else {
                                Utils.showToast(context, "Select a Product and Sub Product");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Create A linearLayout Programmatically
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);

                        params.weight = 1;
                        int i = 1;

                        if (!Amount.isEmpty()) {
                            final LinearLayout linearLayout = new LinearLayout(context);
                            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            linearLayout.setWeightSum(3);
                            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            linearLayout.setId(i);
                            TextView catagory = new TextView(context);
                            TextView subCatagory = new TextView(context);
                            TextView amount = new TextView(context);
//            ImageView ivDel = new ImageView(context);
//            ivDel.setImageResource(R.drawable.delete);
//            ivDel.setBackgroundTintList(Color.c);
//            ivDel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    linearLayout.setVisibility(View.GONE);
//                }
//            });
                            catagory.setGravity(Gravity.CENTER_HORIZONTAL);
                            subCatagory.setGravity(Gravity.CENTER_HORIZONTAL);
                            amount.setGravity(Gravity.CENTER_HORIZONTAL);
//            ivDel.setGravity(Gravity.CENTER_HORIZONTAL);

                            catagory.setTextColor(Color.parseColor("#FFFFFF"));
                            subCatagory.setTextColor(Color.parseColor("#FFFFFF"));
                            amount.setTextColor(Color.parseColor("#FFFFFF"));

                            catagory.setText(productType);
                            subCatagory.setText(subType);
                            amount.setText(Amount);

                            catagory.setLayoutParams(params);
                            subCatagory.setLayoutParams(params);
                            amount.setLayoutParams(params);

                            linearLayout.addView(catagory);
                            linearLayout.addView(subCatagory);
                            linearLayout.addView(amount);

                            View v = new View(this);
                            v.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    2
                            ));
                            et_amount.setText("");
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(et_amount.getWindowToken(), 0);
                            v.setBackgroundColor(Color.parseColor("#B3B3B3"));
                            productLayout.addView(linearLayout);
                            productLayout.addView(v);
                        } else {
                            Utils.showToast(context, "Enter Amount");
                        }

//        AddProductModel gmModel = new AddProductModel(Amount, productType, subType);
//        ArrayList<AddProductModel> arlProduct = new ArrayList<>();
//        arlProduct.add(gmModel);
//        productLayout = findViewById(R.id.productLayout);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
//        AddProductAdapter adapter = new AddProductAdapter(context, arlProduct, activity);
//        productLayout.setLayoutManager(gridLayoutManager);
//        productLayout.setAdapter(adapter);
                    } else {
                        Utils.showToast(context, "Enter an amount");
                    }
                }

            } else {
                Utils.showToast(context, "Select SubProduct ");
            }
        } else {
            Utils.showToast(context, "Select Product ");
        }
//        Toast.makeText(context, "Please Enter Amount", Toast.LENGTH_SHORT).show();
    }

    private void linkViews() {

        AddedProduct = new ArrayList<>();
        AddedSubProduct = new ArrayList<>();
        productList = findViewById(R.id.addProduct1);
        outletList = findViewById(R.id.outletList1);
        et_amount = findViewById(R.id.et_amount);
        btn_add_product = findViewById(R.id.btn_add_product);
        submit = findViewById(R.id.btn_submit);
        SpProductType = findViewById(R.id.spinner_catagory);
        SpProductSubType = findViewById(R.id.spinner_sub_catagory);

        submit.setEnabled(true);
        btn_add_product.setOnClickListener(this);
        submit.setOnClickListener(this);
        productList.setOnClickListener(this);
        outletList.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addProduct1:
                Toast.makeText(context, "Already In Product", Toast.LENGTH_SHORT).show();
                break;
            case R.id.outletList1:
                Intent intent = new Intent(context, OutletFromActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_add_product:
                addProduct();
                break;
            case R.id.btn_submit:
                submit.setEnabled(false);
                submitProduct();
                break;
        }

    }

    private void submitProduct() {
        if (json.length() > 0) {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wInfo = wifiManager.getConnectionInfo();
            Intent get_intent = getIntent();

            Address = get_intent.getStringExtra(Constants.OUTLET_ADDRESS);
            OutletName = get_intent.getStringExtra(Constants.OUTLET_NAME);
            OwnerName = get_intent.getStringExtra(Constants.OWNER_NAME);
            ChannelTypeCode = get_intent.getStringExtra(Constants.CHANNEL_ID);
            CNIC = get_intent.getStringExtra(Constants.CNIC);
            Telno = get_intent.getStringExtra(Constants.PHONE);
            Mobile = get_intent.getStringExtra(Constants.PHONE);
            Mobile2 = get_intent.getStringExtra(Constants.PHONE_OPTIONAL);
            Alternateno = get_intent.getStringExtra(Constants.PHONE_OPTIONAL);
            Faxno = "";
            PurchaserName = get_intent.getStringExtra(Constants.PURCHASER);
            PurchaserMobile = get_intent.getStringExtra(Constants.PURCHASER_PHONE);
            Latitutde = get_intent.getStringExtra(Constants.LATI);
            Longitude = get_intent.getStringExtra(Constants.LONGI);
            MacAddress = wInfo.getMacAddress().toString();
            CountryCode = get_intent.getStringExtra(Constants.COUNTRY_ID);
            CityCode = get_intent.getStringExtra(Constants.CITY_ID);
            RegionCode = get_intent.getStringExtra(Constants.REGION_ID);
            DistributorCode = get_intent.getStringExtra(Constants.DISTRUBUTER_ID);
            AreaCode = get_intent.getStringExtra(Constants.AREA_ID);

            try {
                String link = Utils.getPreferences("link", context);
                JSONObject obj = new JSONObject();
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
//                String encryptLogin = Utils.getPreferences("encode", context);
                String authorizationString = encode;
                cm.postJSONObjectRequest(link, "api/MobileTransaction/AddOutlet", authorizationString, obj, addOutLet);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "Select a Product and Subproduct", Toast.LENGTH_SHORT).show();
        }

    }

    private void getJsonArray() {

        String url = "api/MobileMasterData/GetData/";
        final String encryptLogin = encode;
        String link = Utils.getPreferences("link", context);

        final ProgressDialog progressDialog = new ProgressDialog(this.activity, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Working, Please Wait...");
        progressDialog.setProgressStyle(0);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this.activity.getApplicationContext());
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, "http://" + link + "/" + url, null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                if (response != null) {
                    productType = new ArrayList<>();
                    proCode = new ArrayList<>();
                    productSubType = new ArrayList<>();
                    SubProCode = new ArrayList<>();
                    try {
                        JSONArray jsonArray = new JSONArray(response.toString());
                        JSONObject obj;
                        AppDataBase dataBase = new AppDataBase(context, "");
//                        dataBase.open();

                        for (int i = 0; i <= jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String Entity = jsonObject.getString("Entity");
                            if (Entity.equals("ItemGroup")) {
                                obj = jsonObject.getJSONObject("Object");
                                String pro = obj.getString("itemGroupName");
                                String proID = obj.getString("itemGroupId");
//                                productType.add(pro);
//                                proCode.add(proID);
                                dataBase.open();
                                dataBase.insertSpinnerProduct(proID, pro);
                                dataBase.close();
                            } else if (Entity.equals("ItemSubGroup")) {
                                obj = jsonObject.getJSONObject("Object");
                                String pro = obj.getString("itemGroupName");
                                String proID = obj.getString("itemGroupId");
                                String subPro = obj.getString("itemSubGroupName");
                                String subProID = obj.getString("itemSubGroupId");
//                                productType.add(pro);
//                                proCode.add(proID);
//                                productSubType.add(subPro);
//                                SubProCode.add(subProID);
                                dataBase.open();
                                dataBase.insertSubProduct(subProID, subPro, proID);
                                dataBase.close();
                            }
                            populateSpinner();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//
                } else {
                    Toast.makeText(context, "Something went wrong..!!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();

                if (error instanceof TimeoutError) {

                    Toast.makeText(context, "Connection Timeout", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "Internet Not Available", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "Enter a valid UserName and Password", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(context, "Server cannot be found", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(context, "Can not connect to the internet", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(context, "Data can not be Parsed", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }) {

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");

                String authorizationString = encryptLogin; //this line is diffe
                headers.put("Authorization", authorizationString);
                return headers;
            }
        };
        queue.add(jsonArray);
    }

    private void populateSpinner() {
        final AppDataBase db = new AppDataBase(context, "");
        db.open();

        productType = db.SpinnerProductName();
        proCode = db.SpinnerProductId();

        //Convert ProductTpe ArrayList to Array
        productName = new String[productType.size()];
        productName = productType.toArray(productName);

        // Convert ProCode ArrayList to Array
        productID = new String[proCode.size()];
        productID = proCode.toArray(productID);


        SpProductType.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, productType));
        SpProductType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                String Product = parent.getItemAtPosition(position).toString();
                String productId = productID[position];
                productSubType = db.SubProductsName(productID[position]);
                SubProCode = db.SubProductsId(productID[position]);
                FkSubProCode = db.MainProductsId(productID[position]);
                fkSubProCode = new String[FkSubProCode.size()];
                fkSubProCode = FkSubProCode.toArray(fkSubProCode);
                // Convert productSubType ArrayList to Array
                subProductName = new String[productSubType.size()];
                subProductName = productSubType.toArray(subProductName);

                // Convert SubProCode ArrayList to Array
                subProductID = new String[SubProCode.size()];
                subProductID = SubProCode.toArray(subProductID);
                SpProductSubType.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, productSubType));


            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

}
