package com.ee.calculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;

/**
 * Created by Silver on 5.04.2016.
 */
public class UOW {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    public CalculationsRepo calculationsRepo;
    public CalculationStatsRepo calculationStatsRepo;
    public CalculationTypesRepo calculationTypesRepo;

    public UOW(Context context) {
        dbHelper = new MySQLiteHelper(context);

        database = dbHelper.getWritableDatabase();
        calculationsRepo = new CalculationsRepo(database, dbHelper.TABLE_CALCULATIONS, dbHelper.CALCULATIONS_ALLCOLUMNS);
        calculationStatsRepo = new CalculationStatsRepo(database, dbHelper.TABLE_CALCULATIONS_STATS, dbHelper.CALCULATIONS_STATS_ALLCOLUMNS);
        calculationTypesRepo = new CalculationTypesRepo(database, dbHelper.TABLE_CALCULATION_TYPES, dbHelper.CALCULATION_TYPES_ALLCOLUMNS);

    }

    public void renewDatabase() {
        dbHelper.renewDatabase(database);
    }

    public void seedData() {
        CalculationType plus = new CalculationType();
        plus.setId(0);
        plus.setOperand("+");
        plus.setLifetimeCounter(1);
        calculationTypesRepo.add(plus);

        Calculations calc = new Calculations();
        calc.setNum1(2);
        calc.setNum2(1);
        calc.setRes(3);
        calc.setOperandId(0);
        Date date = new Date(System.currentTimeMillis());
        long timestamp = date.getTime();
        calc.setTimestamp(timestamp);
        calculationsRepo.add(calc);

//        CalculationStats stats = new CalculationStats();
//        stats.setOperandId(0);
//        stats.setDayCounter(3);
//        Date date = new Date(System.currentTimeMillis());
//        long m = date.getTime();
//        stats.setDaystamp(m);
//        calculationStatsRepo.add(stats);

//        Calculations calc = new Calculations();
//        calc.setId(0);
//        calc.setNum1(1);
//        calc.setNum2(2);
//        calc.setRes(3);
//        calc.setOperandId(0);
//        calc.setTimestamp(m);
//        calculationsRepo.add(calc);
    }

}
