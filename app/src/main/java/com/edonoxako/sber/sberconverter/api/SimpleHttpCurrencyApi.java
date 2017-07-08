package com.edonoxako.sber.sberconverter.api;

import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import java.util.List;

/**
 * Created by edono on 08.07.2017.
 */

public class SimpleHttpCurrencyApi implements CurrencyApi {
    public SimpleHttpCurrencyApi(String hostUrl) {

    }

    @Override
    public List<CurrencyRate> getExchangeRates() {
        return null;
    }
}
