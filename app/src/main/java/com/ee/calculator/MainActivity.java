package com.ee.calculator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.sql.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static UOW uow;
    private static CalculationTypesAdapter calculationTypesAdapter;
    private static CalculationsAdapter calculationsAdapter;
    private static CalculationStatsAdapter calculationsStatsAdapter;
    private static int currentview = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onCreate called");
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        uow = new UOW(getApplicationContext());

        //uow.renewDatabase();
        //uow.seedData();
        updateListviews();
        displayCalcListView();
        displayStatsListView();
        displayTypesListView();
    }

    private void displayTypesListView() {
        calculationTypesAdapter = new CalculationTypesAdapter(this, uow.calculationTypesRepo.getCursorAll(), uow);

        ListView listView = (ListView) findViewById(R.id.list);

        // Assign adapter to ListView
        listView.setAdapter(calculationTypesAdapter);
        currentview = 0;
    }//view 0

    private void displayCalcListView() {
        calculationsAdapter = new CalculationsAdapter(this, uow.calculationsRepo.getCursorAll(), uow);

        ListView listView = (ListView) findViewById(R.id.list);

        // Assign adapter to ListView
        listView.setAdapter(calculationsAdapter);
        currentview = 1;

    }// view 1

    private void displayStatsListView() {
        calculationsStatsAdapter = new CalculationStatsAdapter(this, uow.calculationStatsRepo.getCursorAll(), uow);

        ListView listView = (ListView) findViewById(R.id.list);

        // Assign adapter to ListView
        listView.setAdapter(calculationsStatsAdapter);
        currentview = 2;


    }// view 2

    public static void updateDB() {
        String operand = CalculatorReceiver.getOperand();
        uow.calculationTypesRepo.updateTypeUsage(operand);

        long operandId = uow.calculationTypesRepo.getIdFromOperand(CalculatorReceiver.getOperand());
        CalculationStats calculationStatsUsage = new CalculationStats();
        if (uow.calculationStatsRepo.updateStatsUsage(operandId) == -1) {
            //tegu on puuduva operandiga või uue kuuga
            calculationStatsUsage.setDayCounter(1);
            Date date = new Date(System.currentTimeMillis());
            long millis = date.getTime();
            calculationStatsUsage.setDaystamp(millis);
            uow.calculationTypesRepo.addNewTypeUsage(operand);
            //lisada uue operand andmebaasi ja selle id võtmine uuesti
            calculationStatsUsage.setOperandId(uow.calculationTypesRepo.getIdFromOperand(operand));
            uow.calculationStatsRepo.add(calculationStatsUsage);
        }


//        Calculations calc = new Calculations();
//        calc.setNum1(CalculatorReceiver.getX());
//        calc.setNum2(CalculatorReceiver.getY());
//        calc.setRes(CalculatorReceiver.getRes());
//        calc.setOperandId(operandId);
//        Date date = new Date(System.currentTimeMillis());
//        long timestamp = date.getTime();
//        calc.setTimestamp(timestamp);
//        uow.calculationsRepo.add(calc);

    }

    @Override
    protected void onResume() {
        updateListviews();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_calculations) {
            displayCalcListView();
            calculationsAdapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.action_calc_stats) {
            displayStatsListView();
            calculationsStatsAdapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.action_calc_type) {
            displayTypesListView();
            calculationTypesAdapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.action_clear) {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("Kas kustutada andmebaasi informatsioon?")
                    .setMessage("Olete kindel?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Jah", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            uow.renewDatabase();
                            uow.seedData();
                            updateListviews();
                        }
                    })
                    .setNegativeButton("Ei", null)
                    .show();
            updateListviews();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateListviews() {
        if (currentview == 0) {
            displayTypesListView();
        } else if (currentview == 1) {
            displayCalcListView();
        } else {
            displayStatsListView();
        }
    }

}
