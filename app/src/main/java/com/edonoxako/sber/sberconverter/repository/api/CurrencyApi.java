package com.edonoxako.sber.sberconverter.repository.api;

import com.edonoxako.sber.sberconverter.model.Rates;

/**
 * Created by edono on 08.07.2017.
 */

public interface CurrencyApi {
    Rates getExchangeRates() throws ApiErrorException;
}
