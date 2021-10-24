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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.jyaconsulting.aashal1.adapter.product_adapters.ProductAdapter;
import com.jyaconsulting.aashal1.adapter.subproduct_adapters.ProductDetailAdapter;
import com.jyaconsulting.aashal1.database.AppDataBase;
import com.jyaconsulting.aashal1.model.ProductDetailModel;
import com.jyaconsulting.aashal1.model.ProductModel;
import com.jyaconsulting.aashal1.model.UserInfoModel;
import com.jyaconsulting.aashal1.utilinterface.CMResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderBookingActivity extends AppCompatActivity implements View.OnClickListener, Serializable {
    private Context context;
    private Activity activity;
    private RecyclerView recycler_product_catagory, recyler_product_detail;
    private Button btn_review_order;
    private TextView cart_product, GrandTotal;
    private LinearLayout addProduct;
    private ProductAdapter adapter;
    private ProductDetailAdapter adapter1;
    private GridLayoutManager gridLayoutManager, gridLayoutManager2;
    AppDataBase db, databse;
    String product = null, id = "",encode;
    int i = 1;
    ArrayList<ProductDetailModel> productDetails;
    ArrayList<ProductModel> productArrayList;
    private ArrayList<String> ProductId, productId, SubProduct, SubProductId, Packing, PackingId, ItemPrice, CatagoryId, subCatagoryId;
    CommunicationManager cm;
    CMResponse getItemList = new CMResponse() {
        @Override
        public void consumeResponse(String response, boolean isSuccess) throws JSONException {
            if (isSuccess == true) {
                if (response != null) {
                    AppDataBase db = new AppDataBase(context, "");
                    db.open();
                    JSONArray jsonArray = new JSONArray(response.toString());
                    JSONObject obj;
                    ProductId = new ArrayList<>();
                    productId = new ArrayList<>();
                    SubProduct = new ArrayList<>();
                    SubProductId = new ArrayList<>();
                    Packing = new ArrayList<>();
                    PackingId = new ArrayList<>();
                    ItemPrice = new ArrayList<>();
                    CatagoryId = new ArrayList<>();
                    subCatagoryId = new ArrayList<>();
                    productArrayList = new ArrayList<>();
                    if (jsonArray != null) {
                        for (int i = 0; i <= jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String Entity = jsonObject.getString("Entity");
                            if (Entity.equals("ItemGroup")) {
                                obj = jsonObject.getJSONObject("Object");
                                String productID = obj.getString("itemGroupId");
                                String productName = obj.getString("itemGroupName");
                                ProductId.add(productID);
                                ProductModel pm = new ProductModel(productID, productName);
                                productArrayList.add(pm);
                                db.insertProduct(productID, productName);
                            } else if (Entity.equals("ItemSubGroup")) {
                                obj = jsonObject.getJSONObject("Object");
                                String productID = obj.getString("itemGroupId");
                                String subProduct = obj.getString("itemSubGroupName");
                                String subProductID = obj.getString("itemSubGroupId");
                                productId.add(productID);
                                SubProduct.add(subProduct);
                                SubProductId.add(subProductID);
                            } else if (Entity.equals("Item")) {
                                obj = jsonObject.getJSONObject("Object");
                                String productID = obj.getString("itemGroupId");
                                String subProductID = obj.getString("itemSubGroupId");
                                String itemName = obj.getString("itemName");
                                String itemId = obj.getString("itemId");
                                String unitPrice = obj.getString("UnitPrice");
                                CatagoryId.add(productID);
                                subCatagoryId.add(subProductID);
                                Packing.add(itemName);
                                PackingId.add(itemId);
                                ItemPrice.add(unitPrice);
                            }
                            if (i == jsonArray.length() - 1) {
                                db.close();
                                linkViews();
                            }
                        }
                    } else {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_booking);
        context = OrderBookingActivity.this;
        activity = this;
        cm = new CommunicationManager(activity);
        databse = new AppDataBase(context, "");
        databse.open();
        ArrayList<UserInfoModel> arr=databse.getUserInfo();
        UserInfoModel current=arr.get(0);
        encode=current.getAccount();
        if (databse.getAllproductDetail().size() == 0) {
            databse.close();
            productAndSubProductList();
        } else {
            databse.close();
            linkViews();
        }
    }

    public void selectedProduct(ArrayList<ProductDetailModel> array) {
        recyler_product_detail = findViewById(R.id.recyler_product_detail);
        gridLayoutManager2 = new GridLayoutManager(recyler_product_detail.getContext(), 1,
                GridLayoutManager.VERTICAL, false);
        recyler_product_detail.setLayoutManager(gridLayoutManager2);
        adapter1 = new ProductDetailAdapter(context, array, cart_product, GrandTotal);
        recyler_product_detail.setAdapter(adapter1);
    }

    private void productAndSubProductList() {
        String link = Utils.getPreferences("link", context);
//        String encode = Utils.getPreferences("encode", context);
        String authorizationString = encode; //this line is diffe
        cm.getRequestJsonArr(link, "api/MobileMasterData/GetData/", authorizationString, getItemList);
    }

    private void linkViews() {

        // TODO =============== TODO get Element Id's of XML Elements =================
        FloatingActionButton fab = findViewById(R.id.refresh_product);
        ImageView up = findViewById(R.id.up);
        ImageView down = findViewById(R.id.down);
        addProduct = findViewById(R.id.layout_addProduct);
        GrandTotal = findViewById(R.id.GrandTotal);
        cart_product = findViewById(R.id.cart_product);
        recycler_product_catagory = findViewById(R.id.recycler_product_catagory);
        recyler_product_detail = findViewById(R.id.recyler_product_detail);
        btn_review_order = findViewById(R.id.btn_review_order);
        String cartProduct;
        if (Utils.getPreferences("cartItem", context).isEmpty()) {
            cart_product.setText("0");
        } else {
            cartProduct = Utils.getPreferences("cartItem", context);
            cart_product.setText(cartProduct);
        }
        //TODO =============== Populate Product Catagory and Packing =====================
        AppDataBase db = new AppDataBase(context, "");
        db.open();
        if (db.getAllproductDetail().size() == 0) {
            db.close();
            populateProductPackigQuantity();
        } else {
            popolateRecylerViewfromLocaldb();
        }

        //TODO ================Apply On Click Listener =========================

        btn_review_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cart_product.getText().toString().equalsIgnoreCase("0")) {
                    Toast.makeText(context, "No Product in Cart", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, ReviewOrderActivity.class);
                    startActivity(intent);
                }
            }
        });
        fab.setOnClickListener(this);
        up.setOnClickListener(this);
        down.setOnClickListener(this);
//        addProduct.setOnClickListener(this);


    }

    private void popolateRecylerViewfromLocaldb() {

        db = new AppDataBase(context, "");
        db.open();
        gridLayoutManager = new GridLayoutManager(recycler_product_catagory.getContext(), 1,
                GridLayoutManager.VERTICAL, false);
        recycler_product_catagory.setLayoutManager(gridLayoutManager);
        adapter = new ProductAdapter(context, db.getAllProduct());
        recycler_product_catagory.setAdapter(adapter);

        //TODO ========= Check If Particular Product is Selected then Show its Catagory only otherwise show all product Catagory =======
//        product = getIntent().getStringExtra("selectedProduct");
        if (!getIntent().getStringExtra("selectedProduct").equalsIgnoreCase("null")) {
            String productId = getIntent().getStringExtra("selectedProduct");
            db = new AppDataBase(context, productId);
            db.open();
            ArrayList<ProductDetailModel> array = db.getAllproductDetail();
            if (array.size() == 0) {

            } else {
                gridLayoutManager2 = new GridLayoutManager(recyler_product_detail.getContext(), 1,
                        GridLayoutManager.VERTICAL, false);
                recyler_product_detail.setLayoutManager(gridLayoutManager2);
                adapter1 = new ProductDetailAdapter(context, array, cart_product, GrandTotal);
                recyler_product_detail.setAdapter(adapter1);
                db.close();
            }
        } else {
            gridLayoutManager2 = new GridLayoutManager(recyler_product_detail.getContext(), 1,
                    GridLayoutManager.VERTICAL, false);
            recyler_product_detail.setLayoutManager(gridLayoutManager2);
            adapter1 = new ProductDetailAdapter(context, db.getAllproductDetail(), cart_product, GrandTotal);
            recyler_product_detail.setAdapter(adapter1);
            db.close();
        }
    }

    private void populateProductPackigQuantity() {

        //TODO ============== Populate Products ===========================

        gridLayoutManager = new GridLayoutManager(recycler_product_catagory.getContext(), 1,
                GridLayoutManager.VERTICAL, false);
        recycler_product_catagory.setLayoutManager(gridLayoutManager);
        adapter = new ProductAdapter(context, productArrayList);
        recycler_product_catagory.setAdapter(adapter);

        //TODO =============== Populate Product Catagory and Packing =====================
        productDetails = new ArrayList<>();
        gridLayoutManager2 = new GridLayoutManager(recyler_product_detail.getContext(), 1,
                GridLayoutManager.VERTICAL, false);
        recyler_product_detail.setLayoutManager(gridLayoutManager2);
        String[] Product_Id, product_Id, Sub_Product, SubProduct_Id, packing, packing_Id, catagory_Id, SubCatagory_id, itemPrice;
        String Pid, pid, Sp, SpId, pck, pckId, unitPrice, Cid, sCid;
        product_Id = new String[productId.size()];
        Product_Id = new String[ProductId.size()];
        Sub_Product = new String[SubProduct.size()];
        SubProduct_Id = new String[SubProductId.size()];
        packing = new String[Packing.size()];
        packing_Id = new String[PackingId.size()];
        itemPrice = new String[ItemPrice.size()];
        catagory_Id = new String[CatagoryId.size()];
        SubCatagory_id = new String[subCatagoryId.size()];

        Product_Id = ProductId.toArray(Product_Id);
        product_Id = productId.toArray(product_Id);
        Sub_Product = SubProduct.toArray(Sub_Product);
        SubProduct_Id = SubProductId.toArray(SubProduct_Id);
        packing = Packing.toArray(packing);
        packing_Id = PackingId.toArray(packing_Id);
        itemPrice = ItemPrice.toArray(itemPrice);
        catagory_Id = CatagoryId.toArray(catagory_Id);
        SubCatagory_id = subCatagoryId.toArray(SubCatagory_id);

        for (int i = 0; i <= Product_Id.length - 1; i++) {
            pid = Product_Id[i];
            for (int j = 0; j <= SubProduct_Id.length - 1; j++) {
                if (product_Id[j] == Product_Id[i]) {
                    pid = product_Id[j];
                    Sp = Sub_Product[j];
                    SpId = SubProduct_Id[j];
                    for (int k = 0; k <= packing_Id.length - 1; k++) {
                        String p_id = SubProduct_Id[j];
                        if (SubCatagory_id[k] == SubProduct_Id[j]) {
                            pck = packing[k];
                            pckId = packing_Id[k];
                            unitPrice = itemPrice[k];
                            Cid = catagory_Id[k];
                            sCid = SubCatagory_id[k];
                            ProductDetailModel pdm = new ProductDetailModel(pid, Sp, SpId, Cid, sCid, pckId, pck, unitPrice);
                            productDetails.add(pdm);
                            AppDataBase appDataBase = new AppDataBase(context, "");
                            appDataBase.open();
                            appDataBase.insertProductDetail(pid, SpId, Sp, pckId, pck, Cid, sCid, unitPrice);
                            appDataBase.close();
                        }
                    }
                }
            }
        }

        adapter1 = new ProductDetailAdapter(context, productDetails, cart_product, GrandTotal);
        recyler_product_detail.setAdapter(adapter1);
    }

    //TODO ================= On ClickListener Method ================================

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.up:
                i = i + 1;
                // TODO ============ Scroll Up ===========

                int firstVisibleItemIndex = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (firstVisibleItemIndex > 0) {
                    gridLayoutManager.smoothScrollToPosition(recycler_product_catagory, null, firstVisibleItemIndex - 1);
                }
                break;
            case R.id.down:

                // TODO =========== Scroll Down =============
                int totalItemCount = recycler_product_catagory.getAdapter().getItemCount();
                if (totalItemCount <= 0) return;
                int lastVisibleItemIndex = gridLayoutManager.findLastVisibleItemPosition();

                if (lastVisibleItemIndex >= totalItemCount) return;
                gridLayoutManager.smoothScrollToPosition(recycler_product_catagory, null, lastVisibleItemIndex + 1);
                break;
//            case R.id.addProduct:
//                AppDataBase db = new AppDataBase(context, "");
//                db.open();
//                int count = db.getAllOrder().size();
//                if (count >= 0) {
//                    String cartItem = String.valueOf(count);
//                    cart_product.setText(cartItem);
//                }
            case R.id.refresh_product:
                AppDataBase db = new AppDataBase(context, "");
                db.open();
                db.delCatagory();
                db.delPacking();
                db.delProduct();
                db.delAllPromo();
                productAndSubProductList();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(context, MerchandizingActivity.class);
        startActivity(a);
    }
}
