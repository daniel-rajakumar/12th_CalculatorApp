package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.android.material.textfield.TextInputEditText;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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
        textInputEditText.setText("0");

        relativeLayout.setBackgroundColor(Color.rgb(44,44,44));
        tableLayout.setBackgroundColor(Color.rgb(33,33,33));
















        // number buttons
        for (int i: numButtons){
            Button button = findViewById(i);

            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.weight = 1.0f;
            params.setMargins(15,15,15,15);
            button.setLayoutParams(params);

            button.setTextColor(Color.rgb(200, 200, 200));
            button.setTextSize(50);
            button.setBackgroundTintList(null);
            button.setBackgroundResource(R.drawable.rounded_corner_numbers);
            button.setOnClickListener(this);
        }

        // symbol buttons
        for (int i: operationsButtons){
            Button button = findViewById(i);

            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.weight = 1.0f;
            params.setMargins(15,15,15,15);
            button.setLayoutParams(params);

            button.setTextColor(Color.rgb(30,30,30));
            button.setTextSize(50);
            button.setBackgroundTintList(null);
            button.setBackgroundResource(R.drawable.rounded_corner_symbols);
            button.setOnClickListener(this);
        }
    }

    String equation;
    @Override
    public void onClick(View v) {
        Button button = (Button) v;

        equation = textInputEditText.getText().toString();
        switch (button.getId()){
            case R.id.button_0:         equation += '0' ; break;
            case R.id.button_1:         equation += '1' ; break;
            case R.id.button_2:         equation += '2' ; break;
            case R.id.button_3:         equation += '3' ; break;
            case R.id.button_4:         equation += '4' ; break;
            case R.id.button_5:         equation += '5' ; break;
            case R.id.button_6:         equation += '6' ; break;
            case R.id.button_7:         equation += '7' ; break;
            case R.id.button_8:         equation += '8' ; break;
            case R.id.button_9:         equation += '9' ; break;
            case R.id.button_plus:      equation += '+' ; break;
            case R.id.button_minus:     equation += '-' ; break;
            case R.id.button_multiply:  equation += '*' ; break;
            case R.id.button_division:  equation += '/' ; break;
            case R.id.button_clear:     equation = ""; break;
            case R.id.button_equal: {
                equation = solve(equation);
                if (Double.parseDouble(equation) % 1 == 0) {
                    equation = (int)(Double.parseDouble(equation)) + "";
                }
                break;
            }
        }

        textInputEditText.setText(equation);
    }


    double divAndMult(String str){
        double sum;
        StringTokenizer number = new StringTokenizer(str, "*/ ");
        StringTokenizer symbol = new StringTokenizer(str, "1234567890. ");

        sum = Double.parseDouble(number.nextToken());
        while (number.hasMoreTokens()){
            String sym = symbol.nextToken();
            String num = number.nextToken();

            if (sym.equals("*")) sum *= Double.parseDouble(num);
            if (sym.equals("/")) sum /= Double.parseDouble(num);
        }

        return sum;
    }

    double addAndSub(String str){
        double sum;
        StringTokenizer number = new StringTokenizer(str, "+- ");
        StringTokenizer symbol = new StringTokenizer(str, "1234567890. ");

        sum = Double.parseDouble(number.nextToken().toString());
        while (number.hasMoreTokens()){
            String sym = symbol.nextToken();
            String num = number.nextToken();

            if (sym.equals("+")) sum += Double.parseDouble(num);
            if (sym.equals("-")) sum -= Double.parseDouble(num);
        }

        return sum;
    }
    

    String solve(String equation){
        StringTokenizer group, group_sym;
        String sum;

        group = new StringTokenizer(equation, "-+ ");
        StringTokenizer groups = new StringTokenizer(equation, "-+ ");
        group_sym = new StringTokenizer(equation, "/*1234567890. ");

        sum = divAndMult(group.nextToken()) + "";

        while (group.hasMoreTokens()) {
            String gro = group_sym.nextToken();
            if (gro.equals("+")) sum += "+";
            if (gro.equals("-")) sum += "-";
            sum += divAndMult(group.nextToken());
        }

        sum = addAndSub(sum) + "";

        return sum;
    }
}