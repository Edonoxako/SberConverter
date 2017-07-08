package com.edonoxako.sber.sberconverter.api;

import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import java.util.List;

/**
 * Created by edono on 08.07.2017.
 */

public interface CurrencyApi {
    List<CurrencyRate> getExchangeRates();
}
