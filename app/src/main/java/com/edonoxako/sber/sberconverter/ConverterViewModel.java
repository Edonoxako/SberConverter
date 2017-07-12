package com.edonoxako.sber.sberconverter;

import com.edonoxako.sber.sberconverter.api.ApiErrorException;
import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import java.util.List;

/**
 * Created by Eugeny.Martinenko on 11.07.2017.
 */

public interface ConverterViewModel {
    List<CurrencyRate> getAllRates() throws ApiErrorException;

    void swap();

    void setRightCurrencyCharCode(String charCode);

    void setLeftCurrencyCharCode(String charCode);

    void setLeftCurrencyValue(double value);

    void setRightCurrencyValue(double value);

    String getLeftCurrencyCharCode();

    String getRightCurrencyCharCode();

    double getLeftCurrencyValue();

    double getRightCurrencyValue();
}
