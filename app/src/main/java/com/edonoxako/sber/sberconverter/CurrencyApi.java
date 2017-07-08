package com.edonoxako.sber.sberconverter;

import java.util.List;

/**
 * Created by edono on 08.07.2017.
 */

interface CurrencyApi {
    List<CurrencyRate> getExchangeRates();
}
