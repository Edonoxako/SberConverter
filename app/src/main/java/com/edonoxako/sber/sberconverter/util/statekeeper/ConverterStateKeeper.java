package com.edonoxako.sber.sberconverter.util.statekeeper;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public interface ConverterStateKeeper {
    void saveState(String leftCurrencyCharCode, String rightCurrencyCharCode, double leftCurrencyValue);
    String restoreFirstCurrency();
    String restoreSecondCurrency();
    double restoreFirstCurrencyValue();
}
