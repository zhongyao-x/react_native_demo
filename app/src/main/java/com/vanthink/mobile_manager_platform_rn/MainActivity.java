package com.vanthink.mobile_manager_platform_rn;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vanthink.mobile_manager_platform_rn.activity.rn.HelloWorldActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toRNPage(View view){
        startActivity(new Intent(this, HelloWorldActivity.class));
    }
}
