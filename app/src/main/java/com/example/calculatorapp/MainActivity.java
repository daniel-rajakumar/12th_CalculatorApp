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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    RelativeLayout relativeLayout;
    TableLayout tableLayout;
    TextInputEditText textInputEditText;
    int[] numButtons, operationsButtons;
    final String EMPTY_ANSWER = "0";

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
            button.setOnLongClickListener(this);
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
            button.setOnLongClickListener(this);
        }
    }

    String equation;
    @Override
    public void onClick(View v) {
        Button button = (Button) v;

        equation = textInputEditText.getText().toString();
        switch (button.getId()){
            case R.id.button_0:         updateEquation("0", false); break;
            case R.id.button_1:         updateEquation("1", false); break;
            case R.id.button_2:         updateEquation("2", false); break;
            case R.id.button_3:         updateEquation("3", false); break;
            case R.id.button_4:         updateEquation("4", false); break;
            case R.id.button_5:         updateEquation("5", false); break;
            case R.id.button_6:         updateEquation("6", false); break;
            case R.id.button_7:         updateEquation("7", false); break;
            case R.id.button_8:         updateEquation("8", false); break;
            case R.id.button_9:         updateEquation("9", false); break;
            case R.id.button_plus:      updateEquation("+", false); break;
            case R.id.button_minus:     updateEquation("-", false); break;
            case R.id.button_multiply:  updateEquation("*", false); break;
            case R.id.button_division:  updateEquation("/", false); break;
            case R.id.button_clear:     updateEquation("c", true); break;
            case R.id.button_equal:     updateEquation(solve(equation), true);
        }
    }

    void updateEquation(String str, boolean override){
        int cursorLocation;


        if (str.equals("c")){
//            if (textInputEditText.getText().length() < 1) return;
            String text = textInputEditText.getText().toString();
            cursorLocation = textInputEditText.getSelectionStart() - 1;
            if (text.equals("0")) return;


//            System.out.printf("text: [%s][%s][%s]\n", text, cursorLocation, text.length()-1);
//            if (text.length() - 1 < 0) return;
//            System.out.printf("text: [%s][%s][%s]\n", text, cursorLocation, text.length()-1);
            if (cursorLocation < 0) return;
            textInputEditText.setText(removeCharAt(text, cursorLocation));
            textInputEditText.setSelection(cursorLocation);
            return;
        }

        if (override){
            cursorLocation = str.length();
            textInputEditText.setText(str);
            textInputEditText.setSelection(cursorLocation);
            return;
        }

        if (textInputEditText.getText().toString().equals("0")){
            textInputEditText.setText("");
        }



        cursorLocation = textInputEditText.getSelectionStart();
        String leftHalf = textInputEditText.getText().toString().substring(0, cursorLocation);
        String rightHalf = textInputEditText.getText().toString().substring(cursorLocation);

        textInputEditText.setText(String.format("%s%s%s", leftHalf, str, rightHalf));
        textInputEditText.setSelection(cursorLocation + 1);
    }




    String removeCharAt(String str, int index){
        if (index < 1) return "0";
        return str.substring(0, index) + str.substring(index + 1);
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


        if (Double.parseDouble(sum) % 1 == 0) {
            sum = (int)(Double.parseDouble(sum)) + "";
        }

        return sum;
    }

    @Override
    public boolean onLongClick(View v) {
        Button button = (Button) v;

        equation = textInputEditText.getText().toString();
        switch (button.getId()){
            case R.id.button_clear: {
                textInputEditText.setText("0");
                break;
            }

        }

        return false;
    }
}