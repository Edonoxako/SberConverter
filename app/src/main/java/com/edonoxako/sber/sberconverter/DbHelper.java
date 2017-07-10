package com.edonoxako.sber.sberconverter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

/**
 * Created by Eugeny.Martinenko on 10.07.2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, CurrencyContract.DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CurrencyContract.SQL_CREATE_RATES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CurrencyContract.SQL_DELETE_RATES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void clearDbAndRecreate() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(CurrencyContract.SQL_DELETE_RATES);
        db.execSQL(CurrencyContract.SQL_CREATE_RATES);
        db.close();
    }

    public QueryResult queryRateByCharCode(String charCode) throws IOException {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(
                CurrencyContract.RatesTable.TABLE_NAME,
                null,
                CurrencyContract.RatesTable.COLUMN_CHAR_CODE + " = ?",
                new String[]{charCode},
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            return new QueryResult(cursor);
        } else {
            throw new IOException("Error trying fetch rate");
        }
    }

    public void insert(int numCode, String charCode, String name, int nominal, double value) {
        ContentValues cv = new ContentValues();
        cv.put(CurrencyContract.RatesTable.COLUMN_NUM_CODE, numCode);
        cv.put(CurrencyContract.RatesTable.COLUMN_CHAR_CODE, charCode);
        cv.put(CurrencyContract.RatesTable.COLUMN_NAME, name);
        cv.put(CurrencyContract.RatesTable.COLUMN_NOMINAL, nominal);
        cv.put(CurrencyContract.RatesTable.COLUMN_VALUE, value);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(CurrencyContract.RatesTable.TABLE_NAME, null, cv);

        db.close();
    }
}
