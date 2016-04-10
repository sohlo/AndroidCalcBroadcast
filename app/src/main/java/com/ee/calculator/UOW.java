package com.ee.calculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Silver on 5.04.2016.
 */
public class UOW {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    public CalculationsRepo calcRepo;
    public CalculationStatsRepo statsRepo;
    public CalculationTypesRepo typesRepo;

    public UOW(Context context) {
        dbHelper = new MySQLiteHelper(context);

        database = dbHelper.getWritableDatabase();
        calcRepo = new CalculationsRepo(database, dbHelper.TABLE_CALCULATIONS, dbHelper.CALCULATIONS_ALLCOLUMNS);
        statsRepo = new CalculationStatsRepo(database, dbHelper.TABLE_CALCULATIONS_STATS, dbHelper.CALCULATIONS_STATS_ALLCOLUMNS);
        typesRepo = new CalculationTypesRepo(database, dbHelper.TABLE_CALCULATION_TYPES, dbHelper.CALCULATION_TYPES_ALLCOLUMNS);

    }

    public void renewDatabase() {
        dbHelper.renewDatabase(database);
    }

    public void seedData() {
        CalculationType plus = new CalculationType();
        plus.setId(0);
        plus.setOperand("+");
        plus.setLifetimeCounter(1);
        typesRepo.add(plus);

//        CalculationStats stats = new CalculationStats();
//        stats.setOperandId(0);
//        stats.setDayCounter(3);
//        Date date = new Date(System.currentTimeMillis());
//        long m = date.getTime();
//        stats.setDaystamp(m);
//        statsRepo.add(stats);

//        Calculations calc = new Calculations();
//        calc.setId(0);
//        calc.setNum1(1);
//        calc.setNum2(2);
//        calc.setRes(3);
//        calc.setOperationId(0);
//        calc.setTimestamp(m);
//        calcRepo.add(calc);
    }

}
