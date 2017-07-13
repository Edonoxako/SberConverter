package com.edonoxako.sber.sberconverter.evaluator;

import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Eugeny.Martinenko on 10.07.2017.
 */

public class ExchangeRateEvaluator {

    private static final int VALUE_SCALE = 4;

    public double evaluate(double amount, CurrencyRate currencyFrom, CurrencyRate currencyTo) {
        if (amount < 0) throw new IllegalArgumentException("Nominal is negative!");
        BigDecimal amountDecimal = new BigDecimal(amount);
        return amountDecimal
                .multiply(getValue(currencyFrom)
                        .divide(getValue(currencyTo), VALUE_SCALE, RoundingMode.CEILING))
                .doubleValue();
    }

    private BigDecimal getValue(CurrencyRate currencyRate) {
        BigDecimal nominal = new BigDecimal(currencyRate.getNominal());
        BigDecimal value = new BigDecimal(currencyRate.getValue());
        return value.divide(nominal, VALUE_SCALE, RoundingMode.CEILING);
    }
}
