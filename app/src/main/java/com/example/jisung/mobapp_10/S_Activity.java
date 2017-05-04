package com.example.jisung.mobapp_10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class S_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_);
    }
    void onClick(View v){
        if(v.getId()==R.id.b1){
            Intent intent = new Intent(S_Activity.this,MainActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(S_Activity.this,MainActivity.class);
            startActivity(intent);
        }
    }
}
