package com.ee.calculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Silver on 1.04.2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // Tabel 1
    public static final String TABLE_CALCULATIONS = "calculations";
    public static final String CALCULATIONS_COLUMN_ID = "_id";
    public static final String CALCULATIONS_COLUMN_CALCULATION_TYPE_ID = "operandId";
    public static final String CALCULATIONS_COLUMN_NUM_1 = "num1";
    public static final String CALCULATIONS_COLUMN_NUM_2 = "num2";
    public static final String CALCULATIONS_COLUMN_RESULT = "res";
    public static final String CALCULATIONS_COLUMN_TIMESTAMP = "timestamp";
    public static final String[] CALCULATIONS_ALLCOLUMNS = {CALCULATIONS_COLUMN_ID, CALCULATIONS_COLUMN_CALCULATION_TYPE_ID, CALCULATIONS_COLUMN_NUM_1, CALCULATIONS_COLUMN_NUM_2, CALCULATIONS_COLUMN_RESULT, CALCULATIONS_COLUMN_TIMESTAMP};

    // Tabel 2
    public static final String TABLE_CALCULATION_TYPES = "calculationTypes";
    public static final String CALCULATION_TYPES_COLUMN_ID = "_id";
    public static final String CALCULATION_TYPES_COLUMN_OPERAND_USED = "operand";
    public static final String CALCULATION_TYPES_COLUMN_OPERAND_COUNT = "lifetimeCounter";
    public static final String[] CALCULATION_TYPES_ALLCOLUMNS = {CALCULATION_TYPES_COLUMN_ID, CALCULATION_TYPES_COLUMN_OPERAND_USED, CALCULATION_TYPES_COLUMN_OPERAND_COUNT};

    // Tabel 3
    public static final String TABLE_CALCULATIONS_STATS = "calculationStats";
    public static final String CALCULATIONS_STATS_COLUMN_ID = "_id";
    public static final String CALCULATIONS_STATS_COLUMN_DAYSTAMP = "daystamp";
    public static final String CALCULATIONS_STATS_COLUMN_DAY_COUNTER = "dayCount";
    public static final String[] CALCULATIONS_STATS_ALLCOLUMNS = {CALCULATIONS_STATS_COLUMN_ID, CALCULATIONS_STATS_COLUMN_DAYSTAMP, CALCULATIONS_STATS_COLUMN_DAY_COUNTER, CALCULATIONS_COLUMN_CALCULATION_TYPE_ID};


    private static final String DATABASE_NAME = "calculations.db";
    private static final int DATABASE_VERSION = 1;

    // SQL laused tabelite loomiseks
    private static final String DATABASE_CALCULATIONS_CREATE = "create table " + TABLE_CALCULATIONS
            + "(" + CALCULATIONS_COLUMN_ID + " integer primary key autoincrement, "
            + CALCULATIONS_COLUMN_CALCULATION_TYPE_ID + " integer,"
            + CALCULATIONS_COLUMN_NUM_1 + " real,"
            + CALCULATIONS_COLUMN_NUM_2 + " real,"
            + CALCULATIONS_COLUMN_RESULT + " real,"
            + CALCULATIONS_COLUMN_TIMESTAMP + " integer);";

    private static final String DATABASE_CALC_TYPE_CREATE = "create table " + TABLE_CALCULATION_TYPES
            + "(" + CALCULATION_TYPES_COLUMN_ID + " integer primary key autoincrement, "
            + CALCULATION_TYPES_COLUMN_OPERAND_USED + " text not null,"
            + CALCULATION_TYPES_COLUMN_OPERAND_COUNT + " integer);";

    private static final String DATABASE_CALC_STATS_CREATE = "create table " + TABLE_CALCULATIONS_STATS
            + "(" + CALCULATIONS_STATS_COLUMN_ID + " integer primary key autoincrement, "
            + CALCULATIONS_STATS_COLUMN_DAYSTAMP + " integer,"
            + CALCULATIONS_COLUMN_CALCULATION_TYPE_ID + " integer,"
            + CALCULATIONS_STATS_COLUMN_DAY_COUNTER + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void renewDatabase(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALCULATION_TYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALCULATIONS_STATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALCULATIONS);

        db.execSQL(DATABASE_CALCULATIONS_CREATE);
        db.execSQL(DATABASE_CALC_TYPE_CREATE);
        db.execSQL(DATABASE_CALC_STATS_CREATE);
    }


    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CALCULATIONS_CREATE);
        db.execSQL(DATABASE_CALC_TYPE_CREATE);
        db.execSQL(DATABASE_CALC_STATS_CREATE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALCULATION_TYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALCULATIONS_STATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALCULATIONS);
        onCreate(db);
    }

}
