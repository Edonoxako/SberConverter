package com.edonoxako.sber.sberconverter;

import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import java.util.List;

/**
 * Created by Eugeny.Martinenko on 11.07.2017.
 */

public interface ConverterViewModel {
    List<CurrencyRate> getAllRates();
    CurrencyRate getFirstCurrency();
    CurrencyRate getSecondCurrency();
    void swap();
}
