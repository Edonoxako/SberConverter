package com.edonoxako.sber.sberconverter.data;

import android.database.Cursor;

import com.edonoxako.sber.sberconverter.CurrencyRate;

/**
 * Created by Eugeny.Martinenko on 10.07.2017.
 */

public class QueryResult {

    private Cursor cursor;

    public QueryResult(Cursor cursor) {
        this.cursor = cursor;
    }

    public int getNumCode() {
        return cursor.getInt(cursor.getColumnIndex(CurrencyContract.RatesTable.COLUMN_NUM_CODE));
    }

    public String getCharCode() {
        return cursor.getString(cursor.getColumnIndex(CurrencyContract.RatesTable.COLUMN_CHAR_CODE));
    }

    public String getName() {
        return cursor.getString(cursor.getColumnIndex(CurrencyContract.RatesTable.COLUMN_NAME));
    }

    public int getNominal() {
        return cursor.getInt(cursor.getColumnIndex(CurrencyContract.RatesTable.COLUMN_NOMINAL));
    }

    public double getValue() {
        return cursor.getDouble(cursor.getColumnIndex(CurrencyContract.RatesTable.COLUMN_VALUE));
    }

    public CurrencyRate asRate() {
        return new CurrencyRate(getNumCode(), getCharCode(), getNominal(), getName(), getValue());
    }

    public boolean hasNext() {
        return cursor.moveToNext();
    }

    public void close() {
        cursor.close();
    }
}
