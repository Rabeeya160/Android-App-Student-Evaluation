package com.jyaconsulting.aashal1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingActivity extends AppCompatActivity {
    Context context;
    EditText ip,port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        context=SettingActivity.this;
        if (!Utils.getPreferences("link",context).isEmpty()){
            EditText ip=findViewById(R.id.ip);
            EditText port=findViewById(R.id.port);
            ip.setText(Utils.getPreferences("ip",context));
            port.setText(Utils.getPreferences("port",context));
        }
        linkViews();
    }

    private void linkViews() {
        ip=findViewById(R.id.ip);
        port=findViewById(R.id.port);

        Button btn_setting_save=findViewById(R.id.btn_save_setting);

        btn_setting_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strIp=ip.getText().toString();
                String strPort=port.getText().toString();
                String link=strIp+":"+strPort;
                Utils.savePreferences("link",link,context);
                Utils.savePreferences("ip",strIp,context);
                Utils.savePreferences("port",strPort,context);
                Intent intent=new Intent(context,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
