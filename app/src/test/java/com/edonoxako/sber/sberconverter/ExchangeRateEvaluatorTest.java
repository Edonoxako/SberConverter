package com.edonoxako.sber.sberconverter;

import junit.framework.Assert;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Eugeny.Martinenko on 10.07.2017.
 */

public class ExchangeRateEvaluatorTest {

    @Test
    public void testEvaluate() throws Exception {
        ExchangeRateEvaluator evaluator = new ExchangeRateEvaluator();
        CurrencyRate currencyUsd = new CurrencyRate(1, "USD", 1, "Доллар", 61.1234);
        CurrencyRate currencyEur = new CurrencyRate(1, "EUR", 1, "Евро", 67.4321);

        double result = evaluator.evaluate(1, currencyUsd, currencyEur);

        assertEquals(1 * (61.1234 / 1) / (67.4321 / 1), result, 0.001);
    }
}
