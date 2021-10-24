package com.jyaconsulting.aashal1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jyaconsulting.aashal1.Utils;
import com.jyaconsulting.aashal1.model.CustomerModel;
import com.jyaconsulting.aashal1.model.NewOutletModel;
import com.jyaconsulting.aashal1.model.NewOutletProduct;
import com.jyaconsulting.aashal1.model.OrderModel;
import com.jyaconsulting.aashal1.model.PJPModel;
import com.jyaconsulting.aashal1.model.ProductDetailModel;
import com.jyaconsulting.aashal1.model.ProductModel;
import com.jyaconsulting.aashal1.model.PromoModel;
import com.jyaconsulting.aashal1.model.UserInfoModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AppDataBase {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private ABCDataBase abcDataBase;
    private String day;

    public AppDataBase(Context context, String day) {
        this.context = context;
        this.day = day;
    }

    /* Database Name and vesrion, Change in version when change in table */
    private static final String DATA_BASE_NAME = "Aashal.db";
    private static final int DATA_BASE_VERSION = 6;

    /*Step1  Table name*/
    private static final String TABLE_NAME_PRODUCT = "Products";
    private static final String TABLE_NAME_USER_INFO = "userInfo";
    private static final String TABLE_NAME_OUTLET_NAME = "outletName";
    private static final String TABLE_NAME_NEW_OUTLETS = "newOutlets";
    private static final String TABLE_NAME_NEW_OUTLET_PRODUCT = "newOutletProduct";
    private static final String TABLE_NAME_CHANEL = "Chanel";
    private static final String TABLE_NAME_TAXES = "taxes";
    private static final String TABLE_NAME_SPINNER_PRODUCT = "SpinnerProducts";
    private static final String TABLE_NAME_SUB_PRODUCT = "SubProducts";
    private static final String TABLE_NAME_OUTLETS = "outlets";
    private static final String TABLE_NAME_PRODUCT_CATAGORY = "ProductCatagory";
    private static final String TABLE_NAME_PRODUCT_DETAIL = "ProductDetail";
    private static final String TABLE_NAME_PRODUCT_PACKING = "ProductPacking";
    private static final String TABLE_NAME_PJP = "PJP";
    private static final String TABLE_NAME_PROMOTION = "promotion"; //Promotion Added into Order
    private static final String TABLE_NAME_ITEM_PROMOTION = "itemPromotion";  // All Promotion Available
    private static final String TABLE_NAME_ORDER = "ProductOrder";
    private static final String TABLE_NAME_PJP_DAYS = "PjpDays";

    /*Step2 Column name of Table Product*/
    private static final String COLUMN_NAME_PRODUCT = "Id";
    private static final String COLUMN_NAME_PRODUCT_ID = "productId";
    private static final String COLUMN_NAME_PRODUCT_NAME = "productName";


    //UserName Table's Columns
    private static final String COLUMN_NAME_USER_ID = "Id";
    private static final String COLUMN_NAME_USER_NAME = "UserName";
    private static final String COLUMN_NAME_USER_REG_ID = "regId";
    private static final String COLUMN_NAME_USER_REG_NAME = "regName";
    private static final String COLUMN_NAME_USER_DIST_ID = "distId";
    private static final String COLUMN_NAME_USER_DIST_NAME = "distName";
    private static final String COLUMN_NAME_USER_AREA_ID = "areaId";
    private static final String COLUMN_NAME_USER_AREA_NAME = "areaName";
    private static final String COLUMN_NAME_USER_CITY_ID = "cityId";
    private static final String COLUMN_NAME_USER_CITY_NAME = "cityName";
    private static final String COLUMN_NAME_USER_COUNTRY_ID = "countryId";
    private static final String COLUMN_NAME_USER_COUNTRY_NAME = "countryName";
    private static final String COLUMN_NAME_USER_ACCOUNT = "account";


    private static final String COLUMN_NAME_TAX_ID = "Id";
    private static final String COLUMN_NAME_TAX_REAL_ID = "realId";
    private static final String COLUMN_NAME_TAX_NAME = "taxName";
    private static final String COLUMN_NAME_TAX_VALUE = "value";

    private static final String COLUMN_NAME_CHANEL_ID = "Id";
    private static final String COLUMN_NAME_CHANEL_REAL_ID = "realId";
    private static final String COLUMN_NAME_CHANEL_NAME = "chanel";
    private static final String COLUMN_NAME_CHANEL_CODE = "chanelCode";


    private static final String COLUMN_NAME_DAY_ID = "Id";
    private static final String COLUMN_NAME_DAY = "day";
    private static final String COLUMN_NAME_PJP_FK = "pjpId";
    private static final String COLUMN_NAME_PJP_STATUS = "status";


    //ItemPromotion Table Column

    private static final String COLUMN_NAME_ITEM_PROMO_ID = "Id";
    private static final String COLUMN_NAME_ITEM_PROMO_REAL_ID = "realId";
    private static final String COLUMN_NAME_PARENT_ITEM_ID = "parentItemId";
    private static final String COLUMN_NAME_CHILD_ITEM_ID = "childItemId";
    private static final String COLUMN_NAME_PARENT_QTY = "parentQty";
    private static final String COLUMN_NAME_CHILD_QTY = "childQty";
    private static final String COLUMN_NAME_START_DATE = "startDate";
    private static final String COLUMN_NAME_END_DATE = "endDate";


    private static final String COLUMN_NAME_OUTLET_ID = "Id";
    private static final String COLUMN_NAME_OUTLET_REAL_ID = "realId";
    private static final String COLUMN_NAME_OUTLET_NAME = "outletName";
    private static final String COLUMN_NAME_OUTLET_PHONE = "phone";
    private static final String COLUMN_NAME_OUTLET_OWNER = "owner";
    private static final String COLUMN_NAME_OUTLET_PLACE_ADDRESS = "address";
    private static final String COLUMN_NAME_OUTLET_IS_ALL_DAYS = "IsAllDaysApplicable";

    //Column Name Table ProductCatagory
    private static final String COLUMN_NAME_CATAGORY_ID = "catagoryId";
    private static final String COLUMN_NAME_CATAGORY = "catagory";
    private static final String COLUMN_NAME_PRO_ID = "productId";

    //Column Name Table Packing
    private static final String COLUMN_NAME_PACKING_ID = "packingId";
    private static final String COLUMN_NAME_PACKING = "packing";
    private static final String COLUMN_NAME_PROD_ID = "productId";
    //    private static final String COLUMN_NAME_STATUS = "status";
    private static final String COLUMN_NAME_CATA_ID = "catagoryId";
    private static final String COLUMN_NAME_PRICE = "price";

    //Column Name Table PJP
    private static final String COLUMN_NAME_PJP_ID = "pjpId";
    private static final String COLUMN_NAME_PJP_real_ID = "pjpRealId";
    private static final String COLUMN_NAME_OUTLET = "outletName";
    private static final String COLUMN_NAME_OUTLET_ADDRESS = "outletAddress";
    private static final String COLUMN_NAME_OUTLET_STATUS = "status";
    private static final String COLUMN_NAME_OUTLET_ALTERNATE = "alternate";
    private static final String COLUMN_NAME_OUTLET_STARTDATE = "startDate";
    private static final String COLUMN_NAME_PHONE = "phone";
    private static final String COLUMN_NAME_OWNER = "OWNER";
    private static final String COLUMN_NAME_DATE = "date";
    private static final String COLUMN_NAME_SUNDAY = "sunday";
    private static final String COLUMN_NAME_MONDAY = "monday";
    private static final String COLUMN_NAME_TUESDAY = "tuesday";
    private static final String COLUMN_NAME_WEDNESDAY = "wednesday";
    private static final String COLUMN_NAME_THURSDAY = "thursday";
    private static final String COLUMN_NAME_FRIDAY = "friday";
    private static final String COLUMN_NAME_SATURDAY = "saturday";


    //OutLetName Table Column for offline order
    private static final String COLUMN_NAME_OUTLET_NAME_ID = "outletId";
    private static final String COLUMN_NAME_OUTLET_NAME_REAL_ID = "outletRealId";

    //Column Name Table ProductDetail
    private static final String COLUMN_NAME_MAIN_DETAIL_ID = "detailId";
    private static final String COLUMN_NAME_MAIN_PRODUCT_ID = "productId";
    private static final String COLUMN_NAME_SUBPRODUCT_ID = "subProductId";
    private static final String COLUMN_NAME_SUBPRODUCT = "subProduct";
    private static final String COLUMN_NAME_PRODUCT_PACKING_ID = "packingId";
    //    private static final String COLUMN_NAME_STATUS = "status";
    private static final String COLUMN_NAME_PRODUCT_PACKING = "packing";
    private static final String COLUMN_NAME_PRODUCT_CATAGORY_ID = "catagoryId";
    private static final String COLUMN_NAME_SUBCATAGORY_ID = "subCatagoryId";
    private static final String COLUMN_NAME_PRODUCT_PRICE = "price";

    private static final String COLUMN_NAME_ID = "Id";
    private static final String COLUMN_NAME_SPINNER_PRODUCT_ID = "productId";
    private static final String COLUMN_NAME_SPINNER_PRODUCT_NAME = "ProductName";

    private static final String COLUMN_NAME_SUB_PRODUCT_ID = "Id";
    private static final String COLUMN_NAME_SUB_DB_PRODUCT_ID = "SPDbId";
    private static final String COLUMN_NAME_SUB_PRODUCT_NAME = "SubProductName";
    private static final String COLUMN_NAME_SPINNER_ID = "productId";


    // Column Name Table Promotion
    private static final String COLUMN_NAME_PROMO_ID = "promoId";
    private static final String COLUMN_NAME_REAL_PROMO_ID = "promoRealId";
    private static final String COLUMN_NAME_PROMO_PRODUCT = "promoProduct";
    private static final String COLUMN_NAME_PROMO_PRODUCT_QUANTITY = "promoQuantity";
    private static final String COLUMN_NAME_PROMO_OUTLET_ID = "outletId";
    //    private static final String COLUMN_NAME_STATUS = "status";


    private static final String COLUMN_NAME_ORDER_ID = "orderId";
    private static final String COLUMN_NAME_ORDER_OUTLET_ID = "outletId";
    private static final String COLUMN_NAME_ORDER_PACKING_ID = "packingId";
    private static final String COLUMN_NAME_ORDER_PACKING = "packing";
    private static final String COLUMN_NAME_ORDER_PRODUCT = "orderProduct";
    private static final String COLUMN_NAME_ORDER_SUB_PRODUCT_ID = "SubProductId";
    private static final String COLUMN_NAME_ORDER_PRODUCT_ID = "ProductId";
    private static final String COLUMN_NAME_ORDER_PRODUCT_QUANTITY = "OrderQuantity";
    private static final String COLUMN_NAME_ORDER_PRICE = "OrderPrice";
    private static final String COLUMN_NAME_ORDER_LONGI = "longi";
    private static final String COLUMN_NAME_ORDER_LATI = "lati";
    private static final String COLUMN_NAME_ORDER_DISCOUNT = "discount";

    //NewOutlets Table's Column Names
    private static final String COLUMN_NAME_NEW_OUTLET_ID = "newOutletId";
    private static final String COLUMN_NAME_NEW_OUTLET_NAME = "newOutletName";
    private static final String COLUMN_NAME_NEW_OUTLET_ADDRESS = "newOutletAddress";
    private static final String COLUMN_NAME_NEW_OUTLET_CHANNEL_TYPE = "channelType";
    private static final String COLUMN_NAME_NEW_OUTLET_OWNER_NAME = "ownerName";
    private static final String COLUMN_NAME_NEW_OUTLET_CNIC = "newOutletCNIC";
    private static final String COLUMN_NAME_NEW_OUTLET_TEL_NO = "TelNo";
    private static final String COLUMN_NAME_NEW_OUTLET_MOBILE = "mobile";
    private static final String COLUMN_NAME_NEW_OUTLET_MOBILE2 = "mobile2";
    private static final String COLUMN_NAME_NEW_OUTLET_ALT_NO = "altNo";
    private static final String COLUMN_NAME_NEW_OUTLET_FAX = "faxNo";
    private static final String COLUMN_NAME_NEW_OUTLET_PURCHASER_NAME = "purchaserName";
    private static final String COLUMN_NAME_NEW_OUTLET_PURCHASER_MOBILE = "purchaserMobile";
    private static final String COLUMN_NAME_NEW_OUTLET_LAT = "lati";
    private static final String COLUMN_NAME_NEW_OUTLET_LONG = "longi";
    private static final String COLUMN_NAME_NEW_OUTLET_MAC_ADDRESS = "MacAddress";
    private static final String COLUMN_NAME_NEW_OUTLET_COUNTRY = "countryId";
    private static final String COLUMN_NAME_NEW_OUTLET_CITY = "cityId";
    private static final String COLUMN_NAME_NEW_OUTLET_REGION = "regionId";
    private static final String COLUMN_NAME_NEW_OUTLET_AREA = "areaId";
    private static final String COLUMN_NAME_NEW_OUTLET_DISTRIBUTOR_ID = "distributorId";

    //NewOutletProduct Table's Column Name
    private static final String COLUMN_NAME_NEW_OUTLET_PRODUCT_ID = "newOutletProductId";
    private static final String COLUMN_NAME_NEW_OUTLET_PRODUCT_GROUP_ID = "groupId";
    private static final String COLUMN_NAME_NEW_OUTLET_PRODUCT_SUBGROUP_ID = "subGroupId";
    private static final String COLUMN_NAME_NEW_OUTLET_PRODUCT_QUANTITY = "quantity";
    private static final String COLUMN_NAME_NEW_OUTLET_PRODUCT_AMOUNT = "amount";
    private static final String COLUMN_NAME_NEW_OUTLET_PRODUCT_FID = "outletId";


    /*Step3 Drop Table*/
    private static final String DROP_TABLE_PJP = "DROP TABLE IF EXISTS " + TABLE_NAME_PJP;
    private static final String DROP_TABLE_CHANEL = "DROP TABLE IF EXISTS " + TABLE_NAME_CHANEL;
    private static final String DROP_TABLE_PJP_DAYS = "DROP TABLE IF EXISTS " + TABLE_NAME_PJP_DAYS;
    private static final String DROP_TABLE_TAXES = "DROP TABLE IF EXISTS " + TABLE_NAME_TAXES;
    private static final String DROP_TABLE_OUTLET = "DROP TABLE IF EXISTS " + TABLE_NAME_OUTLETS;
    private static final String DROP_TABLE_PRODUCT_DETAIL = "DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCT_DETAIL;
    private static final String DROP_TABLE_PRODUCT = "DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCT;
    private static final String DROP_TABLE_PRODUCT_CATAGORY = "DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCT_CATAGORY;
    private static final String DROP_TABLE_PRODUCT_PACKING = "DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCT_PACKING;
    private static final String DROP_TABLE_PROMOTION = "DROP TABLE IF EXISTS " + TABLE_NAME_PROMOTION;
    private static final String DROP_TABLE_ORDER = "DROP TABLE IF EXISTS " + TABLE_NAME_ORDER;
    private static final String DROP_TABLE_SPINNER_PRODUCT = "DROP TABLE IF EXISTS " + TABLE_NAME_SPINNER_PRODUCT;
    private static final String DROP_TABLE_SUB_PRODUCT = "DROP TABLE IF EXISTS " + TABLE_NAME_SUB_PRODUCT;
    private static final String DROP_TABLE_ITEM_PROMOTION = "DROP TABLE IF EXISTS " + TABLE_NAME_ITEM_PROMOTION;
    private static final String DROP_TABLE_OUTLET_NAME = "DROP TABLE IF EXISTS " + TABLE_NAME_OUTLET_NAME;
    private static final String DROP_TABLE_NEW_OUTLETS = "DROP TABLE IF EXISTS " + TABLE_NAME_NEW_OUTLETS;
    private static final String DROP_TABLE_NEW_OUTLET_PRODUCT = "DROP TABLE IF EXISTS " + TABLE_NAME_NEW_OUTLET_PRODUCT;
    private static final String DROP_TABLE_USER_INFO = "DROP TABLE IF EXISTS " + TABLE_NAME_USER_INFO;

    /*Step4 Create Table*/
    private static final String CREATE_TABLE_PJP =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PJP + " (" +
                    COLUMN_NAME_PJP_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_PJP_real_ID + " TEXT," +
                    COLUMN_NAME_OUTLET + " TEXT," +
                    COLUMN_NAME_OUTLET_ADDRESS + " TEXT," +
                    COLUMN_NAME_PHONE + " TEXT," +
                    COLUMN_NAME_OWNER + " TEXT," +
                    COLUMN_NAME_OUTLET_ALTERNATE + " TEXT," +
                    COLUMN_NAME_OUTLET_STARTDATE + " TEXT," +
                    COLUMN_NAME_OUTLET_STATUS + " TEXT," +
                    COLUMN_NAME_OUTLET_IS_ALL_DAYS + " TEXT," +
                    COLUMN_NAME_DATE + " TEXT," +
                    COLUMN_NAME_SUNDAY + " INTEGER," +
                    COLUMN_NAME_MONDAY + " INTEGER," +
                    COLUMN_NAME_TUESDAY + " INTEGER," +
                    COLUMN_NAME_WEDNESDAY + " INTEGER," +
                    COLUMN_NAME_THURSDAY + " INTEGER," +
                    COLUMN_NAME_FRIDAY + " INTEGER," +
                    COLUMN_NAME_SATURDAY + " INTEGER)";


    /*Create Table NewOutlets*/
    private static final String CREATE_TABLE_NEW_OUTLETS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_NEW_OUTLETS + " (" +
                    COLUMN_NAME_NEW_OUTLET_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_NEW_OUTLET_NAME + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_ADDRESS + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_CHANNEL_TYPE + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_OWNER_NAME + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_CNIC + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_TEL_NO + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_MOBILE + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_MOBILE2 + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_ALT_NO + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_FAX + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_PURCHASER_NAME + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_PURCHASER_MOBILE + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_LAT + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_LONG + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_MAC_ADDRESS + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_COUNTRY + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_CITY + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_REGION + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_AREA + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_DISTRIBUTOR_ID + " TEXT)";

    /*Create Table NewOutletProduct*/
    private static final String CREATE_TABLE_NEW_OUTLET_PRODUCT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_NEW_OUTLET_PRODUCT + " (" +
                    COLUMN_NAME_NEW_OUTLET_PRODUCT_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_NEW_OUTLET_PRODUCT_GROUP_ID + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_PRODUCT_SUBGROUP_ID + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_PRODUCT_QUANTITY + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_PRODUCT_AMOUNT + " TEXT," +
                    COLUMN_NAME_NEW_OUTLET_PRODUCT_FID + " TEXT)";


    private static final String CREATE_TABLE_USER_INFo =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER_INFO + " (" +
                    COLUMN_NAME_USER_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_USER_NAME + " TEXT," +
                    COLUMN_NAME_USER_REG_ID + " TEXT," +
                    COLUMN_NAME_USER_REG_NAME + " TEXT," +
                    COLUMN_NAME_USER_DIST_ID + " TEXT," +
                    COLUMN_NAME_USER_DIST_NAME + " TEXT," +
                    COLUMN_NAME_USER_AREA_ID + " TEXT," +
                    COLUMN_NAME_USER_AREA_NAME + " TEXT," +
                    COLUMN_NAME_USER_CITY_ID + " TEXT," +
                    COLUMN_NAME_USER_CITY_NAME + " TEXT," +
                    COLUMN_NAME_USER_COUNTRY_ID + " TEXT," +
                    COLUMN_NAME_USER_COUNTRY_NAME + " TEXT," +
                    COLUMN_NAME_USER_ACCOUNT + " TEXT)";


    private static final String CREATE_TABLE_SPINNER_PRODUCT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_SPINNER_PRODUCT + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_SPINNER_PRODUCT_ID + " TEXT," +
                    COLUMN_NAME_SPINNER_PRODUCT_NAME + " TEXT)";

    private static final String CREATE_TABLE_OUTLET_NAME =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_OUTLET_NAME + " (" +
                    COLUMN_NAME_OUTLET_NAME_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_OUTLET_NAME_REAL_ID + " TEXT)";

    private static final String CREATE_TABLE_CHANEL =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CHANEL + " (" +
                    COLUMN_NAME_CHANEL_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_CHANEL_REAL_ID + " TEXT," +
                    COLUMN_NAME_CHANEL_CODE + " TEXT," +
                    COLUMN_NAME_CHANEL_NAME + " TEXT)";


    private static final String CREATE_TABLE_TAXES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TAXES + " (" +
                    COLUMN_NAME_TAX_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TAX_REAL_ID + " TEXT," +
                    COLUMN_NAME_TAX_NAME + " TEXT," +
                    COLUMN_NAME_TAX_VALUE + " TEXT)";

    private static final String CREATE_TABLE_ITEM_PROMOTION =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ITEM_PROMOTION + " (" +
                    COLUMN_NAME_ITEM_PROMO_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_ITEM_PROMO_REAL_ID + " TEXT," +
                    COLUMN_NAME_PARENT_ITEM_ID + " TEXT," +
                    COLUMN_NAME_PARENT_QTY + " TEXT," +
                    COLUMN_NAME_CHILD_ITEM_ID + " TEXT," +
                    COLUMN_NAME_CHILD_QTY + " TEXT," +
                    COLUMN_NAME_START_DATE + " TEXT," +
                    COLUMN_NAME_END_DATE + " TEXT)";


    private static final String CREATE_TABLE_PJP_DAYS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PJP_DAYS + " (" +
                    COLUMN_NAME_DAY_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_DAY + " TEXT," +
                    COLUMN_NAME_PJP_STATUS + " TEXT," +
                    COLUMN_NAME_PJP_FK + " TEXT)";


    private static final String CREATE_TABLE_SUB_PRODUCTS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_SUB_PRODUCT + " (" +
                    COLUMN_NAME_SUB_PRODUCT_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_SUB_DB_PRODUCT_ID + " TEXT," +
                    COLUMN_NAME_SUB_PRODUCT_NAME + " TEXT," +
                    COLUMN_NAME_SPINNER_ID + " TEXT)";


    private static final String CREATE_TABLE_OUTLETS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_OUTLETS + " (" +
                    COLUMN_NAME_OUTLET_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_OUTLET_REAL_ID + " TEXT," +
                    COLUMN_NAME_OUTLET_NAME + " TEXT," +
                    COLUMN_NAME_OUTLET_PLACE_ADDRESS + " TEXT," +
                    COLUMN_NAME_OUTLET_PHONE + " TEXT," +
                    COLUMN_NAME_OUTLET_OWNER + " TEXT)";


    private static final String CREATE_TABLE_PRODUCT_DETAIL =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PRODUCT_DETAIL + " (" +
                    COLUMN_NAME_MAIN_DETAIL_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_MAIN_PRODUCT_ID + " TEXT," +
                    COLUMN_NAME_SUBPRODUCT_ID + " TEXT," +
                    COLUMN_NAME_SUBPRODUCT + " TEXT," +
                    COLUMN_NAME_PRODUCT_PACKING_ID + " TEXT," +
                    COLUMN_NAME_PRODUCT_PACKING + " TEXT," +
                    COLUMN_NAME_PRODUCT_CATAGORY_ID + " TEXT," +
                    COLUMN_NAME_SUBCATAGORY_ID + " INTEGER," +
                    COLUMN_NAME_PRODUCT_PRICE + " INTEGER)";


    /*Step4 Create Table*/
    private static final String CREATE_TABLE_PRODUCT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PRODUCT + " (" +
                    COLUMN_NAME_PRODUCT + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_PRODUCT_ID + " TEXT," +
                    COLUMN_NAME_PRODUCT_NAME + " TEXT)";

    /*Step4 Create Table*/
    private static final String CREATE_TABLE_PRODUCT_CATAGORY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PRODUCT_CATAGORY + " (" +
                    COLUMN_NAME_CATAGORY_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_CATAGORY + " TEXT," +
                    COLUMN_NAME_PRO_ID + " TEXT)";

    /*Step4 Create Table*/
    private static final String CREATE_TABLE_ORDER =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ORDER + " (" +
                    COLUMN_NAME_ORDER_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_ORDER_PRODUCT_ID + " TEXT," +
                    COLUMN_NAME_ORDER_SUB_PRODUCT_ID + " TEXT," +
                    COLUMN_NAME_ORDER_PACKING_ID + " TEXT," +
                    COLUMN_NAME_ORDER_OUTLET_ID + " TEXT," +
                    COLUMN_NAME_ORDER_PACKING + " TEXT," +
                    COLUMN_NAME_ORDER_PRODUCT + " TEXT," +
                    COLUMN_NAME_ORDER_PRODUCT_QUANTITY + " TEXT," +
                    COLUMN_NAME_ORDER_LONGI + " TEXT," +
                    COLUMN_NAME_ORDER_LATI + " TEXT," +
                    COLUMN_NAME_ORDER_DISCOUNT + " TEXT," +
                    COLUMN_NAME_ORDER_PRICE + " TEXT)";

    /*Step4 Create Table*/
    private static final String CREATE_TABLE_PROMOTION =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PROMOTION + " (" +
                    COLUMN_NAME_PROMO_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_REAL_PROMO_ID + " TEXT," +
                    COLUMN_NAME_PROMO_PRODUCT + " TEXT," +
                    COLUMN_NAME_PROMO_PRODUCT_QUANTITY + " TEXT," +
                    COLUMN_NAME_PROMO_OUTLET_ID + " TEXT)";

    /*Step4 Create Table*/
    private static final String CREATE_TABLE_PRODUCT_PACKING =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PRODUCT_PACKING + " (" +
                    COLUMN_NAME_PACKING_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_PACKING + " TEXT," +
                    COLUMN_NAME_PROD_ID + " TEXT," +
                    COLUMN_NAME_CATA_ID + " TEXT," +
                    COLUMN_NAME_PRICE + " TEXT)";


    /*Step5 insert Query*/
    public long insertPJP(String outlet, String address, String phone, String owner, String status, String date, String realId, int sunday, int monday,
                          int tuesday, int wednesday, int thursday, int friday, int saturday, String isAllDays,
                          String AlterNate, String startDate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_OUTLET, outlet);
        contentValues.put(COLUMN_NAME_OUTLET_ADDRESS, address);
        contentValues.put(COLUMN_NAME_PHONE, phone);
        contentValues.put(COLUMN_NAME_OWNER, owner);
        contentValues.put(COLUMN_NAME_OUTLET_STATUS, status);
        contentValues.put(COLUMN_NAME_DATE, date);
        contentValues.put(COLUMN_NAME_SUNDAY, sunday);
        contentValues.put(COLUMN_NAME_MONDAY, monday);
        contentValues.put(COLUMN_NAME_TUESDAY, tuesday);
        contentValues.put(COLUMN_NAME_WEDNESDAY, wednesday);
        contentValues.put(COLUMN_NAME_THURSDAY, thursday);
        contentValues.put(COLUMN_NAME_FRIDAY, friday);
        contentValues.put(COLUMN_NAME_SATURDAY, saturday);
        contentValues.put(COLUMN_NAME_OUTLET_IS_ALL_DAYS, isAllDays);
        contentValues.put(COLUMN_NAME_PJP_real_ID, realId);
        contentValues.put(COLUMN_NAME_OUTLET_STARTDATE, startDate);
        contentValues.put(COLUMN_NAME_OUTLET_ALTERNATE, AlterNate);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_PJP, null, contentValues);
        return insertedId;
    }

    public long insertNewOutlets(String outletName, String address, String channelType, String owner, String cnic, String telNo,
                                 String mobile, String mobile2, String altNo, String fax, String purchaser, String purchaserMobile,
                                 String lat, String longi, String macAddress, String country, String city, String region, String area,
                                 String distributor) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_NEW_OUTLET_NAME, outletName);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_ADDRESS, address);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_CHANNEL_TYPE, channelType);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_OWNER_NAME, owner);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_CNIC, cnic);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_TEL_NO, telNo);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_MOBILE, mobile);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_MOBILE2, mobile2);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_ALT_NO, altNo);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_FAX, fax);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_PURCHASER_NAME, purchaser);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_PURCHASER_MOBILE, purchaserMobile);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_LAT, lat);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_LONG, longi);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_MAC_ADDRESS, macAddress);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_COUNTRY, country);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_CITY, city);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_REGION, region);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_AREA, area);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_DISTRIBUTOR_ID, distributor);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_NEW_OUTLETS, null, contentValues);
        return insertedId;
    }


    public long insertNewOutletProducts(String groupId, String subGroupId, String quantity, String amount, String outletId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_NEW_OUTLET_PRODUCT_GROUP_ID, groupId);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_PRODUCT_SUBGROUP_ID, subGroupId);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_PRODUCT_QUANTITY, quantity);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_PRODUCT_AMOUNT, amount);
        contentValues.put(COLUMN_NAME_NEW_OUTLET_PRODUCT_FID, outletId);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_NEW_OUTLET_PRODUCT, null, contentValues);
        return insertedId;
    }

    public long insertProductDetail(String productId, String subProductId, String subProduct, String packingId, String packing,
                                    String catagoryId, String subCatagoryId, String price) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_MAIN_PRODUCT_ID, productId);
        contentValues.put(COLUMN_NAME_SUBPRODUCT_ID, subProductId);
        contentValues.put(COLUMN_NAME_SUBPRODUCT, subProduct);
        contentValues.put(COLUMN_NAME_PRODUCT_PACKING_ID, packingId);
        contentValues.put(COLUMN_NAME_PRODUCT_PACKING, packing);
        contentValues.put(COLUMN_NAME_PRODUCT_CATAGORY_ID, catagoryId);
        contentValues.put(COLUMN_NAME_SUBCATAGORY_ID, subCatagoryId);
        contentValues.put(COLUMN_NAME_PRODUCT_PRICE, price);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_PRODUCT_DETAIL, null, contentValues);
        return insertedId;
    }

    public long insertProduct(String product, String productName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_PRODUCT_ID, product);
        contentValues.put(COLUMN_NAME_PRODUCT_NAME, productName);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_PRODUCT, null, contentValues);
        return insertedId;
    }

    public long insertTaxes(String id, String taxName, String value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_TAX_REAL_ID, id);
        contentValues.put(COLUMN_NAME_TAX_NAME, taxName);
        contentValues.put(COLUMN_NAME_TAX_VALUE, value);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_TAXES, null, contentValues);
        return insertedId;
    }

    public long insertPjpDay(String Day, String status, String fk_Id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_DAY, Day);
        contentValues.put(COLUMN_NAME_PJP_STATUS, status);
        contentValues.put(COLUMN_NAME_PJP_FK, fk_Id);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_PJP_DAYS, null, contentValues);
        return insertedId;
    }

    public long insertChanel(String id, String name, String code) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_CHANEL_REAL_ID, id);
        contentValues.put(COLUMN_NAME_CHANEL_NAME, name);
        contentValues.put(COLUMN_NAME_CHANEL_CODE, code);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_CHANEL, null, contentValues);
        return insertedId;
    }

    public long insertProductCatagory(String catagory, String productid) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_CATAGORY, catagory);
        contentValues.put(COLUMN_NAME_PRO_ID, productid);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_PRODUCT_CATAGORY, null, contentValues);
        return insertedId;
    }

    public long insertOrder(String OutletId, String itemId, String Item, String productId, String subProductId, String Product,
                            String Quantity, String Price) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_ORDER_OUTLET_ID, OutletId);
        contentValues.put(COLUMN_NAME_ORDER_PACKING_ID, itemId);
        contentValues.put(COLUMN_NAME_ORDER_PACKING, Item);
        contentValues.put(COLUMN_NAME_ORDER_PRODUCT, Product);
        contentValues.put(COLUMN_NAME_ORDER_PRODUCT_ID, productId);
        contentValues.put(COLUMN_NAME_ORDER_SUB_PRODUCT_ID, subProductId);
        contentValues.put(COLUMN_NAME_ORDER_PRODUCT_QUANTITY, Quantity);
        contentValues.put(COLUMN_NAME_ORDER_PRICE, Price);
        contentValues.put(COLUMN_NAME_ORDER_LATI, "");
        contentValues.put(COLUMN_NAME_ORDER_LONGI, "");
        contentValues.put(COLUMN_NAME_ORDER_DISCOUNT, "");
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_ORDER, null, contentValues);
        return insertedId;
    }


    public long insertUserInfo(String userName, String regId, String regName, String distId, String distName, String areaId,
                               String areaName, String cityId, String cityName, String countryId, String countryName, String account) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_USER_NAME, userName);
        contentValues.put(COLUMN_NAME_USER_REG_ID, regId);
        contentValues.put(COLUMN_NAME_USER_REG_NAME, regName);
        contentValues.put(COLUMN_NAME_USER_DIST_ID, distId);
        contentValues.put(COLUMN_NAME_USER_DIST_NAME, distName);
        contentValues.put(COLUMN_NAME_USER_AREA_ID, areaId);
        contentValues.put(COLUMN_NAME_USER_AREA_NAME, areaName);
        contentValues.put(COLUMN_NAME_USER_CITY_ID, cityId);
        contentValues.put(COLUMN_NAME_USER_CITY_NAME, cityName);
        contentValues.put(COLUMN_NAME_USER_COUNTRY_ID, countryId);
        contentValues.put(COLUMN_NAME_USER_COUNTRY_NAME, countryName);
        contentValues.put(COLUMN_NAME_USER_ACCOUNT, account);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_USER_INFO, null, contentValues);
        return insertedId;
    }

    public long insertOutlets(String outletId, String outletName, String outletAdress, String outletPhone, String outletOwner) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_OUTLET_REAL_ID, outletId);
        contentValues.put(COLUMN_NAME_OUTLET_NAME, outletName);
        contentValues.put(COLUMN_NAME_OUTLET_PLACE_ADDRESS, outletAdress);
        contentValues.put(COLUMN_NAME_OUTLET_OWNER, outletOwner);
        contentValues.put(COLUMN_NAME_OUTLET_PHONE, outletPhone);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_OUTLETS, null, contentValues);
        return insertedId;
    }

    public long insertPromotion(String id, String Product, String Quantity, String outletId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_REAL_PROMO_ID, id);
        contentValues.put(COLUMN_NAME_PROMO_PRODUCT, Product);
        contentValues.put(COLUMN_NAME_PROMO_PRODUCT_QUANTITY, Quantity);
        contentValues.put(COLUMN_NAME_PROMO_OUTLET_ID, outletId);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_PROMOTION, null, contentValues);
        return insertedId;
    }

    public long insertItemPromotion(String id, String parentId, String parentQuantity, String childId,
                                    String childQty, String start, String end) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_ITEM_PROMO_REAL_ID, id);
        contentValues.put(COLUMN_NAME_PARENT_ITEM_ID, parentId);
        contentValues.put(COLUMN_NAME_PARENT_QTY, parentQuantity);
        contentValues.put(COLUMN_NAME_CHILD_ITEM_ID, childId);
        contentValues.put(COLUMN_NAME_CHILD_QTY, childQty);
        contentValues.put(COLUMN_NAME_START_DATE, start);
        contentValues.put(COLUMN_NAME_END_DATE, end);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_ITEM_PROMOTION, null, contentValues);
        return insertedId;
    }

    public long insertPacking(String packing, String price, String product, String catagory) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_PACKING, packing);
        contentValues.put(COLUMN_NAME_PRICE, price);
        contentValues.put(COLUMN_NAME_PROD_ID, product);
        contentValues.put(COLUMN_NAME_CATA_ID, catagory);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_PRODUCT_PACKING, null, contentValues);
        return insertedId;
    }

    public long insertSpinnerProduct(String productId, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_SPINNER_PRODUCT_ID, productId);
        contentValues.put(COLUMN_NAME_SPINNER_PRODUCT_NAME, name);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_SPINNER_PRODUCT, null, contentValues);
        return insertedId;
    }

    public long insertSubProduct(String SubProductId, String name, String ProductId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_SUB_DB_PRODUCT_ID, SubProductId);
        contentValues.put(COLUMN_NAME_SUB_PRODUCT_NAME, name);
        contentValues.put(COLUMN_NAME_SPINNER_ID, ProductId);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_SUB_PRODUCT, null, contentValues);
        return insertedId;
    }

    public long insertOutlet(String Id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_OUTLET_NAME_REAL_ID, Id);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_OUTLET_NAME, null, contentValues);
        return insertedId;
    }

    public long updatePJP(String status, String Id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_OUTLET_STATUS, status);
        String id = Id;
        long updatedId = sqLiteDatabase.update(TABLE_NAME_PJP, contentValues, COLUMN_NAME_PJP_real_ID + "=?",
                new String[]{String.valueOf(id)});
        return updatedId;
    }

    public long updatePJPDay(String status, String Id, String Day) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_PJP_STATUS, status);
        String id = Id;
        long updatedId = sqLiteDatabase.update(TABLE_NAME_PJP_DAYS, contentValues, COLUMN_NAME_PJP_FK + "=? AND "
                + COLUMN_NAME_DAY + "=?", new String[]{String.valueOf(id), Day});
        return updatedId;
    }

    public long updateProduct(String product, String Id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_PRODUCT_NAME, product);
        String id = Id;
        long updatedId = sqLiteDatabase.update(TABLE_NAME_PRODUCT, contentValues, COLUMN_NAME_PJP_ID + "=?",
                new String[]{String.valueOf(id)});
        return updatedId;
    }

    public long updateProductCatagory(String catagory, String productid, String Id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_CATAGORY, catagory);
        contentValues.put(COLUMN_NAME_PRO_ID, productid);
        String id = Id;
        long updatedId = sqLiteDatabase.update(TABLE_NAME_PRODUCT_CATAGORY, contentValues, COLUMN_NAME_PJP_ID + "=?",
                new String[]{String.valueOf(id)});
        return updatedId;
    }

    public long updatePacking(String packing, String price, String product, String catagory, String Id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_PACKING, packing);
        contentValues.put(COLUMN_NAME_PRICE, price);
        contentValues.put(COLUMN_NAME_PROD_ID, product);
        contentValues.put(COLUMN_NAME_CATA_ID, catagory);
        String id = Id;
        long updatedId = sqLiteDatabase.update(TABLE_NAME_PRODUCT_PACKING, contentValues, COLUMN_NAME_PJP_ID + "=?",
                new String[]{String.valueOf(id)});
        return updatedId;
    }

    public long updateOrder(String price, String Id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_ORDER_PRICE, price);
        String id = Id;
        long updatedId = sqLiteDatabase.update(TABLE_NAME_ORDER, contentValues, COLUMN_NAME_ORDER_PACKING_ID + "=?",
                new String[]{String.valueOf(id)});
        return updatedId;
    }

    public long updateLocation(double lati, double longi, String dsc, String Id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_ORDER_LONGI, String.valueOf(longi));
        contentValues.put(COLUMN_NAME_ORDER_LATI, String.valueOf(lati));
        contentValues.put(COLUMN_NAME_ORDER_DISCOUNT, dsc);
        String id = Id;
        long updatedId = sqLiteDatabase.update(TABLE_NAME_ORDER, contentValues, COLUMN_NAME_ORDER_OUTLET_ID + "=?",
                new String[]{String.valueOf(id)});
        return updatedId;
    }

    public long updateOrderQty(String Qty, String Id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_ORDER_PRODUCT_QUANTITY, Qty);
        String id = Id;
        long updatedId = sqLiteDatabase.update(TABLE_NAME_ORDER, contentValues, COLUMN_NAME_ORDER_PACKING_ID + "=?",
                new String[]{String.valueOf(id)});
        return updatedId;
    }

