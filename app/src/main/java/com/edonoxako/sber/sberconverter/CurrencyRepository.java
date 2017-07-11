package com.edonoxako.sber.sberconverter;

import com.edonoxako.sber.sberconverter.api.ApiErrorException;
import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import java.util.List;

/**
 * Created by Eugeny.Martinenko on 11.07.2017.
 */

public interface CurrencyRepository {
    List<CurrencyRate> getCurrencyRates() throws ApiErrorException;

    List<CurrencyRate> getCurrencyRatesFromCache();
}
