package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {
    RelativeLayout relativeLayout;
    TableLayout tableLayout_buttons;
    TextInputEditText textInputEditText_question;
    TextView textView_answer;

    int[] numButtons, operationsButtons, specialButtons_1, specialButtons_2;
    final String EMPTY_ANSWER = "0";
    String history;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        };

        specialButtons_1 = new int[]{
                R.id.button_equal
        };

        specialButtons_2 = new int[]{
                R.id.button_clear
        };


        relativeLayout = findViewById(R.id.relative);
        tableLayout_buttons = findViewById(R.id.table_button);
        textInputEditText_question = findViewById(R.id.input);


        textView_answer = findViewById(R.id.textView_history);










        textInputEditText_question.setShowSoftInputOnFocus(false);
        textInputEditText_question.setText(EMPTY_ANSWER);

        textView_answer.setTextColor(getColor(R.color.dark_answer_foreground));
        textView_answer.setTextSize(150);

        relativeLayout.setBackgroundResource(R.drawable.dark_background_middle);
        tableLayout_buttons.setBackgroundResource(R.drawable.dark_background_bottom);
        textInputEditText_question.setTextColor(getColor(R.color.dark_question_foreground));

        // number buttons
        for (int i: numButtons){
            Button button = findViewById(i);

            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.weight = 1.0f;
            params.setMargins(15,15,15,15);
            button.setLayoutParams(params);


//            button.setTextColor(Color.rgb(200, 200, 200));
            button.setTextSize(50);
            button.setBackgroundTintList(null);
            button.setBackgroundResource(R.drawable.dark_number_button);
            button.setTextColor(getResources().getColorStateList(R.drawable.dark_number_button));
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
        for (int i: specialButtons_1){
            Button button = findViewById(i);

            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.weight = 1.0f;
            params.setMargins(15,15,15,15);
            button.setLayoutParams(params);

            button.setTextColor(getColor(R.color.dark_special_symbol_button_1_foreground));
            button.setTextSize(50);
            button.setBackgroundTintList(null);
            button.setBackgroundResource(R.drawable.dark_special_symbol_1_button);
            button.setOnClickListener(this);
            button.setOnLongClickListener(this);
        }

        // clear
        for (int i: specialButtons_2){
            Button button = findViewById(i);

            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.weight = 1.0f;
            params.setMargins(15,15,15,15);
            button.setLayoutParams(params);

            button.setTextColor(getColor(R.color.dark_special_symbol_button_2_foreground));
            button.setTextSize(50);
            button.setBackgroundTintList(null);
            button.setBackgroundResource(R.drawable.dark_special_symbol_2_button);
            button.setOnClickListener(this);
            button.setOnLongClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;

        String equation = textInputEditText_question.getText().toString();
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
            case R.id.button_equal:     {
                equation = textInputEditText_question.getText().toString();
                equation  = equation.replaceAll(" ", "");

                if (!isEqualtionValid(equation)) {
                    textView_answer.setText("ERROR");
                    return;
                }
                if (isEqualtionValid(equation)) {
                    updateEquation(solve(equation), true);
                }

            }
        }
    }

    void updateEquation(String str, boolean override){
        int cursorLocation;



        // if clear
        if (str.equals("c")){
            String text = textInputEditText_question.getText().toString();
            cursorLocation = textInputEditText_question.getSelectionStart() - 1;
            if (cursorLocation < 0 || text.equals(EMPTY_ANSWER))  return;
            text = removeCharAt(text, cursorLocation);
            textInputEditText_question.setText(text);
            textInputEditText_question.setSelection(cursorLocation);
            textView_answer.setText(solve(text));
            System.out.println("clear: " + text);
            isEqualtionValid(text);
            return;
        }

        if (override){
            cursorLocation = str.length();
            textInputEditText_question.setText(str);
            textInputEditText_question.setSelection(cursorLocation);
            return;
        }

        if (textInputEditText_question.getText().toString().equals(EMPTY_ANSWER)){
            textInputEditText_question.setText("");
        }



        cursorLocation = textInputEditText_question.getSelectionStart();
        String leftHalf = textInputEditText_question.getText().toString().substring(0, cursorLocation);
        String rightHalf = textInputEditText_question.getText().toString().substring(cursorLocation);

        textInputEditText_question.setText(String.format("%s%s%s", leftHalf, str, rightHalf));

        textInputEditText_question.setSelection(cursorLocation + 1);


        try {
            textView_answer.setText(solve(textInputEditText_question.getText().toString()));

        } catch (Exception e){
            System.err.println("in update equ: "  + e);
        }

        isEqualtionValid(textInputEditText_question.getText().toString());





    }




    String removeCharAt(String str, int index){
        if (str.length() == 1) return EMPTY_ANSWER;
        return str.substring(0, index) + str.substring(index + 1);
    }

    

    boolean isEqualtionValid(String str){
        double sum;
        int  count_symbol = 0;
        StringTokenizer number = new StringTokenizer(str, "*/+-");
        StringTokenizer symbol = new StringTokenizer(str, "1234567890.");

        while (symbol.hasMoreTokens()){
            String token = symbol.nextToken().toString();

            for (int i = 0; i < token.length(); i++){
                System.out.println("280: " + str);
                count_symbol++;
            }
        }

        System.out.printf("count: number: %s  | symbol: %s\n", number.countTokens(), count_symbol - 2);


        if (number.countTokens() <= count_symbol - 1)  textView_answer.setText("ERROR");

        return number.countTokens() > count_symbol;
    }






    double divAndMult(String str){
        double sum;
        StringTokenizer number = new StringTokenizer(str, "*/");
        StringTokenizer symbol = new StringTokenizer(str, "1234567890.");

        if (number.countTokens() < 1) return 0;

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
        StringTokenizer number = new StringTokenizer(str, "+-");
        StringTokenizer symbol = new StringTokenizer(str, "1234567890.");

        sum = Double.parseDouble(number.nextToken());
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
        StringBuilder sum;


        System.out.println("sum: " + equation);
        if (new StringTokenizer(equation, "-+/*").countTokens() == 0) return EMPTY_ANSWER;

        group = new StringTokenizer(equation, "-+");
        group_sym = new StringTokenizer(equation, "/*1234567890.");

        StringTokenizer group1 = new StringTokenizer(equation, "-+");
        while (group1.hasMoreTokens()){
            System.out.println("sum: " + group1.nextToken());

        }


        sum = new StringBuilder(divAndMult(group.nextToken()) + "");


        while (group.hasMoreTokens()) {
            String gro = group_sym.nextToken();
            if (gro.length() > 1) return "ERROR";
            if (gro.equals("+")) sum.append("+");
            if (gro.equals("-")) sum.append("-");
            String test = group.nextToken();
            sum.append(divAndMult(test));
        }

        sum = new StringBuilder(addAndSub(sum.toString()) + "");


        if (Double.parseDouble(sum.toString()) % 1 == 0) {
            sum = new StringBuilder((int) (Double.parseDouble(sum.toString())) + "");
        }

        textView_answer.setText("");

        return sum.toString();
    }

    @Override
    public boolean onLongClick(View v) {
        Button button = (Button) v;

        switch (button.getId()){
            case R.id.button_clear: {
                textInputEditText_question.setText(EMPTY_ANSWER);
                textView_answer.setText(EMPTY_ANSWER);
                textInputEditText_question.setSelection(EMPTY_ANSWER.length());
            }
        }

        return false;
    }

    SwitchCompat aSwitch;

    @SuppressLint({"ResourceType", "UseCompatLoadingForColorStateLists"})
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nightmode, menu);

        aSwitch = menu.findItem(R.id.app_bar_switch)
                      .setActionView(R.layout.switch_item)
                      .getActionView()
                      .findViewById(R.id.theme_switch);

        aSwitch.setBackgroundTintList(null);

        aSwitch.toggle();

        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                textInputEditText_question.setTextColor(getColor(R.color.dark_question_foreground));
                textInputEditText_question.setBackgroundResource(R.drawable.dark_background_top);
                relativeLayout.setBackgroundResource(R.drawable.dark_background_middle);
                tableLayout_buttons.setBackgroundResource(R.drawable.dark_background_bottom);

                for (int i: numButtons) {
                    Button button = findViewById(i);
                    button.setBackgroundResource(R.drawable.dark_number_button);
                    button.setTextColor(getResources().getColorStateList(R.drawable.dark_number_button));
                }

                for (int i: operationsButtons){
                    Button button = findViewById(i);
                    button.setBackgroundResource(R.drawable.dark_symbol_button);
                    button.setTextColor(getColor(R.color.dark_symbol_button_foreground));
                }

                for (int i: specialButtons_1){
                    Button button = findViewById(i);
                    button.setBackgroundResource(R.drawable.dark_special_symbol_1_button);
                    button.setTextColor(getColor(R.color.dark_special_symbol_button_1_foreground));
                }

                for (int i: specialButtons_2){
                    Button button = findViewById(i);
                    button.setBackgroundResource(R.drawable.dark_special_symbol_2_button);
                    button.setTextColor(getColor(R.color.dark_special_symbol_button_2_foreground));
                }

            } else {
                textInputEditText_question.setTextColor(getColor(R.color.light_question_foreground));
                textInputEditText_question.setBackgroundResource(R.drawable.light_background_top);
                relativeLayout.setBackgroundResource(R.drawable.light_background_middle);
                tableLayout_buttons.setBackgroundResource(R.drawable.light_background_bottom);

                for (int i: numButtons) {
                    Button button = findViewById(i);
                    button.setBackgroundResource(R.drawable.light_number_button);
                    button.setTextColor(getResources().getColorStateList(R.drawable.light_number_button));
                }

                for (int i: operationsButtons){
                    Button button = findViewById(i);
                    button.setBackgroundResource(R.drawable.light_symbol_button);
                    button.setTextColor(getColor(R.color.light_symbol_button_foreground));
                }

                for (int i: specialButtons_1){
                    Button button = findViewById(i);
                    button.setBackgroundResource(R.drawable.light_special_symbol_1_button);
                    button.setTextColor(getColor(R.color.light_special_symbol_button_1_foreground));
                }

                for (int i: specialButtons_2){
                    Button button = findViewById(i);
                    button.setBackgroundResource(R.drawable.light_special_symbol_2_button);
                    button.setTextColor(getColor(R.color.light_special_symbol_button_2_foreground));
                }


            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}