//    public ArrayList<RoutTableModel> getAll() {
//        ArrayList<RoutTableModel> arrayList = new ArrayList<>();
//        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_ROUT, null, null, null, null, null, null);
//
//        while (cursor.moveToNext()) {
//            String id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ID));
//            String startAddress = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_START_ADDRESS));
//            String destination = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESTINATION));
//            String status = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_STATUS));
//            String user = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER));
//            String dateTime = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DATETIME));
////            byte[] img = cursor.getBlob(cursor.getColumnIndex(COLUMN_NAME_IMG));
//            RoutTableModel routTableModel = new RoutTableModel(id, startAddress, destination, status, user, dateTime);
//            arrayList.add(routTableModel);
////            id=id+1;
//
//        }
//        return arrayList;
//    }


    public ArrayList<String> SpinnerChanelName() {

        ArrayList<String> AllProduct = new ArrayList<>();
        AllProduct.add("--Select Channel--");
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_CHANEL, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CHANEL_NAME));
//            String Product = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCT_NAME));
//            ProductModel productModel = new ProductModel(id, Product);
            AllProduct.add(id);
//            id=id+1;
        }
        return AllProduct;
    }

    public ArrayList<String> SpinnerChanelID() {

        ArrayList<String> AllProduct = new ArrayList<>();
        AllProduct.add("--Select Chanel Type--");
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_CHANEL, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CHANEL_REAL_ID));
//            String Product = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCT_NAME));
//            ProductModel productModel = new ProductModel(id, Product);
            AllProduct.add(id);
