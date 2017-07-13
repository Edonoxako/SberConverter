package com.edonoxako.sber.sberconverter.view;

import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import java.util.List;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public interface ConverterView {
    void showFirstCurrency(int currencyCodeIndex);
    void showSecondCurrency(int currencyCodeIndex);
    void showFirstCurrencyValue(double value);
    void showSecondCurrencyValue(double value);
    void setCurrencyRatesList(List<CurrencyRate> rates);
    void showMessage(String message);
}
