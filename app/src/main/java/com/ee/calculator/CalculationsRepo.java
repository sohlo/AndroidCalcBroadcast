package com.ee.calculator;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silver on 28.03.2016.
 */
public class CalculationsRepo extends Repo<Calculations> {
    public CalculationsRepo(SQLiteDatabase db, String tablename, String[] allColumns) {
        super(db, tablename, allColumns);
    }

    @Override
    public Calculations cursorToEntity(Cursor cursor) {
        Calculations calculations = new Calculations();
        calculations.setId(cursor.getLong(0));
        calculations.setOperandId(cursor.getLong(1));
        calculations.setNum1(cursor.getFloat(2));
        calculations.setNum2(cursor.getFloat(3));
        calculations.setRes(cursor.getFloat(4));
        calculations.setTimestamp(cursor.getInt(5));
        return calculations;
    }

    @Override
    public ContentValues entityToContentValues(Calculations entity) {
        ContentValues values = new ContentValues();
        values.put(getAllColumns()[1], entity.getOperandId());
        values.put(getAllColumns()[2], entity.getNum1());
        values.put(getAllColumns()[3], entity.getNum2());
        values.put(getAllColumns()[3], entity.getRes());
        values.put(getAllColumns()[3], entity.getTimestamp());
        return values;
    }

    public List<Calculations> getCalcList(long id) {
        List<Calculations> listOfEntity = new ArrayList<Calculations>();

        Cursor cursor = getDatabase().query(getTablename(),
                getAllColumns(), "_id = " + id, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Calculations entity = cursorToEntity(cursor);
            listOfEntity.add(entity);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return listOfEntity;
    }

}