//            id=id+1;
        }
        return AllProduct;
    }


    public ArrayList<UserInfoModel> getUserInfo() {

        ArrayList<UserInfoModel> userInfo = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_USER_INFO, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String regID, reg, distID, distName, areaID, area, City, cityCode, CountryCode, Country, Account, salesman;
            salesman = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER_NAME));
            regID = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER_REG_ID));
            reg = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER_REG_NAME));
            distID = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER_DIST_ID));
            distName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER_DIST_NAME));
            areaID = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER_AREA_ID));
            area = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER_AREA_NAME));
            cityCode = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER_CITY_ID));
            City = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER_CITY_NAME));
            CountryCode = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER_COUNTRY_ID));
            Country = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER_COUNTRY_NAME));
            Account = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER_ACCOUNT));

            UserInfoModel uim = new UserInfoModel(salesman, regID, reg, areaID, area, distID, distName, cityCode, City, CountryCode,
                    Country, Account);

            userInfo.add(uim);

        }
        return userInfo;
    }

    public ArrayList<PJPModel> getAllPjp() {

        ArrayList<PJPModel> Allpjp = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_PJP, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PJP_ID));
            String realId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PJP_real_ID));
            String outlet = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET));
            String Phone = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PHONE));
            String Address = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_ADDRESS));
            String owner = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OWNER));
            String status = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_STATUS));
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DATE));
            String AllDay = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_IS_ALL_DAYS));
            int sunday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_SUNDAY));
            int monday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_MONDAY));
            int tuesday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TUESDAY));
            int wednesday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_WEDNESDAY));
            int thursday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_THURSDAY));
            int friday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_FRIDAY));
            int saturday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_SATURDAY));
            String AlterNate = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_ALTERNATE));
            String StartDate = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_STARTDATE));

            PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, date, AllDay, StartDate, AlterNate);
            Allpjp.add(pjpModel);

            //TODO ========================= Apply Filter on PJPs on the basis of day ================================
