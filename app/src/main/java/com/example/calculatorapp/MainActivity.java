package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    TableLayout tableLayout;
    TextInputEditText textInputEditText;
    int[] numButtons, operationsButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        numButtons = new int[]{
                R.id.button_0,
                R.id.button_1,
                R.id.button_2,
                R.id.button_3,
                R.id.button_4,
                R.id.button_5,
                R.id.button_6,
                R.id.button_7,
                R.id.button_8,
                R.id.button_9
        };

        operationsButtons = new int[]{
                R.id.button_plus,
                R.id.button_minus,
                R.id.button_multiply,
                R.id.button_division,
                R.id.button_equal,
                R.id.button_clear
        };



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.relative);
        tableLayout = findViewById(R.id.table);
        textInputEditText = findViewById(R.id.input);

        textInputEditText.setShowSoftInputOnFocus(false);

        relativeLayout.setBackgroundColor(Color.rgb(44,44,44));
        tableLayout.setBackgroundColor(Color.rgb(33,33,33));

        textInputEditText.setHintTextColor(Color.WHITE);

        // number buttons
        for (int i: numButtons){
            Button button = findViewById(i);

            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.weight = 1.0f;
            params.setMargins(15,15,15,15);
            button.setLayoutParams(params);

            button.setTextColor(Color.WHITE);
            button.setTextSize(50);
            button.setBackgroundTintList(null);
            button.setBackgroundResource(R.drawable.rounded_corner_numbers);
        }

        // symbol buttons
        for (int i: operationsButtons){
            Button button = findViewById(i);

            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.weight = 1.0f;
            params.setMargins(15,15,15,15);
            button.setLayoutParams(params);

            button.setTextColor(Color.GREEN);
            button.setTextSize(50);
            button.setBackgroundTintList(null);
            button.setBackgroundResource(R.drawable.rounded_corner_symbols);
        }
    }
}