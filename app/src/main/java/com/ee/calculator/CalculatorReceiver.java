package com.ee.calculator;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class CalculatorReceiver extends BroadcastReceiver {

    private static String screenText = "";
    private static String currentNumber = "";
    private static String operand = "";
    private static float x;
    private static float y;
    private static float res;

    public static String getOperand() {
        return operand;
    }

    public static void setOperand(String operand) {
        CalculatorReceiver.operand = operand;
    }

    public static float getX() {
        return x;
    }

    public static void setX(float x) {
        CalculatorReceiver.x = x;
    }

    public static float getY() {
        return y;
    }

    public static void setY(float y) {
        CalculatorReceiver.y = y;
    }

    public static float getRes() {
        return res;
    }

    public static void setRes(float res) {
        CalculatorReceiver.res = res;
    }

    private CalculatorEngine solver = new CalculatorEngine();

    public static void setCurrentNumber(String currentNumber) {
        CalculatorReceiver.currentNumber = currentNumber;
    }

    public void setScreentext(String screentext) {
        CalculatorReceiver.screenText = screentext;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isOrderedBroadcast()) {
            Bundle extras = intent.getExtras();
            String symbol;
            boolean isRestoringState;
            if (extras != null) {
                symbol = extras.getString("insertedButton");
                isRestoringState = extras.getBoolean("restoreState");
                if (isRestoringState) {
                    String state = extras.getString("savedCalculation");
                    if (state != null)
                        solver.clear();
                    solver.setStringToArray(state);
                } else {
                    solver.checkBtn(symbol);
                }
            }
            setScreentext(solver.getDisplayText());
            setCurrentNumber(solver.getCurrentNumber());
            setResultData(screenText + currentNumber);
            setResultCode(Activity.RESULT_OK);
        }
    }


    public static void saveStat(float x, float y, float res, String operand) {
        setX(x);
        setY(y);
        setRes(res);
        setOperand(operand);
        MainActivity.updateDB();

    }
}
