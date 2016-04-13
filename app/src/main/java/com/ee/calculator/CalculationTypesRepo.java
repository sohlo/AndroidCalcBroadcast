package com.ee.calculator;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silver on 1.04.2016.
 */
public class CalculationTypesRepo extends Repo<CalculationType> {

    public CalculationTypesRepo(SQLiteDatabase db, String tablename, String[] allColumns) {
        super(db, tablename, allColumns);
    }

    @Override
    public CalculationType cursorToEntity(Cursor cursor) {
        CalculationType calculationType = new CalculationType();
        calculationType.setId(cursor.getLong(0));
        calculationType.setOperand(cursor.getString(1));
        calculationType.setLifetimeCounter(cursor.getLong(2));
        return calculationType;
    }

    @Override
    public ContentValues entityToContentValues(CalculationType entity) {
        ContentValues values = new ContentValues();
        values.put(getAllColumns()[1], entity.getOperand());
        values.put(getAllColumns()[2], entity.getLifetimeCounter());
        return values;
    }

    public List<CalculationType> getTypeList(long id) {
        List<CalculationType> listOfEntity = new ArrayList<CalculationType>();

        Cursor cursor = getDatabase().query(getTablename(),
                getAllColumns(), "_id = " + id, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CalculationType entity = cursorToEntity(cursor);
            listOfEntity.add(entity);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return listOfEntity;
    }

    //operandi kasutamise korral kasutamisarvu uuendamine
    public void updateTypeUsage(String operand) {

        Cursor cursor = getDatabase().query(getTablename(),
                getAllColumns(), "operand = '" + operand + "'", null, null, null, null);

        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            CalculationType entity = cursorToEntity(cursor);
            long count = entity.getLifetimeCounter() + 1;
            //väärtuse suurendamine
            if (operand.equals(entity.getOperand())) {
                entity.setLifetimeCounter(count);
                update(entity);
                count = 0;
            }
        } else {
            addNewTypeUsage(operand);
        }
        cursor.close();
    }

    public void addNewTypeUsage(String operand) {
        CalculationType newTypeUsage = new CalculationType();
        newTypeUsage.setOperand(operand);
        newTypeUsage.setLifetimeCounter(1);
        add(newTypeUsage);
    }

    //Rebo getbyid problemaatiline, kas oli seda meetodit ikka vaja, // TODO: 11.04.2016 üle kontrollida vajalikkust
    public String getOperandById(long operandId) {
        Cursor cursor = getDatabase().query(getTablename(),
                getAllColumns(), "_id = '" + operandId + "'", null, null, null, null);
        String operand = "";
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            CalculationType entity = cursorToEntity(cursor);
            operand = entity.getOperand();
            cursor.close();
        }
        return operand;
    }

    // operandile id väärtuse võtmine andmebaasist
    public long getIdFromOperand(String operand) {
        Cursor cursor = getDatabase().query(getTablename(),
                getAllColumns(), "operand = '" + operand + "'", null, null, null, null);
        long id = -1;
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            CalculationType entity = cursorToEntity(cursor);
            id = entity.getId();
            cursor.close();
        }
        return id;
    }

}
