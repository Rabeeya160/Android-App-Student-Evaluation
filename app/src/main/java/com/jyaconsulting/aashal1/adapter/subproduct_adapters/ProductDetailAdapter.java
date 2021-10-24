package com.jyaconsulting.aashal1.adapter.subproduct_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.jyaconsulting.aashal1.R;
import com.jyaconsulting.aashal1.Utils;
import com.jyaconsulting.aashal1.database.AppDataBase;
import com.jyaconsulting.aashal1.model.ProductDetailModel;
import com.jyaconsulting.aashal1.viewholder.SubProductViewHolder.ProductDetailViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailAdapter extends RecyclerView.Adapter<ProductDetailViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ProductDetailModel> data;
    private ProductDetailViewHolder holder;
    private ProductDetailModel current;
    private String ProductId, subProduct, subProductId, catagoryId, SubCatagoryId, packing_id, packing, price;
    private int global_position;
    private int OrderPrice, OrderQuantity, TotalPrice, GrandTotal = 0;
    private TextView cart_product, GTotal;
    private String Quantity;
    private int i = 1;
    ArrayList<String> ItemId, Product_Id, SubProduct_Id;
    String[][] arr_order;

    public ProductDetailAdapter(Context context, List<ProductDetailModel> data, TextView cart, TextView GTotal) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        cart_product = cart;
        Quantity = null;
