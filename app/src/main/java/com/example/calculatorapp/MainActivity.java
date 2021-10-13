package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.relative);
        tableLayout = findViewById(R.id.table);

        relativeLayout.setBackgroundColor(Color.rgb(44,44,44));
        tableLayout.setBackgroundColor(Color.rgb(33,33,33));
    }
}