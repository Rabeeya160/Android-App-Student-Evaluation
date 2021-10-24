package com.jyaconsulting.aashal1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jyaconsulting.aashal1.database.AppDataBase;
import com.jyaconsulting.aashal1.model.UserInfoModel;
import com.jyaconsulting.aashal1.utilinterface.CMResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Activity activity;
    private Button changePassword;
    //    private TextView tv_forgot, tv_register;
    private EditText oldPassword, newPassword;
    String encode;
    CommunicationManager cm;
    CMResponse change_password = new CMResponse() {
        @Override
        public void consumeResponse(String response, boolean isSuccess) throws JSONException {
            if (isSuccess == true) {
                if (response != null) {
//                JSONObject object = new JSONObject(response);
//                JSONArray Jarray  = object.getJSONArray();

//                for (int i = 0; i < object.length(); i++)
//                {
//                    JSONObject Jasonobject = Jarray.getJSONObject(i);
//                }

                    Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(context, "Password is changed Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        context = ChangePasswordActivity.this;
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
        changePassword = findViewById(R.id.btn_change_password);
        oldPassword = findViewById(R.id.et_old_password);
        newPassword = findViewById(R.id.et_new_password);
//        tv_forgot = findViewById(R.id.tv_forgot);
//        tv_register = findViewById(R.id.tv_register);

        changePassword.setOnClickListener(this);
//        tv_register.setOnClickListener(this);
//        tv_forgot.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String oldPassword = this.oldPassword.getText().toString();
        String newPassword = this.newPassword.getText().toString();
        String link = Utils.getPreferences("link", context);

//        String encode = Utils.getPreferences("encode", context);
        String authorizationString = encode; //this line is diffe

        try {
            JSONObject params = new JSONObject();
            params.put("UserName", encode);
            params.put("OldPassword", oldPassword);
            params.put("NewPassword", newPassword);
            cm.postRequest(link, "api/MobileAccount/PostChangePassword/", authorizationString, params, change_password);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {


        Intent a = new Intent(context, HomeActivity.class);
        startActivity(a);
    }
}
