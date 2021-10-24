package com.jyaconsulting.aashal1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OutletFromActivity extends AppCompatActivity implements View.OnClickListener,
        LocationListener {
    private Context context;
    private Activity activity;
    LinearLayout productList, outletList;
    private EditText outletName, outletAddress, ownerName, cnic, phone, phoneOptional, perchaserName, perchaserPhone;
    private TextView distributorName, regionID, region, areaID, area, latlong, city, distributorID;
    private Spinner Spinner_channel;
    ArrayList<String> channel, channelCode;
    String[] channelID, channelName;
    String strChannelId,encode;
    String mPermission = "android.permission.ACCESS_FINE_LOCATION";
    Location location;
    double lati, longi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_from);
        context = OutletFromActivity.this;
        activity = OutletFromActivity.this;
        locationManagement();

        // Initialize Spinner
        Spinner_channel = (Spinner) findViewById(R.id.spinner_channel);
        AppDataBase db = new AppDataBase(context, "");
        db.open();
        ArrayList<UserInfoModel> arr=db.getUserInfo();
        UserInfoModel current=arr.get(0);
        encode=current.getAccount();
        if (db.SpinnerChanelName().size() > 1) {
            populateSpinner();
            db.close();
        } else {
            db.close();
//            String encode = Utils.getPreferences("encode", context);
            // Call GET Request with JSONArray to fill spinner
            getRequestJsonArr("api/MobileMasterData/GetData/",
                 encode);
        }
        // To Get Id's of XML Element
        linkViews();
        getLatestLocation(0.0, 0.0);

        // Get Text From Login Activity and Set to TextViews
        setStringToTextViews();
    }

    private void setStringToTextViews() {

        AppDataBase db = new AppDataBase(context, "");
        db.open();
        ArrayList<UserInfoModel> arr=db.getUserInfo();
        UserInfoModel current=arr.get(0);

        distributorID.setText(current.getDistId());
        distributorName.setText(current.getDistName());
        area.setText(current.getAreaName());
        areaID.setText(current.getAreaId());
        region.setText(current.getRegName());
        regionID.setText(current.getRegId());
        city.setText(current.getCityName());
        db.close();
    }

    public Location getLatestLocation(double lat, double lng) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        @SuppressLint("MissingPermission") Location locationGPS = null;
        @SuppressLint("MissingPermission") Location locationNet = null;
        long GPSLocationTime = 0;
        long NetLocationTime = 0;
        if (latlong.getText().toString().isEmpty()) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000, 1000, this);
            locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 20000, 1000, this);


            locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (null != locationGPS) {

                longi = locationGPS.getLongitude();
                lati = locationGPS.getLatitude();

                latlong.setText("Lat = " + String.valueOf(lati) + "\nLong = " + String.valueOf(longi));
            }


            if (null != locationNet) {
                longi = locationNet.getLongitude();
                lati = locationNet.getLatitude();
                latlong.setText("Lat = " + String.valueOf(lati) + "\nLong = " + String.valueOf(longi));
            }
        }

        if (0 < GPSLocationTime - NetLocationTime) {
            return locationGPS;
        } else {
            return locationNet;
        }


        //Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

