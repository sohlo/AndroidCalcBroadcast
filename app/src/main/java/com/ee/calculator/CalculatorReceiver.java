package com.ee.calculator;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class CalculatorReceiver extends BroadcastReceiver {
    private static final String TAG = "CalculatorReceiver";
    private static String screenText = "";
    private static String currentNumber = "";
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

}
