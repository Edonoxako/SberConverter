package com.edonoxako.sber.sberconverter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * Created by Eugeny.Martinenko on 10.07.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DataBaseTest {

    private DbHelper dbHelper;

    @Before
    public void setUp() throws Exception {
        dbHelper = new DbHelper(RuntimeEnvironment.application);
        dbHelper.clearDbAndRecreate();
    }

    @Test
    public void testInsertAndRead() throws Exception {
        ContentValues cv = new ContentValues();
        cv.put(CurrencyContract.RatesTable.COLUMN_NUM_CODE, 1);
        cv.put(CurrencyContract.RatesTable.COLUMN_CHAR_CODE, "USD");
        cv.put(CurrencyContract.RatesTable.COLUMN_NAME, "Американский доллар");
        cv.put(CurrencyContract.RatesTable.COLUMN_NOMINAL, 1);
        cv.put(CurrencyContract.RatesTable.COLUMN_VALUE, 60.5);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.insert(CurrencyContract.RatesTable.TABLE_NAME, null, cv);

        Cursor cursor = db.query(CurrencyContract.RatesTable.TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();

        int numCode = cursor.getInt(cursor.getColumnIndex(CurrencyContract.RatesTable.COLUMN_NUM_CODE));
        String charCode = cursor.getString(cursor.getColumnIndex(CurrencyContract.RatesTable.COLUMN_CHAR_CODE));
        String name = cursor.getString(cursor.getColumnIndex(CurrencyContract.RatesTable.COLUMN_NAME));
        int nominal = cursor.getInt(cursor.getColumnIndex(CurrencyContract.RatesTable.COLUMN_NOMINAL));
        double value = cursor.getDouble(cursor.getColumnIndex(CurrencyContract.RatesTable.COLUMN_VALUE));

        cursor.close();
        db.close();

        assertEquals(1, numCode);
        assertEquals("USD", charCode);
        assertEquals("Американский доллар", name);
        assertEquals(1, nominal);
        assertEquals(60.5, value, 0.001);
    }

    @Test
    public void testSimplifiedInsertAndRead() throws Exception {
        dbHelper.insert(1, "USD", "Американский доллар", 1, 60.5);
        QueryResult queryResult = dbHelper.queryRateByCharCode("USD");

        int numCode = queryResult.getNumCode();
        String charCode = queryResult.getCharCode();
        String name = queryResult.getName();
        int nominal = queryResult.getNominal();
        double value = queryResult.getValue();

        queryResult.close();
        dbHelper.close();

        assertEquals(1, numCode);
        assertEquals("USD", charCode);
        assertEquals("Американский доллар", name);
        assertEquals(1, nominal);
        assertEquals(60.5, value, 0.001);
    }
}
