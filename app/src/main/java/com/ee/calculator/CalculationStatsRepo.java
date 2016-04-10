package com.ee.calculator;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Silver on 1.04.2016.
 */
public class CalculationStatsRepo extends Repo<CalculationStats> {

    public CalculationStatsRepo(SQLiteDatabase db, String tablename, String[] allColumns) {
        super(db, tablename, allColumns);
    }

    @Override
    public CalculationStats cursorToEntity(Cursor cursor) {
        CalculationStats calculationStats = new CalculationStats();
        calculationStats.setId(cursor.getLong(0));
        calculationStats.setDaystamp(cursor.getLong(1));
        calculationStats.setOperandId(cursor.getLong(2));
        calculationStats.setDayCounter(cursor.getInt(3));
        return calculationStats;
    }

    @Override
    public ContentValues entityToContentValues(CalculationStats entity) {
        ContentValues values = new ContentValues();
        values.put(getAllColumns()[1], entity.getDaystamp());
        values.put(getAllColumns()[2], entity.getDayCounter());
        values.put(getAllColumns()[3], entity.getOperandId());
        return values;
    }

    public List<CalculationStats> getStatList(long id) {
        List<CalculationStats> listOfEntity = new ArrayList<CalculationStats>();

        Cursor cursor = getDatabase().query(getTablename(),
                getAllColumns(), "_id = " + id, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CalculationStats entity = cursorToEntity(cursor);
            listOfEntity.add(entity);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return listOfEntity;
    }

    public void updateStatsUsage(long operandId) {

        Cursor cursor = getDatabase().query(getTablename(),
                getAllColumns(), "operandId = '" + operandId + "'", null, null, null, null);

        cursor.moveToFirst();

        Date date = new Date(System.currentTimeMillis());
        long millis = date.getTime();

        if (!cursor.isAfterLast()) {
            CalculationStats entity = cursorToEntity(cursor);

            //Salvesatud kuupäeva saamine
            long daystamp = entity.getDaystamp();
            Calendar dateSaved = Calendar.getInstance();
            dateSaved.setTimeInMillis(daystamp);
            //Tänase kuupäeva saamine
            Calendar calendar = Calendar.getInstance();
            long day = calendar.get(Calendar.DAY_OF_MONTH);

            //Kontroll kas tegu on tänase sisestusega või juba uuega
            int counter = entity.getDayCounter();
            if (dateSaved.get(Calendar.DAY_OF_MONTH) == day) {
                entity.setDayCounter(counter + 1);
                update(entity);
                counter = 0;
            } else if (dateSaved.get(Calendar.DAY_OF_MONTH) < day) {
                entity.setDaystamp(millis);
                entity.setOperandId(operandId);
                add(entity);
            }
        } else {
            CalculationStats newStat = new CalculationStats();
            newStat.setDaystamp(millis);
            newStat.setDayCounter(1);
            newStat.setOperandId(operandId);
            add(newStat);
        }
        cursor.close();

    }
}