//        if (lat != 0.0 && lng != 0.0) {
//
//            latlong.setText("Lat = " + String.valueOf(lat) + "\nLong = " + String.valueOf(lng));
//        } else {
//
////
////            double longitude = location.getLongitude();
////            double latitude = location.getLatitude();
//            latlong.setText("Lat = " + String.valueOf(lat) + "\nLong = " + String.valueOf(lng));
//        }
    }


    private void linkViews() {

        outletName = findViewById(R.id.et_outletName);
        outletAddress = findViewById(R.id.et_outlet_address);
        ownerName = findViewById(R.id.et_owner_name);
        cnic = findViewById(R.id.et_cnic);
        phone = findViewById(R.id.et_phone);
        phoneOptional = findViewById(R.id.et_phone_optional);
        perchaserName = findViewById(R.id.et_purchaser_name);
        perchaserPhone = findViewById(R.id.et_purchaser_phone);

        distributorName = findViewById(R.id.tv_db_name);
        regionID = findViewById(R.id.tv_reg_id);
        region = findViewById(R.id.tv_region);
        areaID = findViewById(R.id.tv_area_id);
        area = findViewById(R.id.tv_area);
        latlong = findViewById(R.id.tv_latlong);
        city = findViewById(R.id.tv_city);
        distributorID = findViewById(R.id.tv_db_id);

        productList = findViewById(R.id.addProduct);
        outletList = findViewById(R.id.outletList);


        Spinner_channel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String id = channelID[position];
                strChannelId = id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //TODO ==== Apply Check if user already Entered Text in EditText Then these will populate from this data automatically =====
        String strOutletName = Utils.getPreferences(Constants.OUTLET_NAME, context);
        if (!strOutletName.isEmpty()) {
            outletName.setText(strOutletName);
            outletAddress.setText(Utils.getPreferences(Constants.OUTLET_ADDRESS, context));
            ownerName.setText(Utils.getPreferences(Constants.OWNER_NAME, context));
            cnic.setText(Utils.getPreferences(Constants.CNIC, context));
            phone.setText(Utils.getPreferences(Constants.PHONE, context));
            phoneOptional.setText(Utils.getPreferences(Constants.PHONE_OPTIONAL, context));
            perchaserName.setText(Utils.getPreferences(Constants.PURCHASER, context));
            perchaserPhone.setText(Utils.getPreferences(Constants.PURCHASER_PHONE, context));

        }

        productList.setOnClickListener(this);
        outletList.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addProduct:
                addOutlet();
                break;
            case R.id.outletList:
                Toast.makeText(context, "Already In Outlet", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void addOutlet() {

        String strOwnerName, strOutletName, strOutletAdress, strCnic, strPhone, strPhoneOptional,
                strPurchaser, strPurchaserPhone, strdistributer, strRegion, strArea, strAreaID, strRegionID,
                strDistrubuterId, strChannel, strCity;

        // Channel Code Against Selected Channel

//        strChannel = Spinner_channel.getSelectedItem().toString();
//        for (int i = 0; i <= channelName.length - 1; i++) {
//            if (strChannel == channelName[i]) {
//                strChannelId = channelID[i];
//            }
//        }

        // Get Text From EditText
        strOwnerName = ownerName.getText().toString();
        strOutletAdress = outletAddress.getText().toString();
        strOutletName = outletName.getText().toString();
        strCnic = cnic.getText().toString();
        strPhone = phone.getText().toString();
        strPhoneOptional = phoneOptional.getText().toString();
        strPurchaser = perchaserName.getText().toString();
        strPurchaserPhone = perchaserPhone.getText().toString();

        // Get Text From TextView and Spinner
        strArea = area.getText().toString();
        strAreaID = areaID.getText().toString();
        strCity = city.getText().toString();
        strdistributer = distributorName.getText().toString();
        strDistrubuterId = distributorID.getText().toString();
        strRegion = region.getText().toString();
        strRegionID = regionID.getText().toString();

        // Apply Checks to Validate Data
        if (!strChannelId.equalsIgnoreCase("--Select Chanel Type--") &&
                !strChannelId.equalsIgnoreCase("--Select Channel Code--")) {
            if (!strOutletAdress.isEmpty() && !strOutletName.isEmpty() && !strOwnerName.isEmpty() && !strCnic.isEmpty()
                    && !strPhone.isEmpty() && !strCity.isEmpty()
                    && !strPurchaser.isEmpty() && !strPurchaserPhone.isEmpty()) {
                if (strCnic.length() != 13) {
                    Toast.makeText(context, "CNIC Length Must be Equal to Thirteen Characters \nand Write it without \"Dashes\"", Toast.LENGTH_SHORT).show();
                } else {
                    if (strPhone.length() != 11 || phoneFormat(strPhone) != true) {
                        Toast.makeText(context, "Phone Length Must be equal to ten Digits \nincluding 03 and Write it without \"Dashes\"", Toast.LENGTH_SHORT).show();
                    } else {
                        if (strPurchaserPhone.length() != 11 || phoneFormat(strPurchaserPhone) != true) {
                            Toast.makeText(context, "Phone Length Must be equal to ten Digits \nincluding 03 and Write it without \"Dashes\"", Toast.LENGTH_SHORT).show();

                        } else {
                            if (!strPhoneOptional.isEmpty()) {
                                if (strPhoneOptional.length() != 11|| phoneFormat(strPhoneOptional) != true) {
                                    Utils.showToast(context, "Phone Length Must be equal to ten Digits \nincluding 03 and Write it without \"Dashes\"");
                                } else {
                                    Utils.savePreferences(Constants.OUTLET_NAME, outletName.getText().toString(), context);
                                    Utils.savePreferences(Constants.OUTLET_ADDRESS, outletAddress.getText().toString(), context);
                                    Utils.savePreferences(Constants.OWNER_NAME, ownerName.getText().toString(), context);
                                    Utils.savePreferences(Constants.CNIC, cnic.getText().toString(), context);
                                    Utils.savePreferences(Constants.PHONE, phone.getText().toString(), context);
                                    Utils.savePreferences(Constants.PHONE_OPTIONAL, phoneOptional.getText().toString(), context);
                                    Utils.savePreferences(Constants.PURCHASER, perchaserName.getText().toString(), context);
                                    Utils.savePreferences(Constants.PURCHASER_PHONE, perchaserPhone.getText().toString(), context);

                                    AppDataBase db = new AppDataBase(context, "");
                                    db.open();
                                    ArrayList<UserInfoModel> arr=db.getUserInfo();
                                    UserInfoModel current=arr.get(0);
                                    db.close();
                                    Intent getIntent = getIntent();
                                    Intent intent = new Intent(context, AddProductActivity.class);
                                    intent.putExtra(Constants.DISTRUBUTER_ID, strDistrubuterId);
                                    intent.putExtra(Constants.PHONE, strPhone);
                                    intent.putExtra(Constants.PHONE_OPTIONAL, strPhoneOptional);
                                    intent.putExtra(Constants.PURCHASER, strPurchaser);
                                    intent.putExtra(Constants.PURCHASER_PHONE, strPurchaserPhone);
                                    intent.putExtra(Constants.CITY_ID, current.getCityId());
                                    intent.putExtra(Constants.COUNTRY_ID, current.getCountryId());
                                    intent.putExtra(Constants.CNIC, strCnic);
                                    intent.putExtra(Constants.OUTLET_NAME, strOutletName);
                                    intent.putExtra(Constants.OWNER_NAME, strOwnerName);
                                    intent.putExtra(Constants.LATI, String.valueOf(lati));
                                    intent.putExtra(Constants.LONGI, String.valueOf(longi));
                                    intent.putExtra(Constants.OUTLET_ADDRESS, strOutletAdress);
                                    intent.putExtra(Constants.REGION_ID, strRegionID);
                                    intent.putExtra(Constants.AREA_ID, strAreaID);
                                    intent.putExtra(Constants.CHANNEL_ID, strChannelId);
                                    startActivity(intent);

                                }
                            } else {
                                Utils.savePreferences(Constants.OUTLET_NAME, outletName.getText().toString(), context);
                                Utils.savePreferences(Constants.OUTLET_ADDRESS, outletAddress.getText().toString(), context);
                                Utils.savePreferences(Constants.OWNER_NAME, ownerName.getText().toString(), context);
                                Utils.savePreferences(Constants.CNIC, cnic.getText().toString(), context);
                                Utils.savePreferences(Constants.PHONE, phone.getText().toString(), context);
                                Utils.savePreferences(Constants.PHONE_OPTIONAL, phoneOptional.getText().toString(), context);
                                Utils.savePreferences(Constants.PURCHASER, perchaserName.getText().toString(), context);
                                Utils.savePreferences(Constants.PURCHASER_PHONE, perchaserPhone.getText().toString(), context);


                                Intent getIntent = getIntent();
                                Intent intent = new Intent(context, AddProductActivity.class);
                                intent.putExtra(Constants.DISTRUBUTER_ID, strDistrubuterId);
                                intent.putExtra(Constants.PHONE, strPhone);
                                intent.putExtra(Constants.PHONE_OPTIONAL, strPhoneOptional);
                                intent.putExtra(Constants.PURCHASER, strPurchaser);
                                intent.putExtra(Constants.PURCHASER_PHONE, strPurchaserPhone);
                                intent.putExtra(Constants.CITY_ID, Utils.getPreferences(Constants.CITY_ID, context));
                                intent.putExtra(Constants.COUNTRY_ID, Utils.getPreferences(Constants.COUNTRY_ID, context));
                                intent.putExtra(Constants.CNIC, strCnic);
                                intent.putExtra(Constants.OUTLET_NAME, strOutletName);
                                intent.putExtra(Constants.OWNER_NAME, strOwnerName);
                                intent.putExtra(Constants.LATI, String.valueOf(lati));
                                intent.putExtra(Constants.LONGI, String.valueOf(longi));
                                intent.putExtra(Constants.OUTLET_ADDRESS, strOutletAdress);
                                intent.putExtra(Constants.REGION_ID, strRegionID);
                                intent.putExtra(Constants.AREA_ID, strAreaID);
                                intent.putExtra(Constants.CHANNEL_ID, strChannelId);
                                startActivity(intent);
                            }
                        }
                    }

                }

            } else {
                Toast.makeText(context, "Fill the Empty Fields", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Select Channel Types", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean phoneFormat(String Phone) {
        String Start = Phone.substring(0, 2);
        return Start.equalsIgnoreCase("03");
    }

    public void locationManagement() {

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
    public void onLocationChanged(Location location) {
//        Toast.makeText(context, "Location Updated", Toast.LENGTH_SHORT).show();
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

    public void getRequestJsonArr(String url, final String login) {
        final String encryptLogin = login;
        String link = Utils.getPreferences("link", context);
        final ProgressDialog progressDialog = new ProgressDialog(this.activity, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Working, Please Wait...");
        progressDialog.setProgressStyle(0);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this.activity.getApplicationContext());
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, "http://" + link + "/" + url, null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                if (response != null) {
                    channel = new ArrayList<>();
                    channelCode = new ArrayList<>();
                    channel.add("--Select Channel--");
                    channelCode.add("--Select Channel Code--");
                    try {
                        JSONArray jsonArray = new JSONArray(response.toString());
                        JSONObject obj;

                        for (int i = 0; i <= jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            obj = jsonObject.getJSONObject("Object");
                            String str = obj.getString("ChannelTypeName");
                            String code = obj.getString("ChannelTypeId");
                            channel.add(str);
                            channelCode.add(code);
                            // Convert ChannelCode ArrayList to Array
                            channelID = new String[channelCode.size()];
                            channelID = channelCode.toArray(channelID);
                            // Convert Channel ArrayList to Array
                            channelName = new String[channel.size()];
                            channelName = channel.toArray(channelName);
                            AppDataBase db = new AppDataBase(context, "");
                            db.open();
                            db.insertChanel(code, str, "");
                            populateSpinner();
                            db.close();
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
        channel = new ArrayList<>();
        channelCode = new ArrayList<>();
        AppDataBase db = new AppDataBase(context, "");
        db.open();
        channel = db.SpinnerChanelName();
        channelCode = db.SpinnerChanelID();

        channelID = new String[channelCode.size()];
        channelID = channelCode.toArray(channelID);
        // Convert Channel ArrayList to Array
        channelName = new String[channel.size()];
        channelName = channel.toArray(channelName);

        Spinner_channel.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, channel));
        linkViews();
    }

    @Override
    public void onBackPressed() {
        Utils.savePreferences(Constants.OUTLET_NAME, "", context);
        Utils.savePreferences(Constants.OUTLET_ADDRESS, "", context);
        Utils.savePreferences(Constants.OWNER_NAME, "", context);
        Utils.savePreferences(Constants.CNIC, "", context);
        Utils.savePreferences(Constants.PHONE, "", context);
        Utils.savePreferences(Constants.PHONE_OPTIONAL, "", context);
        Utils.savePreferences(Constants.PURCHASER, "", context);
        Utils.savePreferences(Constants.PURCHASER_PHONE, "", context);

        Intent a = new Intent(context, HomeActivity.class);
        startActivity(a);
    }
}
