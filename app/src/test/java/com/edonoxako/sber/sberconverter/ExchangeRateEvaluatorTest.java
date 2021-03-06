package com.edonoxako.sber.sberconverter;

import com.edonoxako.sber.sberconverter.evaluator.ExchangeRateEvaluator;
import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Eugeny.Martinenko on 10.07.2017.
 */

public class ExchangeRateEvaluatorTest {

    private ExchangeRateEvaluator evaluator;

    @Before
    public void setUp() throws Exception {
        evaluator = new ExchangeRateEvaluator();
    }

    @Test
    public void testEvaluate() throws Exception {
        CurrencyRate currencyUsd = new CurrencyRate("A1", 1, "USD", 1, "Доллар", 61.1234);
        CurrencyRate currencyEur = new CurrencyRate("A2", 1, "EUR", 1, "Евро", 67.4321);

        double result = evaluator.evaluate(1, currencyUsd, currencyEur);

        assertEquals(1 * (61.1234 / 1) / (67.4321 / 1), result, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateNegativeNominal() throws Exception {
        CurrencyRate currencyUsd = new CurrencyRate("A1", 1, "USD", 1, "Доллар", 61.1234);
        CurrencyRate currencyEur = new CurrencyRate("A2", 1, "EUR", 1, "Евро", 67.4321);

        evaluator.evaluate(-1, currencyUsd, currencyEur);
    }
}
