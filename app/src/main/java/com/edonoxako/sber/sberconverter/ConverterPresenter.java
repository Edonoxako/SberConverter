package com.edonoxako.sber.sberconverter;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public interface ConverterPresenter {
    void setRightCurrencyCode(String currencyCode);
    void setLeftCurrencyCode(String currencyCode);
    void setRightCurrencyValue(double value);
    void setLeftCurrencyValue(double value);
    void swapCurrencies();
}
