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

    public void setShow(String show) {
        CalculatorReceiver.screenText = show;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isOrderedBroadcast()) {
            Bundle extras = intent.getExtras();
            String symbol;
            if (extras != null) {
                symbol = extras.getString("insertedButton");
                solver.checkBtn(symbol);
            }
            setShow(solver.getDisplayText());
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
