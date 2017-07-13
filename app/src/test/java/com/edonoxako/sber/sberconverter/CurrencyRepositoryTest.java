package com.edonoxako.sber.sberconverter;

import com.edonoxako.sber.sberconverter.repository.api.ApiErrorException;
import com.edonoxako.sber.sberconverter.repository.api.CurrencyApi;
import com.edonoxako.sber.sberconverter.repository.cache.CurrencyCache;
import com.edonoxako.sber.sberconverter.model.CurrencyRate;
import com.edonoxako.sber.sberconverter.model.Rates;
import com.edonoxako.sber.sberconverter.repository.CurrencyRepository;
import com.edonoxako.sber.sberconverter.util.networkchecker.NetworkChecker;
import com.edonoxako.sber.sberconverter.repository.RemoteLocalRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Eugeny.Martinenko on 11.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CurrencyRepositoryTest {

    @Mock
    private NetworkChecker checker;

    @Mock
    private CurrencyApi api;

    @Mock
    private CurrencyCache cache;

    private CurrencyRepository repository;
    private CurrencyRate testRate;

    @Before
    public void setUp() throws Exception {
        repository = new RemoteLocalRepository(checker, api, cache);
        testRate = new CurrencyRate("A1", 1, "USD", 1, "Доллар", 60.5);
    }

    @Test
    public void testGetCurrencyRatesFromApi() throws Exception {
        when(api.getExchangeRates()).thenReturn(new Rates("", "name", Collections.singletonList(testRate)));
        when(checker.networkIsAvailable()).thenReturn(true);

        List<CurrencyRate> rates = repository.getCurrencyRates();

        verify(checker).networkIsAvailable();
        verify(api).getExchangeRates();
        assertEquals(testRate, rates.get(0));
    }

    @Test
    public void testGetCurrencyRateFromCache() throws Exception {
        when(checker.networkIsAvailable()).thenReturn(false);
        when(cache.getAll()).thenReturn(Collections.singletonList(testRate));

        List<CurrencyRate> rates = repository.getCurrencyRates();

        verify(checker).networkIsAvailable();
        verify(cache).getAll();
        assertEquals(testRate, rates.get(0));
    }

    @Test
    public void testSetRatesToCacheOnGetRatesFromApi() throws Exception {
        when(api.getExchangeRates()).thenReturn(new Rates("", "name", Collections.singletonList(testRate)));
        when(checker.networkIsAvailable()).thenReturn(true);

        List<CurrencyRate> rates = repository.getCurrencyRates();

        verify(cache).set(ArgumentMatchers.<CurrencyRate>anyList());
        assertEquals(testRate, rates.get(0));
    }

    @Test(expected = ApiErrorException.class)
    public void testExceptionOnErrorLoadingRatesFromApi() throws Exception {
        when(api.getExchangeRates()).thenThrow(ApiErrorException.class);
        when(checker.networkIsAvailable()).thenReturn(true);

        repository.getCurrencyRates();
    }

    @Test
    public void testGetRatesFromCacheExplicitly() throws Exception {
        when(cache.getAll()).thenReturn(Collections.singletonList(testRate));

        List<CurrencyRate> rates = repository.getCurrencyRatesFromCache();

        verify(cache).getAll();
        assertEquals(testRate, rates.get(0));
    }
}
