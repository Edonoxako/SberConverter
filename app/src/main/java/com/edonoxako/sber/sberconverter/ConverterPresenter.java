package com.edonoxako.sber.sberconverter;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public interface ConverterPresenter {
    void loadCurrencies();
    void setRightCurrencyIndex(int currencyIndex);
    void setLeftCurrencyIndex(int currencyIndex);
    void setRightCurrencyValue(double value);
    void setLeftCurrencyValue(double value);
    void swapCurrencies();
}