//        this.GTotal = GTotal;
//        this.arr_order = arr_order;
    }

    @NonNull
    @Override
    public ProductDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*This Method inflate our adapter Layout*/
        View view = inflater.inflate(R.layout.product_detail_layout, parent, false);
        holder = new ProductDetailViewHolder(view);
        return holder;
        // Write your code here
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductDetailViewHolder holder, int position) {
        /*This method will populate our recylerView with data also it will show position of each item*/
        /*access all your ViewHolder widget here */
//        this.position = position;
//        ArrayList<String> order = new ArrayList<>();
//        arr_order=new String[i][3];
        current = data.get(position);
        ProductId = current.getProductId();
        subProductId = current.getSubProductId();
        subProduct = current.getSubProduct();
        packing_id = current.getPackingId();
        packing = current.getPacking();
        price = current.getPrice();
        catagoryId = current.getCatagoryId();
        SubCatagoryId = current.getSubCatagoryId();

        holder.tvProductId.setText(ProductId);
        holder.tvSubProduct.setText(subProduct);
        holder.tvSubProductId.setText(subProduct);
        holder.tvPacking.setText(packing);
        holder.tvPackingId.setText(packing_id);
        holder.tvCatagoryId.setText(catagoryId);
        holder.tvSubCatagoryId.setText(SubCatagoryId);
        holder.tvItemPrice.setText(price);

        holder.EtQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //do your actions here that you like to perform when done is pressed
                    //Its advised to check for empty edit text and other related
                    //conditions before preforming required actions
//                    InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    global_position = holder.getAdapterPosition();
                    String Proudut = holder.tvSubProduct.getText().toString();
                    Quantity = holder.EtQuantity.getText().toString();
                    if (!Quantity.isEmpty()) {
                        String packing = holder.tvPacking.getText().toString();
                        if (!packing.equalsIgnoreCase("N/A")) {
                            notifyDataSetChanged();
//                    OrderPrice = Integer.parseInt(price);
                            AppDataBase appDataBase = new AppDataBase(context, "");
                            appDataBase.open();
                            if (appDataBase.getSpecificOrder(holder.tvPackingId.getText().toString()).size() > 0) {
                                if (cart_product.getText().toString().equalsIgnoreCase("0")) {

                                    String outletId = Utils.getPreferences("pjpID", context);
                                    long id = appDataBase.insertOrder(outletId, holder.tvPackingId.getText().toString(),
                                            holder.tvPacking.getText().toString(), holder.tvProductId.getText().toString(),
                                            holder.tvSubProductId.getText().toString(),
                                            holder.tvSubProduct.getText().toString(), Quantity, holder.tvItemPrice.getText().toString());
//                                    appDataBase.insertOutlet(outletId);
                                    appDataBase.close();
                                    Utils.savePreferences("orderId", String.valueOf(id), context);
                                    int count = Integer.parseInt(cart_product.getText().toString()) + 1;
                                    Utils.savePreferences("cartItem", String.valueOf(count), context);
                                    cart_product.setText(String.valueOf(count));

                                } else {
                                    Toast.makeText(context, "Item Already Exist in cart", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                String outletId = Utils.getPreferences("pjpID", context);
                                long id = appDataBase.insertOrder(outletId, holder.tvPackingId.getText().toString(), holder.tvPacking.getText().toString(),
                                        holder.tvProductId.getText().toString(), holder.tvSubProductId.getText().toString(),
                                        holder.tvSubProduct.getText().toString(), Quantity, holder.tvItemPrice.getText().toString());
                                appDataBase.close();
                                Utils.savePreferences("orderId", String.valueOf(id), context);
//                    GTotal.setText(String.valueOf(GrandTotal));
                                int count = Integer.parseInt(cart_product.getText().toString()) + 1;
                                Utils.savePreferences("cartItem", String.valueOf(count), context);
                                cart_product.setText(String.valueOf(count));
                            }
                        } else {
                            Toast.makeText(context, "This Item is not Available", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Enter a quantity", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });

//        holder.EtQuantity.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                           }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//                if (charSequence.length() != 0) {
//
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//
//        });
//
//        holder.tvSubProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                global_position = holder.getAdapterPosition();
//                String Proudut = holder.tvSubProduct.getText().toString();
//                Quantity = holder.EtQuantity.getText().toString();
//                notifyDataSetChanged();
//                if (Quantity.isEmpty()) {
//                    Toast.makeText(context, "Please Enter Quantity", Toast.LENGTH_SHORT).show();
//                } else {
////                    OrderPrice = Integer.parseInt(price);
//                    AppDataBase appDataBase = new AppDataBase(context, "");
//                    appDataBase.open();
//                    appDataBase.insertOrder(holder.tvPackingId.getText().toString(), holder.tvPacking.getText().toString(),
//                            holder.tvProductId.getText().toString(), holder.tvSubProductId.getText().toString(),
//                            holder.tvSubProduct.getText().toString(), Quantity, "");
//                    appDataBase.close();
//
////                    GTotal.setText(String.valueOf(GrandTotal));
//                    int count = Integer.parseInt(cart_product.getText().toString()) + 1;
//                    Utils.savePreferences("cartItem", String.valueOf(count), context);
//                    cart_product.setText(String.valueOf(count));
//                }
//
//            }
//        });
//        holder.tvPacking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                global_position = holder.getAdapterPosition();
//                String Proudut = holder.tvSubProduct.getText().toString();
//                Quantity = holder.EtQuantity.getText().toString();
//                notifyDataSetChanged();
//                if (Quantity.isEmpty()) {
//                    Toast.makeText(context, "Please Enter Quantity", Toast.LENGTH_SHORT).show();
//                } else {
////                    OrderPrice = Integer.parseInt(price);
//                    AppDataBase appDataBase = new AppDataBase(context, "");
//                    appDataBase.open();
//                    appDataBase.insertOrder(holder.tvPackingId.getText().toString(), holder.tvPacking.getText().toString(),
//                            holder.tvProductId.getText().toString(), holder.tvSubProductId.getText().toString(),
//                            holder.tvSubProduct.getText().toString(), Quantity, "");
//                    appDataBase.close();
//
////                    GTotal.setText(String.valueOf(GrandTotal));
//
//                    int count = Integer.parseInt(cart_product.getText().toString()) + 1;
//                    Utils.savePreferences("cartItem", String.valueOf(count), context);
//                    cart_product.setText(String.valueOf(count));
//                }
//
//            }
//        });
////        holder.tvSubProduct.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                String qty = holder.EtQuantity.getText().toString();
////            }
////        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


//
}