//            if (day == "sunday") {
//                if (sunday == 1) {
//                    PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, sunday, monday, tuesday,
//                            wednesday, thursday, friday, saturday);
//                    Allpjp.add(pjpModel);
//                }
//            } else if (day == "monday") {
//                if (monday == 1) {
//                    PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, sunday, monday, tuesday,
//                            wednesday, thursday, friday, saturday);
//                    Allpjp.add(pjpModel);
//                }
//            } else if (day == "tuesday") {
//                if (tuesday == 1) {
//                    PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, sunday, monday, tuesday,
//                            wednesday, thursday, friday, saturday);
//                    Allpjp.add(pjpModel);
//                }
//            } else if (day == "wednesday") {
//                if (wednesday == 1) {
//                    PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, sunday, monday, tuesday,
//                            wednesday, thursday, friday, saturday);
//                    Allpjp.add(pjpModel);
//                }
//            } else if (day == "thursday") {
//                if (thursday == 1) {
//                    PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, sunday, monday, tuesday,
//                            wednesday, thursday, friday, saturday);
//                    Allpjp.add(pjpModel);
//                }
//            } else if (day == "friday") {
//                if (friday == 1) {
//                    PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, sunday, monday, tuesday,
//                            wednesday, thursday, friday, saturday);
//                    Allpjp.add(pjpModel);
//                }
//            } else if (day == "saturday") {
//                if (saturday == 1) {
//                    PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, sunday, monday, tuesday,
//                            wednesday, thursday, friday, saturday);
//                    Allpjp.add(pjpModel);
//                }
//            } else if (day == "all") {
//                PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, sunday, monday, tuesday,
//                        wednesday, thursday, friday, saturday);
//                Allpjp.add(pjpModel);
//            }
//            id=id+1;
        }
        //Todo =====================================================================================================================
        return Allpjp;
    }


    public ArrayList<PJPModel> getAllPjpWithDays(String days) {
//        String alyDays=Utils.getPreferences("altDays", context);
        ArrayList<PJPModel> Allpjp = new ArrayList<>();
        Cursor cursor = null;
        if (days.equalsIgnoreCase("\'All\'")) {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM  PJP , PjpDays  WHERE PJP.pjpRealId = PjpDays.pjpId", null);
        } else {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM  PJP , PjpDays  WHERE PJP.pjpRealId = PjpDays.pjpId And PjpDays.day=" + days, null);
        }
        int count = cursor.getCount();

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PJP_ID));
            String realId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PJP_real_ID));
            String outlet = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET));
            String Phone = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PHONE));
            String Address = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_ADDRESS));
            String owner = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OWNER));
            String status = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PJP_STATUS));
            String Day = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DAY));
            String AllDays = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_IS_ALL_DAYS));
            String StartDate = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_STARTDATE));
            String AlterNate = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_ALTERNATE));
