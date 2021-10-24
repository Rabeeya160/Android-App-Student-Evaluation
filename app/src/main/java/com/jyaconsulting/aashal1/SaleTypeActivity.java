package com.jyaconsulting.aashal1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SaleTypeActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Button btn_spotSale, btn_preSell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_type2);
        context = SaleTypeActivity.this;
        linkViews();
    }

    private void linkViews() {
        btn_preSell = findViewById(R.id.btn_pre_sell);
        btn_spotSale = findViewById(R.id.btn_spot_sale);

        btn_spotSale.setOnClickListener(this);
        btn_preSell.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_pre_sell:
                Utils.savePreferences("SaleType", "Pre", context);
                intent = new Intent(context, OutletActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_spot_sale:
                Utils.savePreferences("SaleType", "Spot", context);
                intent = new Intent(context, OutletActivity.class);
                startActivity(intent);
                break;
        }
    }
}
