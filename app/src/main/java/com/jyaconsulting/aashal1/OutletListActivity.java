package com.jyaconsulting.aashal1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.jyaconsulting.aashal1.adapter.CustomerAdapter;
import com.jyaconsulting.aashal1.database.AppDataBase;
import com.jyaconsulting.aashal1.model.PJPModel;
import com.jyaconsulting.aashal1.utilinterface.CMResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OutletListActivity extends AppCompatActivity {
    Context context;
    FloatingActionButton fab;
    Activity activity;
    RecyclerView recyclerView;
    CustomerAdapter adapter;
    CommunicationManager cm;
    ArrayList<PJPModel> customerArray;
    CMResponse customerList = new CMResponse() {
        @Override
        public void consumeResponse(String response, boolean isSuccess) throws JSONException {
            if (isSuccess == true) {
                if (response != null) {
                    customerArray = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject obj;
                    if (jsonArray != null) {
                        for (int i = 0; i <= jsonArray.length() - 1; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("Id");
                            String name = jsonObject.getString("OutletName");
                            String phone = jsonObject.getString("Mobile");
                            String Address = jsonObject.getString("Address");
                            PJPModel customerModel = new PJPModel("","","","",
                                    "","","","","","");
                            customerArray.add(customerModel);
                            AppDataBase db = new AppDataBase(context, "");
                            db.open();
                            db.insertOutlets(String.valueOf(id), name, Address, phone, "");
                            db.close();
                        }
                        linkViews();
                    }
                }
            }
            else {

                Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_list);
        context = OutletListActivity.this;
        activity = this;
        cm = new CommunicationManager(activity);

        //TODO ======= apply check if local db is empty then get customer list from Online Db ==============
//        AppDataBase db = new AppDataBase(context, "");
//        db.open();
//        if (db.getAllCustomer().size() > 0) {
//            db.close();
//            getCustomerListfromLocalDB();
//        } else {
//            db.close();
//            getCustomerList();
//        }
        linkViews();

    }

    private void getCustomerListfromLocalDB() {
        AppDataBase db = new AppDataBase(context, "all");
        db.open();
        customerArray = db.getAllPjpWithList("\'All\'");
        db.close();
//        linkViews();
    }

//    private void getCustomerList() {
//        String link = Utils.getPreferences("link", context);
//        String encode = Utils.getPreferences("encode", context);
//        String authorizationString = "Basic " + Base64.encodeToString((encode).getBytes(), Base64.DEFAULT);
//        cm.getRequestJsonArr(link, "api/MobileTransaction/GetAllOutlets?pageNo=0&pageSize=11000", authorizationString, customerList);
//    }

    private void linkViews() {
        getCustomerListfromLocalDB();

        recyclerView = findViewById(R.id.recycler_outlet_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new CustomerAdapter(context, customerArray);
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.refresh_customer);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDataBase db=new AppDataBase(context,"");
                db.open();
                db.delOutlet();
                db.close();
//                getCustomerList();
            }
        });
    }
}
