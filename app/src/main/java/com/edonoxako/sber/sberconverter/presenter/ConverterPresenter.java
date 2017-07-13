package com.edonoxako.sber.sberconverter.presenter;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public interface ConverterPresenter {
    void setSecondCurrencyIndex(int currencyIndex);
    void setFirstCurrencyIndex(int currencyIndex);
    void setSecondCurrencyValue(double value);
    void setFirstCurrencyValue(double value);
    void refreshRates();
}
