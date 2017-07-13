package com.edonoxako.sber.sberconverter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edonoxako.sber.sberconverter.repository.cache.data.CurrencyContract;
import com.edonoxako.sber.sberconverter.repository.cache.data.DbHelper;
import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

    @After
    public void tearDown() throws Exception {
        dbHelper.close();
    }

    @Test
    public void testBaseInsertAndRead() throws Exception {
        ContentValues cv = new ContentValues();
        cv.put(CurrencyContract.RatesTable.COLUMN_RATE_ID, "testId");
        cv.put(CurrencyContract.RatesTable.COLUMN_NUM_CODE, 1);
        cv.put(CurrencyContract.RatesTable.COLUMN_CHAR_CODE, "USD");
        cv.put(CurrencyContract.RatesTable.COLUMN_NAME, "Американский доллар");
        cv.put(CurrencyContract.RatesTable.COLUMN_NOMINAL, 1);
        cv.put(CurrencyContract.RatesTable.COLUMN_VALUE, 60.5);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.insert(CurrencyContract.RatesTable.TABLE_NAME, null, cv);

        Cursor cursor = db.query(CurrencyContract.RatesTable.TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();

        String rateId = cursor.getString(cursor.getColumnIndex(CurrencyContract.RatesTable.COLUMN_RATE_ID));
        int numCode = cursor.getInt(cursor.getColumnIndex(CurrencyContract.RatesTable.COLUMN_NUM_CODE));
        String charCode = cursor.getString(cursor.getColumnIndex(CurrencyContract.RatesTable.COLUMN_CHAR_CODE));
        String name = cursor.getString(cursor.getColumnIndex(CurrencyContract.RatesTable.COLUMN_NAME));
        int nominal = cursor.getInt(cursor.getColumnIndex(CurrencyContract.RatesTable.COLUMN_NOMINAL));
        double value = cursor.getDouble(cursor.getColumnIndex(CurrencyContract.RatesTable.COLUMN_VALUE));

        cursor.close();
        db.close();

        assertEquals("testId", rateId);
        assertEquals(1, numCode);
        assertEquals("USD", charCode);
        assertEquals("Американский доллар", name);
        assertEquals(1, nominal);
        assertEquals(60.5, value, 0.001);
    }

    @Test
    public void testSimplifiedInsertAndRead() throws Exception {
        CurrencyRate testRate = new CurrencyRate("A1", 1, "USD", 1, "Американский доллар", 60.5);

        dbHelper.insert(testRate);
        CurrencyRate insertedRate = dbHelper.queryRateByCharCode("USD");

        assertEquals(testRate, insertedRate);
    }

    @Test
    public void testInsertListOfRates() throws Exception {
        List<CurrencyRate> testRates = new ArrayList<>();
        testRates.add(new CurrencyRate("A1", 1, "USD", 1, "Американский доллар", 60.5d));
        testRates.add(new CurrencyRate("A2", 2, "EUR", 1, "Евро", 70.5d));
        testRates.add(new CurrencyRate("A3", 3, "JPY", 100, "Японских йен", 50d));

        testWithListOFRates(testRates);
    }

    @Test
    public void testInsertAndReplaceListOfRates() throws Exception {
        List<CurrencyRate> testRates1 = new ArrayList<>();
        testRates1.add(new CurrencyRate("A1", 1, "USD", 1, "Американский доллар", 60.5d));
        testRates1.add(new CurrencyRate("A2", 2, "EUR", 1, "Евро", 70.5d));
        testRates1.add(new CurrencyRate("A3", 3, "JPY", 100, "Японских йен", 50d));

        List<CurrencyRate> testRates2 = new ArrayList<>();
        testRates2.add(new CurrencyRate("A4", 4, "GBP", 1, "Фунт стерлингов Соединенного королевства", 78.2d));
        testRates2.add(new CurrencyRate("A5", 5, "AMD", 1, "Армянских драмов", 12.5d));
        testRates2.add(new CurrencyRate("A6", 6, "BYN", 1, "Белорусский рубль", 30.5d));

        testWithListOFRates(testRates1);
        testWithListOFRates(testRates2);
    }

    @Test
    public void testQueryAllWhenDbIsEmpty() throws Exception {
        List<CurrencyRate> rates = dbHelper.queryAll();

        assertTrue(rates.isEmpty());
    }

    @Test
    public void testInsertAndReplaceNullList() throws Exception {
        dbHelper.resetRates(null);
        List<CurrencyRate> rates = dbHelper.queryAll();

        assertTrue(rates.isEmpty());
    }

    @Test
    public void testInsertAndReplaceEmptyList() throws Exception {
        dbHelper.resetRates(Collections.<CurrencyRate>emptyList());
        List<CurrencyRate> rates = dbHelper.queryAll();

        assertTrue(rates.isEmpty());
    }

    @Test
    public void testInsertWithNull() throws Exception {
        dbHelper.insert(null);
        List<CurrencyRate> rates = dbHelper.queryAll();

        assertTrue(rates.isEmpty());
    }

    @Test
    public void testQueryByCharCodeWhenEntryIsMissing() throws Exception {
        dbHelper.insert(new CurrencyRate("A1", 1, "USD", 1, "Американский доллар", 60.5));

        CurrencyRate rate = dbHelper.queryRateByCharCode("EUR");

        assertNull(rate);
    }

    private void testWithListOFRates(List<CurrencyRate> rates) {
        dbHelper.resetRates(rates);
        List<CurrencyRate> insertedRates = dbHelper.queryAll();

        assertEquals(rates.size(), insertedRates.size());
        assertEquals(rates.get(0), insertedRates.get(0));
        assertEquals(rates.get(1), insertedRates.get(1));
        assertEquals(rates.get(2), insertedRates.get(2));
    }
}
