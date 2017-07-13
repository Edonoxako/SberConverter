package com.edonoxako.sber.sberconverter.viewmodel;

import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import java.util.List;

/**
 * Created by Eugeny.Martinenko on 11.07.2017.
 */

public interface ConverterViewModel {
    void init();

    List<CurrencyRate> getAllRates();

    void swap();

    void setRightCurrencyIndex(int index);

    void setLeftCurrencyIndex(int index);

    void setLeftCurrencyValue(double value);

    void setRightCurrencyValue(double value);

    int getLeftCurrencyIndex();

    int getRightCurrencyIndex();

    double getLeftCurrencyValue();

    double getRightCurrencyValue();

    void saveState();
}
