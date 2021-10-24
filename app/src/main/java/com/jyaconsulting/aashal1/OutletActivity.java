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

import com.github.clans.fab.FloatingActionButton;
import com.jyaconsulting.aashal1.adapter.PJPAdapter;
import com.jyaconsulting.aashal1.database.AppDataBase;
import com.jyaconsulting.aashal1.model.PJPModel;
import com.jyaconsulting.aashal1.model.UserInfoModel;
import com.jyaconsulting.aashal1.utilinterface.CMResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OutletActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Activity activity;
    private Button all, monday, tuesday, wednesday, thursday, friday, saturday, sunday, btn_search;
    private TextView TodayOutlets,VisitedOutlet,ProductiveOutlets;
    private EditText et_search;
    private RecyclerView recycler_outlet;
    private PJPAdapter adapter;
    private ArrayList<PJPModel> Allpjp;
    FloatingActionButton fab;
    CommunicationManager cm;
    String Day;
    String encode;
    CMResponse get_all_outlet = new CMResponse() {
        @Override
        public void consumeResponse(String response, boolean isSuccess) throws JSONException {
            // TODO =============== On Response of API Check Response is Tru or Not ===========================
            if (isSuccess == true) {

                // TODO =============== On Response of API Check Response is Null or Not ===========================
                if (response != null) {

                    // TODO =============== On Response of API Check Response not Contain Following Message ===========================
                    if (response.equalsIgnoreCase("No pjp assigned to user")) {
                        Toast.makeText(context, "No pjp assigned to user", Toast.LENGTH_SHORT).show();
                    } else {

                        AppDataBase db = new AppDataBase(context, "");
                        db.open();
                        db.delPjp();
                        db.delPjpDays();
                        db.close();
                        PJPModel pjp;

                        // TODO =============== On Response of API Convert JSON Object to JSON Array ===========================
                        JSONArray jsonArray = null;
                        Allpjp = new ArrayList<>();
                        JSONObject obj = new JSONObject(response); //TODO Convert String response to JSONObject
                        int allDay = obj.getBoolean("IsApplicableForAllDays") ? 1 : 0;
                        String AllDay = "";
                        if (allDay == 1) {
                            AllDay = "true";
                        } else {
                            AllDay = "false";
                        }

                        jsonArray = obj.getJSONArray("details");
                        // TODO =============== On Response of API Check Response jsonArray not equal to null ===========================
                        if (jsonArray != null) {
                            for (int j = 0; j <= jsonArray.length() - 1; j++) {
                                JSONObject object = jsonArray.getJSONObject(j);

//                                String frequencyType = object.getString("FrequencyType");
//                                String AlterNateDays = object.getString("AlternateDays");
//                                String StartDate = object.getString("StartDate");

                                int outletId = object.getInt("OutletId");
                                int monday = object.getBoolean("IsMonday") ? 1 : 0;
                                int tuesday = object.getBoolean("IsTuesday") ? 1 : 0;
                                int wednesday = object.getBoolean("IsWednesday") ? 1 : 0;
                                int thursday = object.getBoolean("IsThursday") ? 1 : 0;
                                int friday = object.getBoolean("IsFriday") ? 1 : 0;
                                int saturday = object.getBoolean("IsSaturday") ? 1 : 0;
                                int sunday = object.getBoolean("IsSunday") ? 1 : 0;

                                /*if (frequencyType.equalsIgnoreCase("Alternate")) {
                                    if (AlterNateDays.equalsIgnoreCase("0")) {
                                        AlterNateDays = "";
                                        StartDate = "";
                                    } else {

                                        StartDate = StartDate.substring(8, 10);

                                    }
                                } else {
                                    AlterNateDays = "";
                                    StartDate = "";
                                }*/
//                                Toast.makeText(context,StartDate,Toast.LENGTH_SHORT).show();

                                // TODO =============== Insert into SQLite PJP Table ===========================
                                AppDataBase dataBase = new AppDataBase(context, "all");
                                dataBase.open();
                                int size = dataBase.getAllPjp().size();
                                if (size == jsonArray.length()) {
                                    dataBase.close();
                                } else {
                                    if (monday == 1) {
                                        dataBase.insertPjpDay("Monday", "Open", String.valueOf(outletId));
                                    }
                                    if (tuesday == 1) {
                                        dataBase.insertPjpDay("Tuesday", "Open", String.valueOf(outletId));
                                    }
                                    if (wednesday == 1) {
                                        dataBase.insertPjpDay("Wednesday", "Open", String.valueOf(outletId));
                                    }
                                    if (thursday == 1) {
                                        dataBase.insertPjpDay("Thursday", "Open", String.valueOf(outletId));
                                    }
                                    if (friday == 1) {
                                        dataBase.insertPjpDay("Friday", "Open", String.valueOf(outletId));
                                    }
                                    if (saturday == 1) {
                                        dataBase.insertPjpDay("Saturday", "Open", String.valueOf(outletId));
                                    }
                                    if (sunday == 1) {
                                        dataBase.insertPjpDay("Sunday", "Open", String.valueOf(outletId));
                                    }
                                    if (sunday == 0 && monday == 0 && tuesday == 0 && wednesday == 0 && thursday == 0 && friday == 0 && saturday == 0) {
                                        dataBase.insertPjpDay("", "Open", String.valueOf(outletId));
                                    }

                                    Date today = new Date();
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    String dateToStr = format.format(today);
                                    dataBase.insertPJP(object.getString("OutletName"),
                                            object.getString("Address"), object.getString("Mobile"),
                                            object.getString("OwnerName"), "Open", dateToStr, String.valueOf(outletId), sunday, monday, tuesday,
                                            wednesday, thursday, friday, saturday, AllDay, "", "");
                                    dataBase.close();
                                }

                                // TODO =============== Add JsonObject values in PJPModel ===========================
                                pjp = new PJPModel(String.valueOf(outletId), object.getString("OutletName"),
                                        object.getString("Address"), object.getString("Mobile"),
                                        object.getString("OwnerName"), "Open", Day, AllDay, "", "");

                                // TODO =============== Add PJPModel in All PJP Arraylist ===========================
                                Allpjp.add(pjp);
                            }
                            AppDataBase dataBase = new AppDataBase(context, "all");
                            dataBase.open();
                            Allpjp = dataBase.getAllPjpWithDays("\'All\'");
                            dataBase.close();
                            linkViews();
                        }

//                       jsonArray.ge
                    }
                } else {
                    Toast.makeText(context, "Sorry Something went wrong", Toast.LENGTH_SHORT).show();
                }
            } else {
                fab = findViewById(R.id.refresh_pjp);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AppDataBase db = new AppDataBase(context, "");
                        db.open();
                        db.delPjp();
                        db.delPjpDays();
                        db.close();
                        getAllPJPfromOnline();

                    }
                });
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);
        context = OutletActivity.this;
        activity = this;
        cm = new CommunicationManager(activity);

        AppDataBase dataBase = new AppDataBase(context, "");
        dataBase.open();
        ArrayList<UserInfoModel> arr=dataBase.getUserInfo();
        UserInfoModel curr=arr.get(0);
        encode=curr.getAccount();
        dataBase.close();

        //TODO ========= Get Current Day Name =========
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        //TODO ======= Check If The Current Day is Monday ==========
        if (dayOfTheWeek.equalsIgnoreCase("Monday")) {

            //TODO ====Get Current Date =======
            Date today = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateToStr = format.format(today);

            //TODO ==== Get All Record in PJP =====
            AppDataBase db = new AppDataBase(context, "");
            db.open();
            List<PJPModel> pjp = db.getAllPjp();

            //TODO ==== Check if Record Exist in PJP====
            if (pjp.size() > 0) {

                //TODO === if PJP has record then check that is the Current date is equal to Saved date =====
                PJPModel current = pjp.get(0);
                String Date = current.getDay();
                if (!Date.equalsIgnoreCase(dateToStr)) {
                    db.close();
                    getAllPJPfromOnline();
                } else {
                    db.close();
                    getAllPJPfromSqlite();
                }
            }
            // TODO ==== If Record not Exist in PJP ======
            else {
                getAllPJPfromOnline();
            }
        } else {
            //TODO ==== Apply Check if PJP exist in SQLite the show it from here otherwise show from online db ====
            AppDataBase db = new AppDataBase(context, "all");
            db.open();
            int size = db.getAllPjpWithDays("\'All\'").size();
            db.close();
            if (size > 0) {
                getAllPJPfromSqlite();
            } else {
                getAllPJPfromOnline();
            }
        }
    }

    private void getAllPJPfromSqlite() {
        AppDataBase db = new AppDataBase(context, "all");
        db.open();
        Allpjp = db.getAllPjpWithDays("\'All\'");
        linkViews();
    }

    private void getAllPJPfromOnline() {
        String link = Utils.getPreferences("link", context);
//        encode = Utils.getPreferences("encode", context);
        String authorizationString = encode;
        cm.getRequestJsonObj(link, "api/MobileTransaction/GetPJP/", authorizationString, get_all_outlet);
    }

    private void linkViews() {

        all = findViewById(R.id.btn_all);
        monday = findViewById(R.id.btn_monday);
        tuesday = findViewById(R.id.btn_tuesday);
        wednesday = findViewById(R.id.btn_wednesday);
        thursday = findViewById(R.id.btn_thursday);
        friday = findViewById(R.id.btn_friday);
        saturday = findViewById(R.id.btn_saturday);
        sunday = findViewById(R.id.btn_sunday);
        btn_search = findViewById(R.id.btn_search);
        et_search = findViewById(R.id.et_search);
        recycler_outlet = findViewById(R.id.recycler_outlet);
        fab = findViewById(R.id.refresh_pjp);
        TodayOutlets=findViewById(R.id.tvTotalOutlets);
        VisitedOutlet=findViewById(R.id.tvVisitedOutlet);
        ProductiveOutlets=findViewById(R.id.tvProductiveOutlets);

        Utils.savePreferences("pjpID", "", context);
        Utils.savePreferences("outletName", "", context);
        Utils.savePreferences("OutletAddress", "", context);
        Utils.savePreferences("OutletPhone", "", context);
        Utils.savePreferences("OwnerName", "", context);

        SimpleDateFormat sdf=new SimpleDateFormat("EEEE");
        Date day=new Date();
        String DayToStr=sdf.format(day);
        AppDataBase db=new AppDataBase(context,"");
        db.open();
        int TodaysOutlet=db.getTodaysPjp(DayToStr);
        int Productive=db.getTodaysProductivePjp(DayToStr);
        int Visited=db.getTodaysVisitedPjp(DayToStr);

        TodayOutlets.setText(""+TodaysOutlet);
        VisitedOutlet.setText(""+Visited);
        ProductiveOutlets.setText(""+Productive);


        //TODO =========== Populate RecyclerView =================
        populatePJPRecyclerView("data");
//        adapter.notifyDataSetChanged();
//        appDataBase.close();
        fab.setOnClickListener(this);
        all.setOnClickListener(this);
        monday.setOnClickListener(this);
        tuesday.setOnClickListener(this);
        wednesday.setOnClickListener(this);
        thursday.setOnClickListener(this);
        friday.setOnClickListener(this);
        saturday.setOnClickListener(this);
        sunday.setOnClickListener(this);
        btn_search.setOnClickListener(this);
//        all.setOnClickListener(this);
    }

    private void populatePJPRecyclerView(String content) {
        //TODO =========== Initialize PJP RecyclerView =============
        GridLayoutManager gridLayoutManager = new GridLayoutManager(recycler_outlet.getContext(), 1, GridLayoutManager.VERTICAL, false);
        recycler_outlet.setLayoutManager(gridLayoutManager);
        adapter = new PJPAdapter(context, Allpjp);
        recycler_outlet.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_all:
                Utils.savePreferences("Day", "All", context);
                filterPJP("all", "\'All\'", "day");
                break;
            case R.id.btn_monday:
                Utils.savePreferences("Day", "Monday", context);
                filterPJP("monday", "\'Monday\'", "day");
                break;
            case R.id.btn_tuesday:
                Utils.savePreferences("Day", "Tuesday", context);
                filterPJP("tuesday", "\'Tuesday\'", "day");
                break;
            case R.id.btn_wednesday:
                Utils.savePreferences("Day", "Wednesday", context);
                filterPJP("wednesday", "\'Wednesday\'", "day");
                break;
            case R.id.btn_thursday:
                Utils.savePreferences("Day", "Thursday", context);
                filterPJP("thursday", "\'Thursday\'", "day");
                break;
            case R.id.btn_friday:
                Utils.savePreferences("Day", "Friday", context);
                filterPJP("friday", "\'Friday\'", "day");
                break;
            case R.id.btn_saturday:
                Utils.savePreferences("Day", "Saturday", context);
                filterPJP("saturday", "\'Saturday\'", "day");
                break;
            case R.id.btn_sunday:
                Utils.savePreferences("Day", "Sunday", context);
                filterPJP("sunday", "\'Sunday\'", "day");
                break;
            case R.id.btn_search:
                filterPJP(et_search.getText().toString(), "\'All\'", "outlet");
                break;
            case R.id.refresh_pjp:
                AppDataBase db = new AppDataBase(context, "");
                db.open();
                db.delPjp();
                db.delPjpDays();
                db.close();
                all.setBackgroundResource(R.drawable.tab_button_clicked);
                monday.setBackgroundResource(R.drawable.tab_button);
                tuesday.setBackgroundResource(R.drawable.tab_button);
                wednesday.setBackgroundResource(R.drawable.tab_button);
                thursday.setBackgroundResource(R.drawable.tab_button);
                friday.setBackgroundResource(R.drawable.tab_button);
                saturday.setBackgroundResource(R.drawable.tab_button);
                sunday.setBackgroundResource(R.drawable.tab_button);
                getAllPJPfromOnline();
                break;
        }
    }

    private void filterPJP(String day, String selectDay, String filterBase) {
        if (filterBase.equalsIgnoreCase("day")) {
            if (day.equalsIgnoreCase("monday")) {
                all.setBackgroundResource(R.drawable.tab_button);
                monday.setBackgroundResource(R.drawable.tab_button_clicked);
                tuesday.setBackgroundResource(R.drawable.tab_button);
                wednesday.setBackgroundResource(R.drawable.tab_button);
                thursday.setBackgroundResource(R.drawable.tab_button);
                friday.setBackgroundResource(R.drawable.tab_button);
                saturday.setBackgroundResource(R.drawable.tab_button);
                sunday.setBackgroundResource(R.drawable.tab_button);
            } else if (day.equalsIgnoreCase("tuesday")) {
                all.setBackgroundResource(R.drawable.tab_button);
                monday.setBackgroundResource(R.drawable.tab_button);
                tuesday.setBackgroundResource(R.drawable.tab_button_clicked);
                wednesday.setBackgroundResource(R.drawable.tab_button);
                thursday.setBackgroundResource(R.drawable.tab_button);
                friday.setBackgroundResource(R.drawable.tab_button);
                saturday.setBackgroundResource(R.drawable.tab_button);
                sunday.setBackgroundResource(R.drawable.tab_button);
            } else if (day.equalsIgnoreCase("wednesday")) {
                all.setBackgroundResource(R.drawable.tab_button);
                monday.setBackgroundResource(R.drawable.tab_button);
                tuesday.setBackgroundResource(R.drawable.tab_button);
                wednesday.setBackgroundResource(R.drawable.tab_button_clicked);
                thursday.setBackgroundResource(R.drawable.tab_button);
                friday.setBackgroundResource(R.drawable.tab_button);
                saturday.setBackgroundResource(R.drawable.tab_button);
                sunday.setBackgroundResource(R.drawable.tab_button);
            } else if (day.equalsIgnoreCase("thursday")) {
                all.setBackgroundResource(R.drawable.tab_button);
                monday.setBackgroundResource(R.drawable.tab_button);
                tuesday.setBackgroundResource(R.drawable.tab_button);
                wednesday.setBackgroundResource(R.drawable.tab_button);
                thursday.setBackgroundResource(R.drawable.tab_button_clicked);
                friday.setBackgroundResource(R.drawable.tab_button);
                saturday.setBackgroundResource(R.drawable.tab_button);
                sunday.setBackgroundResource(R.drawable.tab_button);
            } else if (day.equalsIgnoreCase("friday")) {
                all.setBackgroundResource(R.drawable.tab_button);
                monday.setBackgroundResource(R.drawable.tab_button);
                tuesday.setBackgroundResource(R.drawable.tab_button);
                wednesday.setBackgroundResource(R.drawable.tab_button);
                thursday.setBackgroundResource(R.drawable.tab_button);
                friday.setBackgroundResource(R.drawable.tab_button_clicked);
                saturday.setBackgroundResource(R.drawable.tab_button);
                sunday.setBackgroundResource(R.drawable.tab_button);
            } else if (day.equalsIgnoreCase("saturday")) {
                all.setBackgroundResource(R.drawable.tab_button);
                monday.setBackgroundResource(R.drawable.tab_button);
                tuesday.setBackgroundResource(R.drawable.tab_button);
                wednesday.setBackgroundResource(R.drawable.tab_button);
                thursday.setBackgroundResource(R.drawable.tab_button);
                friday.setBackgroundResource(R.drawable.tab_button);
                saturday.setBackgroundResource(R.drawable.tab_button_clicked);
                sunday.setBackgroundResource(R.drawable.tab_button);
            } else if (day.equalsIgnoreCase("sunday")) {
                all.setBackgroundResource(R.drawable.tab_button);
                monday.setBackgroundResource(R.drawable.tab_button);
                tuesday.setBackgroundResource(R.drawable.tab_button);
                wednesday.setBackgroundResource(R.drawable.tab_button);
                thursday.setBackgroundResource(R.drawable.tab_button);
                friday.setBackgroundResource(R.drawable.tab_button);
                saturday.setBackgroundResource(R.drawable.tab_button);
                sunday.setBackgroundResource(R.drawable.tab_button_clicked);
            } else if (day.equalsIgnoreCase("all")) {
                all.setBackgroundResource(R.drawable.tab_button_clicked);
                monday.setBackgroundResource(R.drawable.tab_button);
                tuesday.setBackgroundResource(R.drawable.tab_button);
                wednesday.setBackgroundResource(R.drawable.tab_button);
                thursday.setBackgroundResource(R.drawable.tab_button);
                friday.setBackgroundResource(R.drawable.tab_button);
                saturday.setBackgroundResource(R.drawable.tab_button);
                sunday.setBackgroundResource(R.drawable.tab_button);
            }

            AppDataBase db = new AppDataBase(context, day);
            db.open();
            Allpjp = db.getAllPjpWithDays("\'All\'");
            int size = Allpjp.size();
            if (size > 0) {
                populatePJPRecyclerView("data");
            } else {
                Allpjp.add(new PJPModel("", "N/A", "N/A", "N/A", "N/A", "N/A", "", "",
                        "", ""));
                populatePJPRecyclerView("null");
            }
            db.close();
        } else {
            et_search.setText("");
            AppDataBase db = new AppDataBase(context, day);
            db.open();
            Allpjp = db.searchPjp();
            int size = Allpjp.size();
            if (size > 0) {
                populatePJPRecyclerView("data");
            } else {
                Allpjp.add(new PJPModel("", "N/A", "N/A", "N/A", "N/A", "N/A", "", "",
                        "", ""));
                populatePJPRecyclerView("null");
            }
            db.close();
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(context, HomeActivity.class);
        startActivity(a);
    }
}
