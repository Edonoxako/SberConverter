package com.edonoxako.sber.sberconverter;

import java.util.List;

/**
 * Created by edono on 08.07.2017.
 */

class SimpleHttpCurrencyApi implements CurrencyApi {
    public SimpleHttpCurrencyApi(String hostUrl) {

    }

    @Override
    public List<CurrencyRate> getExchangeRates() {
        return null;
    }
}
