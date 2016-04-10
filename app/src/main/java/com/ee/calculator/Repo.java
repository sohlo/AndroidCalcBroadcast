package com.ee.calculator;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silver on 6.04.2016.
 */
public abstract class Repo<T extends IEntity> {
    private SQLiteDatabase database;
    private String tablename;
    private String[] allColumns;

    public Repo(SQLiteDatabase db, String tablename, String[] allColumns) {
        this.database = db;
        this.tablename = tablename;
        this.allColumns = allColumns;
    }

    public List<T> getAll() {
        ArrayList<T> arrayList = new ArrayList<T>();
        Cursor cursor = database.query(tablename, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            T entity = cursorToEntity(cursor);
            arrayList.add(entity);
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }

    public T add(T entity) {
        ContentValues values = entityToContentValues(entity);
        long insertID = database.insert(tablename, null, values);
        return getById(insertID);
    }

    public T getById(long id) {
        Cursor cursor = database.query(tablename, allColumns, allColumns[0] + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        T entity = cursorToEntity(cursor);
        cursor.close();
        return entity;
    }

    public void update(T entity) {
        ContentValues values = entityToContentValues(entity);
        database.update(tablename, values, allColumns[0] + "=" + entity.getId(), null);
    }

    public abstract T cursorToEntity(Cursor cursor);

    public abstract ContentValues entityToContentValues(T entity);


    public String[] getAllColumns() {
        return allColumns;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public String getTablename() {
        return tablename;
    }

    public Cursor getCursorAll() {
        Cursor cursor = database.query(tablename,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        return cursor;
    }


}
