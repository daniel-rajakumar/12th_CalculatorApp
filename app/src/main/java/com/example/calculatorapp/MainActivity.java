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
    SwitchCompat themeSwitch;

    int[] numButtons, operationsButtons, specialButtons_1, specialButtons_2;
    final String EMPTY_ANSWER = "0";
    final String ERROR_MSG = "ERROR";


    /******************************************************************************************
     ************************************ on create *******************************************
     ******************************************************************************************/

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nightmode, menu);

        setup_switch(menu);
        setup_switchToggleAction();

        return super.onCreateOptionsMenu(menu);
    }

    /******************************************************************************************
     ************************************ setup ***********************************************
     ******************************************************************************************/

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


        relativeLayout_main =        findViewById(R.id.relative);
        tableLayout_buttons =        findViewById(R.id.table_button);
        textInputEditText_question = findViewById(R.id.textInput_question);
        textView_answer =            findViewById(R.id.textView_answer);
    }

    void setup_buttons(){
        for (int i: numButtons){
            Button button = findViewById(i);

            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.weight = 1.0f;
            params.setMargins(15,15,15,15);
            button.setLayoutParams(params);

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

    void setup_switch(Menu menu){
        themeSwitch = menu.findItem(R.id.app_bar_switch)
                .setActionView(R.layout.switch_item)
                .getActionView()
                .findViewById(R.id.theme_switch);

        themeSwitch.toggle();
    }

    void setup_switchToggleAction(){
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> onSwitchToggle(isChecked));
    }

    /******************************************************************************************
    ********************************** onClick ***********************************************
    ******************************************************************************************/

    @Override
    public void onClick(View v) {
        Button button = (Button) v;

        switch (button.getId()){
            case R.id.button_0:         updateEquation("0"); break;
            case R.id.button_1:         updateEquation("1"); break;
            case R.id.button_2:         updateEquation("2"); break;
            case R.id.button_3:         updateEquation("3"); break;
            case R.id.button_4:         updateEquation("4"); break;
            case R.id.button_5:         updateEquation("5"); break;
            case R.id.button_6:         updateEquation("6"); break;
            case R.id.button_7:         updateEquation("7"); break;
            case R.id.button_8:         updateEquation("8"); break;
            case R.id.button_9:         updateEquation("9"); break;
            case R.id.button_plus:      updateEquation("+"); break;
            case R.id.button_minus:     updateEquation("-"); break;
            case R.id.button_multiply:  updateEquation("*"); break;
            case R.id.button_division:  updateEquation("/"); break;
            case R.id.button_clear:     updateEquation("c"); break;
            case R.id.button_equal:     updateEquation("="); break;
        }
    }

    void updateEquation(String text ){
        if (text.equals("=")){ onClickEqualButton(); return; }
        if (text.equals("c")){ onClickClearButton(); return; }


        overrideDefaultEquation(text);
        addTextToEquation(text);
        showLiveAnswer();
    }

    void onClickEqualButton(){
        String equation = textInputEditText_question.getText().toString();
        equation  = equation.replaceAll(" ", "");

        boolean validEquation = isEquationValid(equation, false);

        if (!validEquation) { textView_answer.setText(ERROR_MSG); return; }

        String answer = textView_answer.getText().toString();

        if (answer.equals("")) return;

        int cursorLocation = answer.length();
        textInputEditText_question.setText(answer);
        textInputEditText_question.setSelection(cursorLocation);
        textView_answer.setText("");
    }

    void onClickClearButton(){
        String text = textInputEditText_question.getText().toString();
        int cursorLocation = textInputEditText_question.getSelectionStart() - 1;
        if (cursorLocation < 0 || text.equals(EMPTY_ANSWER)) return;

        removeCharAt(text, cursorLocation);

        text = textInputEditText_question.getText().toString();
        if (isEquationValid(text, true)){
            textView_answer.setText(solve(text));
        }
    }


    void addTextToEquation(String str){
        int cursorLocation = textInputEditText_question.getSelectionStart();
        String leftHalf = textInputEditText_question.getText().toString().substring(0, cursorLocation);
        String rightHalf = textInputEditText_question.getText().toString().substring(cursorLocation);

        textInputEditText_question.setText(String.format("%s%s%s", leftHalf, str, rightHalf));
        textInputEditText_question.setSelection(cursorLocation + 1);
    }

    void overrideDefaultEquation(String str){
        for (int i: numButtons) {
            Button button = findViewById(i);
            if (button.getText().toString().equals(str) && textInputEditText_question.getText().toString().equals(EMPTY_ANSWER)){
                textInputEditText_question.setText("");
            }
        }
    }



    void showLiveAnswer(){
        String equation = textInputEditText_question.getText().toString();
        if (isEquationValid(equation, true)){
            textView_answer.setText(solve(equation));
        } else {
            textView_answer.setText(ERROR_MSG);
        }
    }


    /******************************************************************************************
     ********************************** onLongClick *******************************************
     ******************************************************************************************/

    @Override
    public boolean onLongClick(View v) {
        Button button = (Button) v;

        if (button.getId() == R.id.button_clear) clearAllEquation();

        return false;
    }

    /******************************************************************************************
     **************************************** solve *******************************************
     ******************************************************************************************/

    String solve(String equation){
        String solution;

        equation = "0" + equation;

        if (noSymbolsInEquation(equation)) return EMPTY_ANSWER;

        solution = getRidOfDivAndMulti(equation);
        solution = addAndSub(solution.toString()) + "";
        solution = removeDecimalPlacesIfNotNeeded(solution);

        if (isSpecialAnswer(divAndMulti(equation))) {
            return equation.substring(1);
        }

        textView_answer.setText("");

        return solution.toString();
    }

    /******************************************************************************************
     ********************************** toggleSwitch ******************************************
     ******************************************************************************************/

    @SuppressLint({"ResourceType", "UseCompatLoadingForColorStateLists"})
    void onSwitchToggle(boolean isChecked){
        textInputEditText_question.setTextColor(getColor(isChecked ? R.color.dark_question_foreground
                                                                   : R.color.light_question_foreground));
        textInputEditText_question.setBackgroundResource(isChecked ? R.drawable.dark_background_top
                                                                   : R.drawable.light_background_top);
        relativeLayout_main.setBackgroundResource(isChecked ? R.drawable.dark_background_middle
                                                            : R.drawable.light_background_middle);
        tableLayout_buttons.setBackgroundResource(isChecked ? R.drawable.dark_background_bottom
                                                            : R.drawable.light_background_bottom);

        for (int i: numButtons) {
            Button button = findViewById(i);
            button.setBackgroundResource(isChecked ? R.drawable.dark_number_button
                                                   : R.drawable.light_number_button);
            button.setTextColor(isChecked ? getResources().getColorStateList(R.drawable.dark_number_button)
                                          : getResources().getColorStateList(R.drawable.light_number_button));
        }

        for (int i: operationsButtons){
            Button button = findViewById(i);
            button.setBackgroundResource(isChecked ? R.drawable.dark_symbol_button
                                                   : R.drawable.light_symbol_button);
            button.setTextColor(getColor(isChecked ? R.color.dark_symbol_button_foreground
                                                   : R.color.light_symbol_button_foreground));
        }

        for (int i: specialButtons_1){
            Button button = findViewById(i);
            button.setBackgroundResource(isChecked ? R.drawable.dark_special_symbol_1_button
                                                   : R.drawable.light_special_symbol_1_button);
            button.setTextColor(getColor(isChecked ? R.color.dark_special_symbol_button_1_foreground
                                                   : R.color.light_special_symbol_button_1_foreground));
        }

        for (int i: specialButtons_2){
            Button button = findViewById(i);
            button.setBackgroundResource(isChecked ? R.drawable.dark_special_symbol_2_button
                                                   : R.drawable.light_special_symbol_2_button);
            button.setTextColor(getColor(isChecked ? R.color.dark_special_symbol_button_2_foreground
                                                   : R.color.light_special_symbol_button_2_foreground));
        }
    }

    /******************************************************************************************
     ********************************* other general methods **********************************
     ******************************************************************************************/

    void removeCharAt(String equation, int cursorLocation){

        if (equation.length() == 1) {
            cursorLocation = 1;
            equation = EMPTY_ANSWER;
        } else {
            equation = equation.substring(0, cursorLocation) + equation.substring(cursorLocation + 1);
        }

        textInputEditText_question.setText(equation);
        textInputEditText_question.setSelection(cursorLocation);
    }

    boolean isEquationValid(String str, boolean forLiveAnswer){
        int  count_symbol = 0, count_number = 0;
        StringTokenizer number = new StringTokenizer(str, "*/+-");
        StringTokenizer symbol = new StringTokenizer(str, "1234567890.");

        count_number = number.countTokens();
        count_symbol = numberOfSymbols(symbol);

        symbol = new StringTokenizer(str, "1234567890.");


        if (areSymbolsNearEachOther(symbol)) return false;
        if (isEquationEmpty(str)) return false;
        if (isSpecialAnswer(divAndMulti(str))) return false;


        if (isLeftMostNumberIsNegative(str)) {
            count_symbol--;
        }

        if (forLiveAnswer) {
            return hasMoreNumbersThanSymbols(count_number, count_symbol - 1);
        }

        return hasMoreNumbersThanSymbols(count_number, count_symbol);
    }

    boolean isLeftMostNumberIsNegative(String equation){
        return equation.charAt(0) == '-';
    }

    boolean isEquationEmpty(String equation){
        return equation.equals("");
    }

    int numberOfSymbols(StringTokenizer token){
        int count_symbol = 0;

        while (token.hasMoreTokens()) {
            String symbols = token.nextToken();
            for (int i = 0; i < symbols.length(); i++) {
                count_symbol++;
            }
        }

        return count_symbol;
    }

    boolean areSymbolsNearEachOther(StringTokenizer token){
        while (token.hasMoreTokens()){
            String symbols = token.nextToken().toString();
            if (symbols.length() > 1) {
                return true;
            }
        }
        return false;
    }

    boolean hasMoreNumbersThanSymbols(int number, int symbol){
        return number > symbol;
    }


    double divAndMulti(String str){
         double sum;
         StringTokenizer number = new StringTokenizer(str, "*/-+");
         StringTokenizer symbol = new StringTokenizer(str, "1234567890.");

         if (number.countTokens() < 1) return 0;

         sum = Double.parseDouble(number.nextToken());

         while (number.hasMoreTokens()){
             String sym = symbol.nextToken();
             String num = number.nextToken();

             if (sym.equals("*")) sum *= Double.parseDouble(num);
             if (sym.equals("/")) sum /= Double.parseDouble(num);
         }

         if ((sum+"").contains("E-")) return 0;
         if ((sum+"").contains("E")) return Double.MAX_VALUE;

         return sum;
    }

    double addAndSub(String str){
        StringTokenizer number = new StringTokenizer(str, "+-");
        StringTokenizer symbol = new StringTokenizer(str, "1234567890.");

        double sum = Double.parseDouble(number.nextToken());

        while (number.hasMoreTokens()){
            String sym = symbol.nextToken();
            String num = number.nextToken();

            if (sym.equals("+")) sum += Double.parseDouble(num);
            if (sym.equals("-")) sum -= Double.parseDouble(num);
        }

        return sum;
    }



    String getRidOfDivAndMulti(String equation){
        StringTokenizer group, group_sym;
        String solution = "";

        group = new StringTokenizer(equation, "-+");
        group_sym = new StringTokenizer(equation, "/*1234567890.");

        solution = divAndMulti(group.nextToken()) + "";

        while (group.hasMoreTokens()) {
            String gro = group_sym.nextToken();
            if (gro.length() > 1) return ERROR_MSG;
            if (gro.equals("+")) solution += "+";
            if (gro.equals("-")) solution += "-";
            double answer = divAndMulti(group.nextToken());

            if (isSpecialAnswer(answer)) return ERROR_MSG;

            solution += answer;
        }


        return solution;
    }

    String removeDecimalPlacesIfNotNeeded(String solution){
        if (Double.parseDouble(solution) % 1 == 0) {
            return ((int) Double.parseDouble(solution)) + "";
        }
        return  solution;
    }

    boolean isSpecialAnswer(double answer){
        switch (answer + ""){
            case "NaN": case "Infinity":
                return true;
        }
        return false;
    }

    boolean noSymbolsInEquation(String equation){
        return new StringTokenizer(equation, "-+/*").countTokens() == 0;
    }

    void clearAllEquation(){
         textInputEditText_question.setText(EMPTY_ANSWER);
         textView_answer.setText(EMPTY_ANSWER);
         textInputEditText_question.setSelection(EMPTY_ANSWER.length());
    }
}