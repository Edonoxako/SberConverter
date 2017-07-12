package com.edonoxako.sber.sberconverter.cache;

import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import java.util.List;

/**
 * Created by Eugeny.Martinenko on 10.07.2017.
 */

public interface CurrencyCache {
    void set(List<CurrencyRate> rates);
    CurrencyRate getByCharCode(String chatCode);
    List<CurrencyRate> getAll();
}
