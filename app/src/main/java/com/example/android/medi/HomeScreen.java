package com.example.android.medi;

/**
 * Created by Deepanshu on 3/24/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class HomeScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_screen);

    }

    public void Proceed(View view){
        Intent intent = new Intent(this,loginAsPatient.class);
        startActivity(intent);
    }
}