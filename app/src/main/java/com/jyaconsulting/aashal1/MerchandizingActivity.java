package com.jyaconsulting.aashal1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MerchandizingActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Activity activity;
    private ImageView iv_m1, iv_m2, iv_m3, iv_m4, iv_am1, iv_am2, iv_am3, iv_am4, setImage;
    private Button btn_proceed;
    private Bitmap[] pics;
    private TextView skip;
    Bitmap photo1, photo2, photo3, photo4;

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchandizing);
        context = MerchandizingActivity.this;
        activity = this;
        linkViews();

    }

    private void linkViews() {
        skip = findViewById(R.id.skip);

        // TODO ============= Underline Skip Text on XML =====================
        SpannableString content = new SpannableString(skip.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        skip.setText(content);

        // TODO ============ Create Bitmap Array To Store Pics ===============
        pics = new Bitmap[8];
        btn_proceed = findViewById(R.id.btn_proceed);

        // TODO ================== Getting Id's of XML Elements ==================
        iv_am1 = findViewById(R.id.iv_am1);
        iv_am2 = findViewById(R.id.iv_am2);
        iv_am3 = findViewById(R.id.iv_am3);
        iv_am4 = findViewById(R.id.iv_am4);

        iv_m1 = findViewById(R.id.iv_m1);
        iv_m2 = findViewById(R.id.iv_m2);
        iv_m3 = findViewById(R.id.iv_m3);
        iv_m4 = findViewById(R.id.iv_m4);

        //TODO ============ Apply OnClickListener ======================
        btn_proceed.setOnClickListener(this);
        iv_m1.setOnClickListener(this);
        iv_m2.setOnClickListener(this);
        iv_m3.setOnClickListener(this);
        iv_m4.setOnClickListener(this);
        iv_am1.setOnClickListener(this);
        iv_am2.setOnClickListener(this);
        iv_am3.setOnClickListener(this);
        iv_am4.setOnClickListener(this);
        skip.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_proceed:
                Intent intent = new Intent(context, OrderBookingActivity.class);
//                Intent intent = new Intent(context, ProductViewActivity.class);
                intent.putExtra("selectedProduct","null");
                startActivity(intent);
                break;
            case R.id.iv_m1:
                setImage=iv_m1;
                takePicture();
                break;
            case R.id.iv_m2:
                setImage=iv_m2;
                takePicture();
                break;
            case R.id.iv_m3:
                setImage=iv_m3;
                takePicture();
                break;
            case R.id.iv_m4:
                setImage=iv_m4;
                takePicture();
                break;
            case R.id.iv_am1:
                setImage=iv_am1;
                takePicture();
                break;
            case R.id.iv_am2:
                setImage=iv_am2;
                takePicture();
                break;
            case R.id.iv_am3:
                setImage=iv_am3;
                takePicture();
                break;
            case R.id.iv_am4:
                setImage=iv_am4;
                takePicture();
                break;
            case R.id.skip:
                intent=new Intent(context,OrderBookingActivity.class);
//                intent=new Intent(context,ProductViewActivity.class);
                intent.putExtra("selectedProduct","null");
                startActivity(intent);
                break;
        }
    }

    // TODO ============ Arrange Picture Which are Captured Before ===============
//    private void arrangePhoto() {
//        if (iv_am1.getDrawable()==null){
//            if (setImage==iv_m1){
//                iv_am1.setImageBitmap(pics[0]);
//            }else if (setImage==iv_m2){
//                iv_am1.setImageBitmap(pics[1]);
//            } else if (setImage==iv_m3){
//                iv_am1.setImageBitmap(pics[2]);
//            }else if (setImage==iv_m4){
//                iv_am1.setImageBitmap(pics[3]);
//            }
//        }
//        else if (iv_am2.getDrawable()==null){
//            if (setImage==iv_m1){
//                iv_am2.setImageBitmap(pics[0]);
//            }else if (setImage==iv_m2){
//                iv_am2.setImageBitmap(pics[1]);
//            } else if (setImage==iv_m3){
//                iv_am2.setImageBitmap(pics[2]);
//            }else if (setImage==iv_m4){
//                iv_am2.setImageBitmap(pics[3]);
//            }
//        }
//        else if (iv_am3.getDrawable()==null){
//            if (setImage==iv_m1){
//                iv_am3.setImageBitmap(pics[0]);
//            }else if (setImage==iv_m2){
//                iv_am3.setImageBitmap(pics[1]);
//            } else if (setImage==iv_m3){
//                iv_am3.setImageBitmap(pics[2]);
//            }else if (setImage==iv_m4){
//                iv_am3.setImageBitmap(pics[3]);
//            }
//        }
//        else if (iv_am4.getDrawable()==null){
//            if (setImage==iv_m1){
//                iv_am4.setImageBitmap(pics[0]);
//            }else if (setImage==iv_m2){
//                iv_am4.setImageBitmap(pics[1]);
//            } else if (setImage==iv_m3){
//                iv_am4.setImageBitmap(pics[2]);
//            }else if (setImage==iv_m4){
//                iv_am4.setImageBitmap(pics[3]);
//            }
//        }
//    }

    // TODO ================= Take Picture From Camera ====================
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void takePicture() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    // TODO =============== Check Camera Permission Allowed or Not =====================
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }

    // TODO =============== After Capture Picture Set this on Image View =====================
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            if (setImage == iv_m1) {
                photo1 = (Bitmap) data.getExtras().get("data");
                iv_m1.setImageBitmap(photo1);
                pics[0] = photo1;
                setImage = null;
            } else if (setImage == iv_m2) {
                photo2 = (Bitmap) data.getExtras().get("data");
                iv_m2.setImageBitmap(photo2);
                pics[1] = photo2;
                setImage = null;
            } else if (setImage == iv_m3) {
                photo3 = (Bitmap) data.getExtras().get("data");
                iv_m3.setImageBitmap(photo3);
                pics[2] = photo3;
                setImage = null;
            } else if (setImage == iv_m4) {
                photo4 = (Bitmap) data.getExtras().get("data");
                iv_m4.setImageBitmap(photo4);
                pics[3] = photo4;
                setImage = null;
            }else if (setImage == iv_am1) {
                photo4 = (Bitmap) data.getExtras().get("data");
                iv_am1.setImageBitmap(photo4);
                pics[4] = photo4;
                setImage = null;
            }else if (setImage == iv_m2) {
                photo4 = (Bitmap) data.getExtras().get("data");
                iv_am2.setImageBitmap(photo4);
                pics[5] = photo4;
                setImage = null;
            }else if (setImage == iv_am3) {
                photo4 = (Bitmap) data.getExtras().get("data");
                iv_am3.setImageBitmap(photo4);
                pics[6] = photo4;
                setImage = null;
            }else if (setImage == iv_am4) {
                photo4 = (Bitmap) data.getExtras().get("data");
                iv_am4.setImageBitmap(photo4);
                pics[7] = photo4;
                setImage = null;
            }

        }
    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(context, SelectedOutletActivity.class);
        startActivity(a);
    }
}