//            int monday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_MONDAY));
//            int tuesday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TUESDAY));
//            int wednesday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_WEDNESDAY));
//            int thursday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_THURSDAY));
//            int friday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_FRIDAY));
//            int saturday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_SATURDAY));


            String tempDay = day; // this tempDay variable is used to store value of day if AlterNate is not empty and Reminder != 0

            // Frequency Type alternate then save the current Day in day
            if (!AlterNate.isEmpty()) {

//                Utils.savePreferences("altDays", "true", context);

                Date today = new Date();
                SimpleDateFormat date = new SimpleDateFormat("dd");
                String currentDate = date.format(today);
                int cDate = Integer.parseInt(currentDate);
                int sDate = Integer.parseInt(StartDate);
                int Reminder = (cDate - sDate) % Integer.parseInt(AlterNate);
                if (sDate == cDate && status.equalsIgnoreCase("Open")) {
                    //TODO ========= Get Current Day Name =========
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                    Date d = new Date();
                    Day = sdf.format(d);
                    if (day.equalsIgnoreCase("all")) ;
                    {
//                        day = "";
                    }
                } else {
                    if (Reminder == 0) {
                        //TODO ========= Get Current Day Name =========
                        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                        Date d = new Date();
                        Day = sdf.format(d);
                        if (day.equalsIgnoreCase("all")) ;
                        {
//                            day = "";
                        }
                    } else {
                        day = "";
                    }
                }
            }
            //TODO ========================= Apply Filter on PJPs on the basis of day ================================
            if (day.equalsIgnoreCase("sunday")) {
                if (Day.equalsIgnoreCase("Sunday")) {
                    if (!AlterNate.isEmpty()) {
                        PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, Day, "false", StartDate, AlterNate);
                        Allpjp.add(pjpModel);
                    }
                }
            } else if (day.equalsIgnoreCase("monday")) {
                if (Day.equalsIgnoreCase("Monday")) {
                    PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, Day, "false", StartDate, AlterNate);
                    Allpjp.add(pjpModel);

                }
            } else if (day.equalsIgnoreCase("tuesday")) {
                if (Day.equalsIgnoreCase("Tuesday")) {
                    PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, Day, "false", StartDate, AlterNate);
                    Allpjp.add(pjpModel);
                }
            } else if (day.equalsIgnoreCase("wednesday")) {
                if (Day.equalsIgnoreCase("Wednesday")) {
                    PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, Day, "false", StartDate, AlterNate);
                    Allpjp.add(pjpModel);
                }
            } else if (day.equalsIgnoreCase("thursday")) {
                if (Day.equalsIgnoreCase("Thursday")) {
                    PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, Day, "false", StartDate, AlterNate);
                    Allpjp.add(pjpModel);
                }
            } else if (day.equalsIgnoreCase("friday")) {
                if (Day.equalsIgnoreCase("Friday")) {
                    PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, Day, "false", StartDate, AlterNate);
                    Allpjp.add(pjpModel);
                }
            } else if (day.equalsIgnoreCase("saturday")) {
                if (Day.equalsIgnoreCase("Saturday")) {
                    PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, Day, "false", StartDate, AlterNate);
                    Allpjp.add(pjpModel);
                }
            } else if (day.equalsIgnoreCase("all")) {
                if (AllDays.equalsIgnoreCase("true")) {
                    PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, Day, "true", StartDate, AlterNate);
                    Allpjp.add(pjpModel);
                }
