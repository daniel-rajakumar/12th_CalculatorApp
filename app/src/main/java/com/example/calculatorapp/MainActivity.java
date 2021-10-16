package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.android.material.textfield.TextInputEditText;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {
    RelativeLayout relativeLayout;
    TableLayout tableLayout;
    TextInputEditText textInputEditText;
    int[] numButtons, operationsButtons, specialButtons;
    final String EMPTY_ANSWER = "NONE";

    @SuppressLint("ResourceType")
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
                R.id.button_equal
        };

        specialButtons = new int[]{
                R.id.button_clear,
        };


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.relative);
        tableLayout = findViewById(R.id.table);
        textInputEditText = findViewById(R.id.input);

        textInputEditText.setShowSoftInputOnFocus(false);
        textInputEditText.setText(EMPTY_ANSWER);

        relativeLayout.setBackgroundResource(R.drawable.dark_top_background);
        tableLayout.setBackgroundResource(R.drawable.dark_bottom_background);
        textInputEditText.setTextColor(getColor(R.color.dark_answer_foreground));














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
            button.setBackgroundResource(R.drawable.dark_number_button);
            button.setOnClickListener(this);
            button.setOnLongClickListener(this);
        }

        // symbol buttons
        for (int i: operationsButtons){
            Button button = findViewById(i);

            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.weight = 1.0f;
            button.setLayoutParams(params);
            params.setMargins(15,15,15,15);
            button.setLayoutParams(params);

            button.setTextColor(getColor(R.color.dark_symbol_button_foreground));
            button.setTextSize(50);
            button.setBackgroundTintList(null);
            button.setBackgroundResource(R.drawable.dark_symbol_button);
            button.setOnClickListener(this);
            button.setOnLongClickListener(this);
        }

        // symbol buttons
        for (int i: specialButtons){
            Button button = findViewById(i);

            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.weight = 1.0f;
            params.setMargins(15,15,15,15);
            button.setLayoutParams(params);

            button.setTextColor(getColor(R.color.dark_special_symbol_button_foreground));
            button.setTextSize(50);
            button.setBackgroundTintList(null);
            button.setBackgroundResource(R.drawable.dark_special_symbol_button);
            button.setOnClickListener(this);
            button.setOnLongClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;

        String equation = textInputEditText.getText().toString();
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
            String text = textInputEditText.getText().toString();
            cursorLocation = textInputEditText.getSelectionStart() - 1;
            if (cursorLocation < 0 || text.equals(EMPTY_ANSWER))  return;
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

        if (textInputEditText.getText().toString().equals(EMPTY_ANSWER)){
            textInputEditText.setText("");
        }



        cursorLocation = textInputEditText.getSelectionStart();
        String leftHalf = textInputEditText.getText().toString().substring(0, cursorLocation);
        String rightHalf = textInputEditText.getText().toString().substring(cursorLocation);

        textInputEditText.setText(String.format("%s%s%s", leftHalf, str, rightHalf));
        textInputEditText.setSelection(cursorLocation + 1);
    }




    String removeCharAt(String str, int index){
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

        switch (button.getId()){
            case R.id.button_clear: {
                textInputEditText.setText(EMPTY_ANSWER);
                textInputEditText.setSelection(EMPTY_ANSWER.length());
            }
        }

        return false;
    }

    SwitchCompat aSwitch;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nightmode, menu);

        aSwitch = menu.findItem(R.id.app_bar_switch)
                      .setActionView(R.layout.switch_item)
                      .getActionView()
                      .findViewById(R.id.theme_switch);

        aSwitch.setBackgroundTintList(null);

        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                textInputEditText.setTextColor(getColor(R.color.dark_answer_foreground));
                tableLayout.setBackgroundResource(R.drawable.dark_bottom_background);
                textInputEditText.setBackgroundResource(R.drawable.dark_top_background);
                relativeLayout.setBackgroundResource(R.drawable.dark_top_background);

                for (int i: numButtons) {
                    Button button = findViewById(i);
                    button.setTextColor(getColor(R.color.dark_number_foreground));
                }

                for (int i: operationsButtons){
                    Button button = findViewById(i);
                    button.setBackgroundResource(R.drawable.dark_symbol_button);
                    button.setTextColor(getColor(R.color.dark_symbol_button_foreground));
                }

                for (int i: specialButtons){
                    Button button = findViewById(i);
                    button.setBackgroundResource(R.drawable.dark_special_symbol_button);
                    button.setTextColor(getColor(R.color.dark_special_symbol_button_foreground));
                }


            } else {
                textInputEditText.setTextColor(getColor(R.color.light_answer_foreground));
                tableLayout.setBackgroundResource(R.drawable.light_bottom_background);
                textInputEditText.setBackgroundResource(R.drawable.light_top_background);
                relativeLayout.setBackgroundResource(R.drawable.light_top_background);

                for (int i: numButtons) {
                    Button button = findViewById(i);
                    button.setTextColor(getColor(R.color.light_number_foreground));
                }

                for (int i: operationsButtons){
                    Button button = findViewById(i);
                    button.setBackgroundResource(R.drawable.light_symbol_button);
                    button.setTextColor(getColor(R.color.light_symbol_button_foreground));
                }

                for (int i: specialButtons){
                    Button button = findViewById(i);
                    button.setBackgroundResource(R.drawable.light_special_symbol_button);
                    button.setTextColor(getColor(R.color.light_special_symbol_button_foreground));
                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}