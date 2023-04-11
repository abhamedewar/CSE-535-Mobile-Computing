package com.example.mc_proj1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    Button btn1;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Page 1");

        btn1 = (Button)findViewById(R.id.btYes);
        btn2 = (Button)findViewById(R.id.btNo);
        System.out.println("hello");
    }

    public void openMain2(View view) {
        startActivity(new Intent(this, com.example.mc_proj1.MainActivity2.class));
    }
}