//                } else {
//                    PJPModel pjpModel = new PJPModel("N/A", "N/A", "N/A", "N/A", "N/A",
//                            "N/A", "N/A", "false", "N/A", "N/A");
//                    Allpjp.add(pjpModel);
//                }

            }
            day = tempDay;
//            id=id+1;
        }
        //Todo =====================================================================================================================

        if (count > 0 && Allpjp.size() == 0) {
            PJPModel pjpModel = new PJPModel("N/A", "N/A", "N/A", "N/A", "N/A", "N/A",
                    "N/A", "N/A", "N/A", "N/A");
            Allpjp.add(pjpModel);
        }
        return Allpjp;
    }

    public int getTodaysPjp(String days) {
//        String alyDays=Utils.getPreferences("altDays", context);
        ArrayList<PJPModel> Allpjp = new ArrayList<>();
        Cursor cursor = null;

            cursor = sqLiteDatabase.rawQuery("SELECT * FROM  PJP , PjpDays  WHERE PJP.pjpRealId = PjpDays.pjpId AND PjpDays." +
                    COLUMN_NAME_DAY + "="+"'"+(days)+"'", null);

        int count = cursor.getCount();

        return count;
    }
    public int getTodaysProductivePjp(String days) {
//        String alyDays=Utils.getPreferences("altDays", context);
        ArrayList<PJPModel> Allpjp = new ArrayList<>();
        Cursor cursor = null;

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM  PJP , PjpDays  WHERE PJP.pjpRealId = PjpDays.pjpId AND PjpDays." +
                COLUMN_NAME_DAY + "='"+days+ "' AND PjpDays."+COLUMN_NAME_OUTLET_STATUS+"="+"'Order Has Been Taken'", null);

        int count = cursor.getCount();

        return count;
    }
    public int getTodaysVisitedPjp(String days) {
//        String alyDays=Utils.getPreferences("altDays", context);
        ArrayList<String> Allpjp = new ArrayList<>();
        Cursor cursor = null;

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM  PJP , PjpDays  WHERE PJP.pjpRealId = PjpDays.pjpId AND PjpDays." + COLUMN_NAME_DAY + "='"+days+
                "' AND PjpDays.status!='Open'", null);

       int count=cursor.getCount();

        return count;
    }

    public ArrayList<PJPModel> getAllPjpWithList(String days) {
//        String alyDays=Utils.getPreferences("altDays", context);
        ArrayList<PJPModel> Allpjp = new ArrayList<>();
        Cursor cursor = null;
        if (days.equalsIgnoreCase("\'All\'")) {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM  PJP , PjpDays  WHERE PJP.pjpRealId = PjpDays.pjpId", null);
        } else {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM  PJP , PjpDays  WHERE PJP.pjpRealId = PjpDays.pjpId And PjpDays.day=" + days, null);
        }
        int count = cursor.getCount();

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PJP_ID));
            String realId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PJP_real_ID));
            String outlet = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET));
            String Phone = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PHONE));
            String Address = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_ADDRESS));
            String owner = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OWNER));
            String status = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PJP_STATUS));
            String Day = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DAY));
            String AllDays = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_IS_ALL_DAYS));
            String StartDate = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_STARTDATE));
            String AlterNate = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_ALTERNATE));
//            int monday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_MONDAY));
//            int tuesday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TUESDAY));
//            int wednesday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_WEDNESDAY));
//            int thursday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_THURSDAY));
//            int friday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_FRIDAY));
//            int saturday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_SATURDAY));


            String tempDay = day; // this tempDay variable is used to store value of day if AlterNate is not empty and Reminder != 0


            PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, Day, "false", StartDate, AlterNate);
            Allpjp.add(pjpModel);

        }

        return Allpjp;
    }

    public ArrayList<PJPModel> searchPjp() {

        ArrayList<PJPModel> Allpjp = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_PJP + "," +
                TABLE_NAME_PJP_DAYS +
                " WHERE " + TABLE_NAME_PJP + "." + COLUMN_NAME_PJP_real_ID + " = " + TABLE_NAME_PJP_DAYS + "." +
                COLUMN_NAME_PJP_FK, null);
        while (cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PJP_ID));
            String realId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PJP_real_ID));
            String outlet = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET));
            String Phone = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PHONE));
            String Address = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_ADDRESS));
            String owner = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OWNER));
            String status = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_STATUS));
            String Day = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DAY));
            String AllDay = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_IS_ALL_DAYS));
            String StartDate = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_STARTDATE));
            String AlterNate = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_ALTERNATE));
//            int monday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_MONDAY));
//            int tuesday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TUESDAY));
//            int wednesday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_WEDNESDAY));
//            int thursday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_THURSDAY));
//            int friday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_FRIDAY));
//            int saturday = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_SATURDAY));

            //TODO ========================= Apply Filter on PJPs on the basis of Outlet Name ================================
            if (outlet.toLowerCase().contains(day.toLowerCase())) {
                PJPModel pjpModel = new PJPModel(realId, outlet, Address, Phone, owner, status, Day, AllDay, StartDate, AlterNate);
                Allpjp.add(pjpModel);
            }
//            id=id+1;
        }
        //TODO =====================================================================================================================
        return Allpjp;
    }

    public ArrayList<ProductDetailModel> getAllproductDetail() {

        ArrayList<ProductDetailModel> productDetailModels = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_PRODUCT_DETAIL, null, null, null, null, null, null);

        while (cursor.moveToNext()) {

            String productId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_MAIN_PRODUCT_ID));
            String subProductId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SUBPRODUCT_ID));
            String subProduct = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SUBPRODUCT));
            String packingId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCT_PACKING_ID));
            String packing = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCT_PACKING));
            String catagoryId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCT_CATAGORY_ID));
            String subCatagoryId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SUBCATAGORY_ID));
            String price = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCT_PRICE));

            //TODO ========================= Apply Filter on Product DEtail on the basis of Product Id ================================
            if (!day.isEmpty()) {
                if (productId.equalsIgnoreCase(day)) {
                    ProductDetailModel detailModel = new ProductDetailModel(productId, subProduct, subProductId,
                            catagoryId, subCatagoryId, packingId, packing, price);
                    productDetailModels.add(detailModel);
                }

//            id=id+1;
            } else {
                ProductDetailModel detailModel = new ProductDetailModel(productId, subProduct, subProductId,
                        catagoryId, subCatagoryId, packingId, packing, price);
                productDetailModels.add(detailModel);
            }
        }
        //Todo =====================================================================================================================
        return productDetailModels;
    }

    //Get All ProductCatagory
    public ArrayList<ProductModel> getAllProduct() {

        ArrayList<ProductModel> AllProduct = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_PRODUCT, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCT_ID));
            String Product = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCT_NAME));
            ProductModel productModel = new ProductModel(id, Product);
            AllProduct.add(productModel);
