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
    RelativeLayout relativeLayout_main;
    TableLayout tableLayout_buttons;
    TextInputEditText textInputEditText_question;
    TextView textView_answer;

    int[] numButtons, operationsButtons, specialButtons_1, specialButtons_2;
    final String EMPTY_ANSWER = "0";


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignValues();
        setup_questionView();
        setup_answerView();
        setup_mainLayoutView();
        setup_buttonLayoutView();
        setup_buttons();
        setup_operationsButtons();
        setup_specialButtons1();
        setup_specialButtons2();
    }

    void assignValues(){
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


        relativeLayout_main = findViewById(R.id.relative);
        tableLayout_buttons = findViewById(R.id.table_button);
        textInputEditText_question = findViewById(R.id.input);
        textView_answer = findViewById(R.id.textView_history);
    }

    void setup_buttons(){
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
    }

    void setup_operationsButtons(){
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

    }

    void setup_specialButtons1(){
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
    }

    void setup_specialButtons2(){
        for (int i: specialButtons_2){
            Button button = findViewById(i);

            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.weight = 1.0f;
//            params.setMargins(15,15,15,15);
            button.setLayoutParams(params);

            button.setTextColor(getColor(R.color.dark_special_symbol_button_2_foreground));
            button.setTextSize(50);
            button.setBackgroundTintList(null);
            button.setBackgroundResource(R.drawable.dark_special_symbol_2_button);
            button.setOnClickListener(this);
            button.setOnLongClickListener(this);
        }
    }

    void setup_questionView(){
        textInputEditText_question.setShowSoftInputOnFocus(false);
        textInputEditText_question.setText(EMPTY_ANSWER);
        textInputEditText_question.setTextColor(getColor(R.color.dark_question_foreground));
    }

    void setup_answerView(){
        textView_answer.setTextColor(getColor(R.color.dark_answer_foreground));
        textView_answer.setTextSize(150);
    }

    void setup_mainLayoutView(){
        relativeLayout_main.setBackgroundResource(R.drawable.dark_background_middle);
    }

    void setup_buttonLayoutView(){
        tableLayout_buttons.setBackgroundResource(R.drawable.dark_background_bottom);
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
                    textView_answer.setText("ERROR - 192");
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
            if (cursorLocation < 0 || text.equals(EMPTY_ANSWER)) return;
            text = removeCharAt(text, cursorLocation);
            textInputEditText_question.setText(text);
            textInputEditText_question.setSelection(cursorLocation);
            textView_answer.setText(solve(text));
            System.out.println("clear: " + text);
            isEqualtionValid(text);
            return;
        }

        if (override){
            if (str.equals("ERROR")) return;

            cursorLocation = str.length();
            textInputEditText_question.setText(str);
            textInputEditText_question.setSelection(cursorLocation);
            return;
        }


        for (int i: numButtons) {
            Button button = findViewById(i);
            if (button.getText().toString().equals(str) && textInputEditText_question.getText().toString().equals(EMPTY_ANSWER)){
                textInputEditText_question.setText("");
            }
        }


//        if (textInputEditText_question.getText().toString().equals(EMPTY_ANSWER)){
//            textInputEditText_question.setText("");
//        }



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

        isEqualtionValid("0" + textInputEditText_question.getText().toString());





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

        System.out.println("282: " + str);

        while (symbol.hasMoreTokens()){
            String token = symbol.nextToken().toString();

            if (token.length() > 1)  {
                textView_answer.setText("ERROR - 289");
                return false;
            }

            for (int i = 0; i < token.length(); i++){
                System.out.println("280: " + str);
                count_symbol++;
            }
        }

        System.out.printf("count: number: %s  | symbol: %s\n", number.countTokens(), count_symbol - 2);

        System.out.printf("297: %s \n", str);
        try {
            String unwantedAnswers = divAndMult(str) + "";
            System.out.println("299: " + unwantedAnswers);
            switch (unwantedAnswers){
                case "NaN": case "Infinity":
                    textView_answer.setText("ERROR - 307");
            }
        } catch (Exception e){ }



        if (str.charAt(0) == '-') count_symbol--;

        if (!(number.countTokens() >= count_symbol))  textView_answer.setText("ERROR - 215");

        return number.countTokens() > count_symbol;
    }






    double divAndMult(String str){
        double sum;
        StringTokenizer number = new StringTokenizer(str, "*/");
        StringTokenizer symbol = new StringTokenizer(str, "1234567890.");

        if (number.countTokens() < 1) return 0;

//        if (number.countTokens() != symbol.countTokens())
        sum = Double.parseDouble(number.nextToken());

        while (number.hasMoreTokens()){
            String sym = symbol.nextToken();
            String num = number.nextToken();

            if (sym.equals("*")) sum *= Double.parseDouble(num);
            if (sym.equals("/")) sum /= Double.parseDouble(num);
        }

//        sum = Math.round(sum * 10000000.0) / 10000000.0;
        System.out.println("sum_1: " + sum);
        System.out.println("350: " + String.format("%.0f", sum));

        if ((sum+"").contains("E-")) return 0;
        if ((sum+"").contains("E")) return Double.MAX_VALUE;

        return sum;
    }

    double addAndSub(String str){
        double sum = 0;
//        str = "0" + str;
        StringTokenizer number = new StringTokenizer(str, "+-");
        StringTokenizer symbol = new StringTokenizer(str, "1234567890.");

        System.out.println("357: 1)" + str);
        sum = Double.parseDouble(number.nextToken());

        while (number.hasMoreTokens()){
            String sym = symbol.nextToken();
            String num = number.nextToken();

            if (sym.equals("+")) sum += Double.parseDouble(num);
            if (sym.equals("-")) sum -= Double.parseDouble(num);
        }

        System.out.println("357: 2)" + sum);




        return sum;
    }
    

    String solve(String equation){
        StringTokenizer group, group_sym;
        StringBuilder sum;


        equation = "0" + equation;

        System.out.println("359: " + equation);
        if (new StringTokenizer(equation, "-+/*").countTokens() == 0) return EMPTY_ANSWER;

        group = new StringTokenizer(equation, "-+");
        group_sym = new StringTokenizer(equation, "/*1234567890.");



        sum = new StringBuilder(divAndMult(group.nextToken()) + "");



        while (group.hasMoreTokens()) {
            String gro = group_sym.nextToken();
            if (gro.length() > 1) return "ERROR";
            if (gro.equals("+")) sum.append("+");
            if (gro.equals("-")) sum.append("-");
            String test = group.nextToken();
            double answer = divAndMult(test);

            if ((answer + "").equals("NaN")) return "ERROR";
            System.out.println("375: " + answer);
            sum.append(answer);
        }

        sum = new StringBuilder(addAndSub(sum.toString()) + "");


        if (Double.parseDouble(sum.toString()) % 1 == 0) {
            sum = new StringBuilder((int) (Double.parseDouble(sum.toString())) + "");
        }

        try {
            String unwantedAnswers = divAndMult(equation) + "";
            switch (unwantedAnswers){
                case "NaN": case "Infinity":
                    return equation.substring(1);
            }
        } catch (Exception e){ }

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
                relativeLayout_main.setBackgroundResource(R.drawable.dark_background_middle);
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
                relativeLayout_main.setBackgroundResource(R.drawable.light_background_middle);
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