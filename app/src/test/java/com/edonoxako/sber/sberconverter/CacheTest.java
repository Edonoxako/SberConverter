package com.edonoxako.sber.sberconverter;

import com.edonoxako.sber.sberconverter.cache.SQLiteCache;
import com.edonoxako.sber.sberconverter.data.DbHelper;
import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Eugeny.Martinenko on 10.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CacheTest {

    @Mock
    private DbHelper dbHelper;

    private CurrencyRate testRate;
    private SQLiteCache cache;

    @Before
    public void setUp() throws Exception {
        cache = new SQLiteCache(dbHelper);
        testRate = new CurrencyRate("A1", 1, "USD", 1, "Доллар", 60.5);
    }

    @Test
    public void testSetCache() throws Exception {
        List<CurrencyRate> rates = Collections.singletonList(testRate);

        cache.set(rates);

        verify(dbHelper).resetRates(ArgumentMatchers.<CurrencyRate>anyList());
    }

    @Test
    public void testGetByCharCode() throws Exception {
        when(dbHelper.queryRateByCharCode(anyString()))
                .thenReturn(new CurrencyRate("A1", 1, "USD", 1, "Доллар", 60.5));

        CurrencyRate rate = cache.getByCharCode("USD");

        verify(dbHelper).queryRateByCharCode(anyString());
        assertEquals(testRate, rate);
    }

    @Test
    public void testGetAll() throws Exception {
        when(dbHelper.queryAll()).thenReturn(Collections.singletonList(testRate));

        List<CurrencyRate> rates = cache.getAll();

        verify(dbHelper).queryAll();
        assertEquals(testRate, rates.get(0));
    }
}