//            id=id+1;
        }
        return AllProduct;
    }

    public int getAllProductivePjp() {
//        Cursor cursor=sqLiteDatabase.rawQuery("Select * From "+TABLE_NAME_PJP_DAYS+" Where "+COLUMN_NAME_PJP_STATUS+" \'Order\' Has Been Taken Successfully",null);
        return 0;
    }

    public ArrayList<NewOutletModel> getAllNewOutlet() {

        ArrayList<NewOutletModel> AllOutlets = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_NEW_OUTLETS, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_NAME));
            String address = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_ADDRESS));
            String channel = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_CHANNEL_TYPE));
            String owner = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_OWNER_NAME));
            String cnic = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_CNIC));
            String tel = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_TEL_NO));
            String mobile = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_MOBILE));
            String mobile2 = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_MOBILE2));
            String altNo = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_ALT_NO));
            String fax = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_FAX));
            String purchaser = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_PURCHASER_NAME));
            String purchaserMobile = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_PURCHASER_MOBILE));
            String lat = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_LAT));
            String longi = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_LONG));
            String mac = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_MAC_ADDRESS));
            String country = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_COUNTRY));
            String city = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_CITY));
            String region = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_REGION));
            String area = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_AREA));
            String distributor = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_DISTRIBUTOR_ID));
            NewOutletModel newOutletModel = new NewOutletModel(id, name, address, channel, owner, cnic, tel, mobile, mobile2, altNo,
                    fax, purchaser, purchaserMobile, lat, longi, mac, country, city, region, area, distributor);
            AllOutlets.add(newOutletModel);
//            id=id+1;
        }
        return AllOutlets;
    }

    public ArrayList<NewOutletProduct> getAllNewOutletProduct(String Id) {

        ArrayList<NewOutletProduct> AllOutletProduct = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_NEW_OUTLET_PRODUCT, null, COLUMN_NAME_NEW_OUTLET_PRODUCT_FID + "=?",
                new String[]{String.valueOf(Id)}, null, null, null);

        while (cursor.moveToNext()) {
            String group = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_PRODUCT_GROUP_ID));
            String subGroup = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_PRODUCT_SUBGROUP_ID));
            String quantity = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_PRODUCT_QUANTITY));
            String amount = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_PRODUCT_AMOUNT));
            String fId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NEW_OUTLET_PRODUCT_FID));

            NewOutletProduct newOutletProduct = new NewOutletProduct(group, subGroup, quantity, amount, fId);
            AllOutletProduct.add(newOutletProduct);
//            id=id+1;
        }
        return AllOutletProduct;
    }

    public ArrayList<String> SpinnerProductId() {

        ArrayList<String> AllProduct = new ArrayList<>();
        AllProduct.add("--Select Product Type--");
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_SPINNER_PRODUCT, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SPINNER_PRODUCT_ID));
//            String Product = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCT_NAME));
//            ProductModel productModel = new ProductModel(id, Product);
            AllProduct.add(id);
//            id=id+1;
        }
        return AllProduct;
    }

    public ArrayList<String> SpinnerProductName() {
        ArrayList<String> AllProduct = new ArrayList<>();
        AllProduct.add("--Select Product Type--");
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_SPINNER_PRODUCT, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
//            String id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ID));
            String Product = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SPINNER_PRODUCT_NAME));
//            ProductModel productModel = new ProductModel(id, Product);
            AllProduct.add(Product);
//            id=id+1;
        }
        return AllProduct;
    }

    public ArrayList<String> GST() {
        ArrayList<String> AllGst = new ArrayList<>();
        AllGst.add("--Select GST--");
        String query = "SELECT * FROM taxes WHERE taxName='GST'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        while (cursor.moveToNext()) {
//            String id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ID));
            String Product = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TAX_VALUE));
//            ProductModel productModel = new ProductModel(id, Product);
            AllGst.add(Product);
//            id=id+1;
        }
        return AllGst;
    }

    public ArrayList<String> WHT() {
        ArrayList<String> AllWht = new ArrayList<>();
        AllWht.add("--Select WHT--");
        String query = "SELECT * FROM taxes WHERE taxName='WHT'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        while (cursor.moveToNext()) {
//            String id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ID));
            String Product = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TAX_VALUE));
//            ProductModel productModel = new ProductModel(id, Product);
            AllWht.add(Product);
//            id=id+1;
        }
        return AllWht;
    }


    public void Promotion(String Id, int ParentQty) {
//        ArrayList<PromoModel> AllPromo = new ArrayList<>();
//        AllWht.add("--Select WHT--");
        String query = "SELECT * FROM itemPromotion WHERE parentItemId=" + "'" + Id + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        String id = "";
        int Qty = 0;
        while (cursor.moveToNext()) {
            id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CHILD_ITEM_ID));
            String childQty = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CHILD_QTY));
            String parentQty = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PARENT_QTY));
            Qty = ParentQty / Integer.parseInt(parentQty);
            Qty = Integer.parseInt(childQty) * Qty;
//            PromoModel promoModel = new PromoModel(id, PromoProduct,Qty);
//            AllPromo.add(promoModel);
//            id=id+1;

        }
        String Query = "SELECT * FROM " + TABLE_NAME_PRODUCT_DETAIL + " WHERE " + COLUMN_NAME_PRODUCT_PACKING_ID + "=" + "'" + id + "'";
        String PromoProduct = "";
        Cursor Cur = sqLiteDatabase.rawQuery(Query, null);
        while (Cur.moveToNext()) {
            PromoProduct = Cur.getString(Cur.getColumnIndex(COLUMN_NAME_PRODUCT_PACKING));
            String outletId = Utils.getPreferences("pjpID", context);
            insertPromotion(id, PromoProduct, String.valueOf(Qty), outletId);
        }

    }


    public ArrayList<String> SubProductsId(String id) {

        ArrayList<String> AllProduct = new ArrayList<>();
        AllProduct.add("--Select Product Code--");
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_SUB_PRODUCT, null, COLUMN_NAME_SPINNER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        while (cursor.moveToNext()) {
            String Id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SUB_DB_PRODUCT_ID));
//            String Product = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCT_NAME));
//            ProductModel productModel = new ProductModel(id, Product);
            AllProduct.add(Id);
//            id=id+1;
        }
        return AllProduct;
    }


    public ArrayList<String> AllPromotion() {

        ArrayList<String> Promo = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_ITEM_PROMOTION, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ITEM_PROMO_REAL_ID));
//            String Product = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCT_NAME));
//            ProductModel productModel = new ProductModel(id, Product);
            Promo.add(id);
//            id=id+1;
        }
        return Promo;
    }

    public ArrayList<String> SubProductsName(String id) {

        ArrayList<String> AllProduct = new ArrayList<>();
        AllProduct.add("--Select Product Subtype--");
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_SUB_PRODUCT, null, COLUMN_NAME_SPINNER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        while (cursor.moveToNext()) {
//            String id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SUB_PRODUCT_ID));
            String SubProduct = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SUB_PRODUCT_NAME));
//            ProductModel productModel = new ProductModel(id, Product);
            AllProduct.add(SubProduct);
//            id=id+1;
        }
        return AllProduct;
    }

    public ArrayList<String> MainProductsId(String id) {

        ArrayList<String> AllProduct = new ArrayList<>();
        AllProduct.add("--Select SubProduct Code--");
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_SUB_PRODUCT, null, COLUMN_NAME_SPINNER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        while (cursor.moveToNext()) {
            String Id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SPINNER_ID));
//            String SubProduct = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SUB_PRODUCT_NAME));
//            ProductModel productModel = new ProductModel(id, Product);
            AllProduct.add(Id);
//            id=id+1;
        }
        return AllProduct;
    }


    public ArrayList<CustomerModel> getAllCustomer() {

        ArrayList<CustomerModel> AllCustomer = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_OUTLETS, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_REAL_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_NAME));
            String address = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_PLACE_ADDRESS));
            String phone = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_PHONE));
            CustomerModel customerModel = new CustomerModel(id, name, address, phone);
            AllCustomer.add(customerModel);
//            id=id+1;
        }
        return AllCustomer;
    }

    //Get All ProductCatagory
    public ArrayList<ProductDetailModel> getAllProductDetail() {

        ArrayList<ProductDetailModel> AllDetailProduct = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_PRODUCT + "," +
                TABLE_NAME_PRODUCT_CATAGORY + "," + TABLE_NAME_PRODUCT_PACKING +
                " WHERE " + TABLE_NAME_PRODUCT + "." + COLUMN_NAME_PRODUCT_ID + " = " + TABLE_NAME_PRODUCT_CATAGORY + "." +
                COLUMN_NAME_PRO_ID + " AND " + TABLE_NAME_PRODUCT_CATAGORY + "." + COLUMN_NAME_CATAGORY_ID + "=" +
                TABLE_NAME_PRODUCT_PACKING + "." + COLUMN_NAME_CATA_ID, null);

        while (cursor.moveToNext()) {
            String productId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCT_ID));
            String Product = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCT_NAME));
            String CatagoryId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CATAGORY_ID));
            String Catagory = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CATAGORY));
            String PackingId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PACKING_ID));
            String Packing = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PACKING));
            String Price = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRICE));
//            ProductDetailModel productDetailModel = new ProductDetailModel(productId, CatagoryId, PackingId, Catagory, Packing, Price);
//            AllDetailProduct.add(productDetailModel);
//            id=id+1;
        }
        return AllDetailProduct;
    }

    //Get Selected Order
    public ArrayList<OrderModel> getAllOrder(String id) {

        ArrayList<OrderModel> order = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_ORDER, null, COLUMN_NAME_ORDER_OUTLET_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        while (cursor.moveToNext()) {
            String orderId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_ID));
            String ItemId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PACKING_ID));
            String item = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PACKING));
            String orderProduct = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PRODUCT));
            String Quantity = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PRODUCT_QUANTITY));
            String Amount = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PRICE));
            String productId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PRICE));
            String subProductId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PRICE));
            String lati = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_LATI));
            String longi = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_LONGI));
            String outletId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_OUTLET_ID));
            String discount = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_DISCOUNT));
            OrderModel orderModel = new OrderModel(orderId, ItemId, item, productId, subProductId, orderProduct, Quantity, Amount, lati,
                    longi, outletId, discount);
            order.add(orderModel);
