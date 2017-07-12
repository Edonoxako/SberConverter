package com.edonoxako.sber.sberconverter.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import java.util.ArrayList;
import java.util.List;

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

    public CurrencyRate queryRateByCharCode(String charCode) {
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
            QueryResult result = new QueryResult(cursor);
            CurrencyRate currencyRate = result.asRate();
            result.close();
            return currencyRate;
        } else {
            return null;
        }
    }

    public void insert(CurrencyRate rate) {
        if (rate == null) return;
        ContentValues cv = new ContentValues();
        populateValues(cv, rate);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(CurrencyContract.RatesTable.TABLE_NAME, null, cv);
        db.close();
    }

    public void resetRates(List<CurrencyRate> rates) {
        if (rates == null) return;

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        db.beginTransaction();
        try {
            db.delete(CurrencyContract.RatesTable.TABLE_NAME, null, null);
            for (CurrencyRate rate : rates) {
                populateValues(cv, rate);
                db.insert(CurrencyContract.RatesTable.TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        db.close();
    }

    public List<CurrencyRate> queryAll() {
        List<CurrencyRate> rates = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(CurrencyContract.RatesTable.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            QueryResult result = new QueryResult(cursor);

            rates.add(result.asRate());
            while (result.hasNext()) {
                rates.add(result.asRate());
            }

            result.close();
        }

        db.close();
        return rates;
    }

    private void populateValues(ContentValues cv, CurrencyRate rate) {
        cv.put(CurrencyContract.RatesTable.COLUMN_RATE_ID, rate.getId());
        cv.put(CurrencyContract.RatesTable.COLUMN_NUM_CODE, rate.getNumCode());
        cv.put(CurrencyContract.RatesTable.COLUMN_CHAR_CODE, rate.getCharCode());
        cv.put(CurrencyContract.RatesTable.COLUMN_NAME, rate.getName());
        cv.put(CurrencyContract.RatesTable.COLUMN_NOMINAL, rate.getNominal());
        cv.put(CurrencyContract.RatesTable.COLUMN_VALUE, rate.getValue());
    }
}
