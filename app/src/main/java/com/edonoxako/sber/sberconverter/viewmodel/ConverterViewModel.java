package com.edonoxako.sber.sberconverter.viewmodel;

import com.edonoxako.sber.sberconverter.model.CurrencyRate;
import com.edonoxako.sber.sberconverter.repository.api.ApiErrorException;

import java.util.List;

/**
 * Created by Eugeny.Martinenko on 11.07.2017.
 */

public interface ConverterViewModel {
    void init() throws UnknownCurrencyException, ApiErrorException;

    List<CurrencyRate> getAllRates() throws ApiErrorException;

    void setSecondCurrencyIndex(int index) throws ApiErrorException;

    void setFirstCurrencyIndex(int index) throws ApiErrorException;

    void setFirstCurrencyValue(double value) throws ApiErrorException;

    void setSecondCurrencyValue(double value) throws ApiErrorException;

    int getFirstCurrencyIndex();

    int getSecondCurrencyIndex();

    double getFirstCurrencyValue();

    double getSecondCurrencyValue();

    void saveState();
}
