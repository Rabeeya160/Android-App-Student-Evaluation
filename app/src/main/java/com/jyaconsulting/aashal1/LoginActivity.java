package com.jyaconsulting.aashal1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jyaconsulting.aashal1.database.AppDataBase;
import com.jyaconsulting.aashal1.model.UserInfoModel;
import com.jyaconsulting.aashal1.utilinterface.CMResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Activity activity;
    private Button login;
    private TextView tv_forgot, tv_register;
    private EditText et_username, et_password;
    String encode;
    CommunicationManager cm;
    CMResponse authenticate_user = new CMResponse() {
        @Override
        public void consumeResponse(String response, boolean isSuccess) throws JSONException {
            if (isSuccess == true) {
                if (response != null) {

                    Utils.savePreferences("encode", encode, context);
                    JSONObject object = new JSONObject(response);
                    String regID, reg, distID, distName, areaID, area, City, cityCode, CountryCode, salesman;
                    regID = object.getString("RegionId");
                    reg = object.getString("RegionName");
                    distID = object.getString("DistributorId");
                    distName = object.getString("DistributorName");
                    area = object.getString("AreaName");
                    areaID = object.getString("AreaId");
                    City = object.getString("CityName");
                    cityCode = object.getString("CityId");
                    CountryCode = object.getString("CountryId");
                    salesman = object.getString("Name") + " " + object.getString("FathersName");

                    AppDataBase db=new AppDataBase(context,"");
                    db.open();
                    db.delUserInfo();
                    db.insertUserInfo(salesman,regID,reg,distID,distName,areaID,area,cityCode,City,CountryCode,
                            "",encode);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                    Utils.savePreferences(Constants.SALES_MAN, salesman, context);
//                    Utils.savePreferences(Constants.REGION_ID, regID, context);
//                    Utils.savePreferences(Constants.REGION, reg, context);
//                    Utils.savePreferences(Constants.AREA_ID, areaID, context);
//                    Utils.savePreferences(Constants.AREA, area, context);
//                    Utils.savePreferences(Constants.DISTRUBUTER_ID, distID, context);
//                    Utils.savePreferences(Constants.DISTRIBUTER_NAME, distName, context);
//                    Utils.savePreferences(Constants.CITY, City, context);
//                    Utils.savePreferences(Constants.CITY_ID, cityCode, context);
//                    Utils.savePreferences(Constants.COUNTRY_ID, CountryCode, context);
                    startActivity(intent);

                } else {
                    Toast.makeText(context, "Please Enter A Valid UserName and Password", Toast.LENGTH_SHORT).show();
                }
            } else {
                String temp_encode = Utils.getPreferences("temp_encode", context);
                if (temp_encode.equalsIgnoreCase(encode)) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;
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

    private void linkViews() {
        login = findViewById(R.id.btn_login);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginUser();
                }
                return true;
            }

        });

        tv_forgot = findViewById(R.id.tv_change);
//        tv_register = findViewById(R.id.tv_register);
        login.setOnClickListener(this);
//        tv_register.setOnClickListener(this);
        tv_forgot.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
//                try {
                loginUser();

//                    RequestQueue queue = Volley.newRequestQueue(this);
//                    String url ="http://203.124.51.66:5526/api/MobileAccount/Login/";
//
//// Request a string response from the provided URL.
//                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                            new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    // Display the first 500 characters of the response string.
////                                    mTextView.setText("Response is: "+ response.substring(0,500));
//                                }
//                            }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
////                            mTextView.setText("That didn't work!");
//                        }
//                    });
//
//// Add the request to the RequestQueue.
//                    queue.add(stringRequest);
////                    cm.
////                    cm.postRequest("api/MobileAccount/Login/", param, authenticate_user);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                break;
            case R.id.tv_change:
                Intent intent = new Intent(context, SettingActivity.class);
                startActivity(intent);
                break;
//            case R.id.tv_register:
//                Toast.makeText(context, "Register Account", Toast.LENGTH_SHORT).show();
//                break;
        }

    }

    private void loginUser() {
        if (!Utils.getPreferences("link", context).isEmpty()) {
            String UserName = et_username.getText().toString();
            String Password = et_password.getText().toString();
            String link = Utils.getPreferences("link", context);
            encode = UserName + ":" + Password;
            String authorizationString = "Basic " + Base64.encodeToString((encode).getBytes(), Base64.DEFAULT); //this line is diffe
            encode=authorizationString;
//                    JSONObject param = new JSONObject();
//                    param.put("Authorization", authorizationString);
//                    param.put("Content-Type", "application/json");
            cm.getRequestJsonObj(link, "api/MobileAccount/Login/", authorizationString, authenticate_user);
        } else {
            Toast.makeText(context, "Please Set IP Address and Port\nFrom Setting", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
