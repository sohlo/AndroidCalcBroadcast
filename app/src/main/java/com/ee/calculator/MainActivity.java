package com.ee.calculator;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static UOW uow;
    private static CalculationTypesAdapter typesAdapter;
    private static CalculationsAdapter calculationsAdapter;
    private static CalculationStatsAdapter calculationsStatsAdapter;

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
        notifyAdapters();
        displayTypesListView();
    }

    private void displayTypesListView() {
        typesAdapter = new CalculationTypesAdapter(this, uow.typesRepo.getCursorAll(), uow);

        ListView listView = (ListView) findViewById(R.id.list);

        // Assign adapter to ListView
        // listview will iterate over adapter, and get filled subview for every row
        listView.setAdapter(typesAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the id
                String dbid =
                        cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                Toast.makeText(getApplicationContext(),
                        dbid, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void displayCalcListView() {
        calculationsAdapter = new CalculationsAdapter(this, uow.calcRepo.getCursorAll(), uow);

        ListView listView = (ListView) findViewById(R.id.list);

        // Assign adapter to ListView
        // listview will iterate over adapter, and get filled subview for every row
        listView.setAdapter(calculationsAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the id
                String dbid =
                        cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                Toast.makeText(getApplicationContext(),
                        dbid, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void displayStatsListView() {
        calculationsStatsAdapter = new CalculationStatsAdapter(this, uow.statsRepo.getCursorAll(), uow);

        ListView listView = (ListView) findViewById(R.id.list);

        // Assign adapter to ListView
        // listview will iterate over adapter, and get filled subview for every row
        listView.setAdapter(calculationsStatsAdapter);

    }

    public static void updateDB() {
        long operandId = uow.typesRepo.getIdFromOperand(CalculatorReceiver.getOperand());

        uow.typesRepo.updateTypeUsage(CalculatorReceiver.getOperand());
        if (operandId == -1) {
            CalculationType newType = new CalculationType();
            newType.setOperand(CalculatorReceiver.getOperand());
            newType.setLifetimeCounter(1);
            uow.typesRepo.add(newType);
        }
        notifyAdapters();
        uow.statsRepo.updateStatsUsage(operandId);

//        Calculations calc = new Calculations();
//        calc.setNum1(CalculatorReceiver.getX());
//        calc.setNum2(CalculatorReceiver.getY());
//        calc.setRes(CalculatorReceiver.getRes());
//        calc.setOperationId(operandId);
//        calc.setTimestamp();
//        CalculationStats stats = new CalculationStats();
//        stats.setOperandId(operandId);
//        //stats.set();
//        uow.statsRepo.updateStatsUsage(operandId);
        //uow.calcRepo.add(calc);

        notifyAdapters();
    }

    @Override
    protected void onResume() {
        displayTypesListView();
        notifyAdapters();
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
            typesAdapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.action_clear) {
            uow.renewDatabase();
            uow.seedData();
            notifyAdapters();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void notifyAdapters() {
        try {
            calculationsAdapter.notifyDataSetChanged();
            calculationsStatsAdapter.notifyDataSetChanged();
            typesAdapter.notifyDataSetChanged();
        } catch (Exception e) {

        }
    }

}
