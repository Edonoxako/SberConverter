package com.edonoxako.sber.sberconverter;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public interface ConverterStateKeeper {
    void saveState(String leftCurrencyCharCode, String rightCurrencyCharCode, double leftCurrencyValue);
    String restoreLeftCurrency();
    String restoreRightCurrency();
    double restoreLeftCurrencyValue();
}
