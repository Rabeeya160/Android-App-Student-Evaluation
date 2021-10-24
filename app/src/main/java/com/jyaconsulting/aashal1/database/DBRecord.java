package com.jyaconsulting.aashal1.database;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jyaconsulting.aashal1.LoginActivity;
import com.jyaconsulting.aashal1.R;

public class DBRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbrecord);

        String[][] Pjp = {{"JYA Consulting", "Upper Mall Lahore", "04231122334", "Mr. Syed Gardaizi ",""},
                {"Allied Petrolium", "Gulberg Lahore", "04232222312", "Mr. ABC",""},
                {"Shell", "Mall Road Lahore", "04232211331", "Mr. Xyz",""},
                {"Shawaiz Enterprise.", "College Road, Lahore", "04235555655", "Mr. Shawaiz",""},
                {"DevBatch", "Kalma Chowk Lahore", "04232212215", "Mr. KAmran",""},
                {"WebMaster", "Township Lahore", "04231122334", "Mr. Jameel",""},
                {"Zerry & Merry", "TownShip Lahore", "04231122523", "Mr. Shameem",""}};
       int[][] intPJP={{1,}};

        String[] Product = {"Shampoo", "Cooking Oil", "Toothpaste", "Detergent", "Bath Soap", "Dishwash Bar"};

        String[][] Catagory = {{"Head & Shoulder", "1"},{"Panteen", "1"},{"Clear", "1"},
                {"Dalda","2"},{"Kisan","2"},{"Sultan","2"},
                {"Coolgate","3"},{"Sensodyne","3"},{"HiSalz","3"},
                {"Surf Excel","4"},{"Areal","4"},{"Bonus","4"},
                {"Luxe","5"},{"SafeGaurd","5"},{"Detol","5"},
                {"Lemon Mex","6"}};

        String[][] Packing = {{"Small","1","1","60"},{"Medium","1","1","100"},{"Large","1","1","120"},
                {"Small","1","2","60"},{"Medium","1","2","90"},{"Large","1","2","110"},
                {"Small","1","3","70"},{"Medium","1","3","110"},{"Large","1","3","150"},
                {"Small","2","4","60"},{"Medium","2","4","100"},{"Large","2","4","150"},
                {"Small","2","5","60"},{"Medium","2","5","100"},{"Large","2","5","150"},
                {"Small","2","6","60"},{"Medium","2","6","100"},{"Large","2","6","150"},
                {"Small","3","7","20"},{"Medium","3","7","40"},{"Large","3","7","80"},
                {"Small","3","8","20"},{"Medium","3","8","40"},{"Large","3","8","80"},
                {"Small","3","9","60"},{"Medium","3","9","100"},{"Large","3","9","150"},
                {"Small","4","10","10"},{"Medium","4","10","20"},{"Large","4","10","100"},
                {"Small","4","11","10"},{"Medium","4","11","20"},{"Large","4","11","90"},
                {"Small","4","12","10"},{"Medium","4","12","20"},{"Large","4","12","80"},
                {"Small","5","13","30"},{"Medium","5","13","50"},{"Large","5","13","90"},
                {"Small","5","14","30"},{"Medium","5","14","50"},{"Large","5","14","90"},
                {"Small","5","15","30"},{"Medium","5","15","50"},{"Large","5","15","90"},
                {"Small","6","16","10"},{"Medium","6","16","40"},{"Large","6","16","70"}};

        String[] Order ={"null","null","null"};
        String[] Promo ={"null","null"};
        AppDataBase appDataBase = new AppDataBase(this,"");
        appDataBase.open();
        for (int i=0;i<=6;i++){
            String pjp,address,phone,owner;
                pjp=Pjp[i][0];
                address=Pjp[i][1];
                phone=Pjp[i][2];
                owner=Pjp[i][3];
//            appDataBase.insertPJP(pjp,address,phone,owner);
        }

//        for (int i=0;i<=5;i++){
//            appDataBase.insertProduct(Product[i]);
//        }

        for (int i=0;i<=15;i++){
            String catagory,proId;
            catagory=Catagory[i][0];
            proId=Catagory[i][1];
            appDataBase.insertProductCatagory(catagory, proId);
        }

        for (int i=0;i<=47;i++){
            String packing,price,pro,cata;
            packing=Packing[i][0];
            price=Packing[i][3];
            pro=Packing[i][1];
            cata=Packing[i][2];
            appDataBase.insertPacking(packing, price, pro, cata);
        }

//        appDataBase.insertOrder(Order[0],Order[1],Order[2]);
//        appDataBase.insertPromotion(Promo[0],Promo[1]);


        appDataBase.delOrder("1");
        appDataBase.delPromo();
        appDataBase.close();

        Intent intent=new Intent(DBRecord.this, LoginActivity.class);
        startActivity(intent);
    }
}
