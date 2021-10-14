package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    TableLayout tableLayout;
    TextInputEditText textInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.relative);
        tableLayout = findViewById(R.id.table);
        textInputEditText = findViewById(R.id.input);

        textInputEditText.setShowSoftInputOnFocus(false);

        relativeLayout.setBackgroundColor(Color.rgb(44,44,44));
        tableLayout.setBackgroundColor(Color.rgb(33,33,33));

        textInputEditText.setText("hello world");
    }
}