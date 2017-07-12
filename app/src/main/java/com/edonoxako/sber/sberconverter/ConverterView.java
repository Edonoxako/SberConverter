package com.edonoxako.sber.sberconverter;

import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import java.util.List;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public interface ConverterView {
    void showLeftCurrencyCode(String currencyCode);
    void showRightCurrencyCode(String currencyCode);
    void showLeftCurrencyValue(double value);
    void showRightCurrencyValue(double value);
    void setCurrencyRatesList(List<CurrencyRate> rates);
}
