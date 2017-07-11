package com.edonoxako.sber.sberconverter.evaluator;

import com.edonoxako.sber.sberconverter.model.CurrencyRate;

/**
 * Created by Eugeny.Martinenko on 10.07.2017.
 */

public class ExchangeRateEvaluator {

    public double evaluate(int nominal, CurrencyRate currencyFrom, CurrencyRate currencyTo) {
        if (nominal < 0) throw new IllegalArgumentException("Nominal is negative!");
        return nominal * ( getValue(currencyFrom) / getValue(currencyTo) );
    }

    private double getValue(CurrencyRate currencyRate) {
        return currencyRate.getValue() / currencyRate.getNominal();
    }
}