//            id=id+1;
        }
        return order;
    }


    //Get All Order
    public ArrayList<OrderModel> getAllOrder() {

        ArrayList<OrderModel> order = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_ORDER, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String orderId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_ID));
            String ItemId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PACKING_ID));
            String item = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PACKING));
            String orderProduct = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PRODUCT));
            String Quantity = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PRODUCT_QUANTITY));
            String Amount = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PRICE));
            String productId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PRICE));
            String subProductId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PRICE));
            String lati = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_LATI));
            String longi = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_LONGI));
            String outletId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_OUTLET_ID));
            String discount = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_DISCOUNT));
            OrderModel orderModel = new OrderModel(orderId, ItemId, item, productId, subProductId, orderProduct, Quantity, Amount, lati,
                    longi, outletId, discount);
            order.add(orderModel);
//            id=id+1;
        }
        return order;
    }


    //Get All Order
    public ArrayList<OrderModel> getSpecificOrder(String Id) {

        ArrayList<OrderModel> order = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * From " + TABLE_NAME_ORDER + " Where " + COLUMN_NAME_ORDER_PACKING_ID + "=" + Id, null);

        while (cursor.moveToNext()) {
            String orderId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_ID));
            String ItemId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PACKING_ID));
            String item = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PACKING));
            String orderProduct = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PRODUCT));
            String Quantity = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PRODUCT_QUANTITY));
            String Amount = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PRICE));
            String productId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PRICE));
            String subProductId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_PRICE));
            String Lati = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_LATI));
            String Longi = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_LONGI));
            String outletId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_OUTLET_ID));
            String discount = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORDER_DISCOUNT));
            OrderModel orderModel = new OrderModel(orderId, ItemId, item, productId, subProductId, orderProduct, Quantity,
                    Amount, Lati, Longi, outletId, discount);
            order.add(orderModel);
//            id=id+1;
        }
        return order;
    }

    //Get All Order
    public ArrayList<PromoModel> getAllPromo(String id) {

        ArrayList<PromoModel> promo = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_PROMOTION, null, COLUMN_NAME_PROMO_OUTLET_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        while (cursor.moveToNext()) {
            String promoId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_REAL_PROMO_ID));
            String promoProduct = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PROMO_PRODUCT));
            String Quantity = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PROMO_PRODUCT_QUANTITY));
            PromoModel promoModel = new PromoModel(promoId, promoProduct, Quantity);
            promo.add(promoModel);
//            id=id+1;
        }
        return promo;
    }

    //Get All Order
    public ArrayList<String> getAllOutlet() {

        ArrayList<String> outlet = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_OUTLET_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String outletId = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OUTLET_NAME_REAL_ID));
            outlet.add(outletId);
//
        }
        return outlet;
    }

    public void delSelectedOrder(String id) {
//        sqLiteDatabase.delete(TABLE_NAME_ORDER, COLUMN_NAME_ORDER_OUTLET_ID + "=?", new String[]{id});
//        sqLiteDatabase.delete(TABLE_NAME_PROMOTION, COLUMN_NAME_PROMO_OUTLET_ID+"=?", new String[]{id});
        sqLiteDatabase.delete(TABLE_NAME_ORDER, COLUMN_NAME_ORDER_PACKING_ID + "=?", new String[]{String.valueOf(id)});
    }


    public void delOrder(String id) {
//        sqLiteDatabase.delete(TABLE_NAME_ORDER, null, null);
        sqLiteDatabase.delete(TABLE_NAME_ORDER, COLUMN_NAME_ORDER_OUTLET_ID + "=?", new String[]{String.valueOf(id)});
        sqLiteDatabase.delete(TABLE_NAME_PROMOTION, COLUMN_NAME_PROMO_OUTLET_ID + "=?", new String[]{String.valueOf(id)});
        sqLiteDatabase.delete(TABLE_NAME_OUTLET_NAME, COLUMN_NAME_OUTLET_NAME_REAL_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void delTaxes() {
        sqLiteDatabase.delete(TABLE_NAME_TAXES, null, null);
    }

    public void delSpinnerProduct() {
        sqLiteDatabase.delete(TABLE_NAME_SPINNER_PRODUCT, null, null);
    }

    public void delSubProdut() {
        sqLiteDatabase.delete(TABLE_NAME_SUB_PRODUCT, null, null);
    }

    public void delPjp() {
        sqLiteDatabase.delete(TABLE_NAME_PJP, null, null);
    }

    public void delAllPromo() {
        sqLiteDatabase.delete(TABLE_NAME_ITEM_PROMOTION, null, null);
    }

    public void delPjpDays() {
        sqLiteDatabase.delete(TABLE_NAME_PJP_DAYS, null, null);
    }

    public void delPromo() {
        sqLiteDatabase.delete(TABLE_NAME_PROMOTION, null, null);
    }

    public void delCatagory() {
        sqLiteDatabase.delete(TABLE_NAME_PRODUCT_DETAIL, null, null);
    }

    public void delPacking() {
        sqLiteDatabase.delete(TABLE_NAME_PRODUCT_PACKING, null, null);
    }

    public void delOutlet() {
        sqLiteDatabase.delete(TABLE_NAME_OUTLETS, null, null);
    }

    public void delProduct() {
        sqLiteDatabase.delete(TABLE_NAME_PRODUCT, null, null);
    }

    public void delUserInfo() {
        sqLiteDatabase.delete(TABLE_NAME_USER_INFO, null, null);
    }

    public void delNewOutLet(String Id) {
        sqLiteDatabase.delete(TABLE_NAME_NEW_OUTLET_PRODUCT, COLUMN_NAME_NEW_OUTLET_PRODUCT_FID + "=?",
                new String[]{String.valueOf(Id)});
        sqLiteDatabase.delete(TABLE_NAME_NEW_OUTLETS, COLUMN_NAME_NEW_OUTLET_ID + "=?", new String[]{String.valueOf(Id)});
    }


    public AppDataBase open() {
        abcDataBase = new ABCDataBase(context);
        sqLiteDatabase = abcDataBase.getWritableDatabase();
        return AppDataBase.this;
    }

    public void close() {
        abcDataBase.close();
    }

    public void delSingleOutlet(String Id) {
        sqLiteDatabase.delete(TABLE_NAME_OUTLET_NAME, COLUMN_NAME_OUTLET_NAME_REAL_ID + "=?",
                new String[]{String.valueOf(Id)});
    }

    private class ABCDataBase extends SQLiteOpenHelper {

        ABCDataBase(Context context) {
            super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE_PJP);
            sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT_DETAIL);
            sqLiteDatabase.execSQL(CREATE_TABLE_TAXES);
            sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT);
            sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT_CATAGORY);
            sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT_PACKING);
            sqLiteDatabase.execSQL(CREATE_TABLE_ORDER);
            sqLiteDatabase.execSQL(CREATE_TABLE_PROMOTION);
            sqLiteDatabase.execSQL(CREATE_TABLE_OUTLETS);
            sqLiteDatabase.execSQL(CREATE_TABLE_SPINNER_PRODUCT);
            sqLiteDatabase.execSQL(CREATE_TABLE_SUB_PRODUCTS);
            sqLiteDatabase.execSQL(CREATE_TABLE_PJP_DAYS);
            sqLiteDatabase.execSQL(CREATE_TABLE_ITEM_PROMOTION);
            sqLiteDatabase.execSQL(CREATE_TABLE_CHANEL);
            sqLiteDatabase.execSQL(CREATE_TABLE_OUTLET_NAME);
            sqLiteDatabase.execSQL(CREATE_TABLE_NEW_OUTLETS);
            sqLiteDatabase.execSQL(CREATE_TABLE_NEW_OUTLET_PRODUCT);
            sqLiteDatabase.execSQL(CREATE_TABLE_USER_INFo);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_TABLE_CHANEL);
            sqLiteDatabase.execSQL(DROP_TABLE_PJP);
            sqLiteDatabase.execSQL(DROP_TABLE_PJP_DAYS);
            sqLiteDatabase.execSQL(DROP_TABLE_TAXES);
            sqLiteDatabase.execSQL(DROP_TABLE_PRODUCT);
            sqLiteDatabase.execSQL(DROP_TABLE_PRODUCT_CATAGORY);
            sqLiteDatabase.execSQL(DROP_TABLE_PRODUCT_PACKING);
            sqLiteDatabase.execSQL(DROP_TABLE_PROMOTION);
            sqLiteDatabase.execSQL(DROP_TABLE_ORDER);
            sqLiteDatabase.execSQL(DROP_TABLE_PRODUCT_DETAIL);
            sqLiteDatabase.execSQL(DROP_TABLE_OUTLET);
            sqLiteDatabase.execSQL(DROP_TABLE_SPINNER_PRODUCT);
            sqLiteDatabase.execSQL(DROP_TABLE_SUB_PRODUCT);
            sqLiteDatabase.execSQL(DROP_TABLE_ITEM_PROMOTION);
            sqLiteDatabase.execSQL(DROP_TABLE_OUTLET_NAME);
            sqLiteDatabase.execSQL(DROP_TABLE_NEW_OUTLETS);
            sqLiteDatabase.execSQL(DROP_TABLE_NEW_OUTLET_PRODUCT);
            sqLiteDatabase.execSQL(DROP_TABLE_USER_INFO);
        }
    }
}
