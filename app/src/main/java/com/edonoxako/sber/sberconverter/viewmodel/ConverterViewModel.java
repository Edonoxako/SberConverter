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

    void setRightCurrencyIndex(int index) throws ApiErrorException;

    void setLeftCurrencyIndex(int index) throws ApiErrorException;

    void setLeftCurrencyValue(double value) throws ApiErrorException;

    void setRightCurrencyValue(double value) throws ApiErrorException;

    int getLeftCurrencyIndex();

    int getRightCurrencyIndex();

    double getLeftCurrencyValue();

    double getRightCurrencyValue();

    void saveState();
}
