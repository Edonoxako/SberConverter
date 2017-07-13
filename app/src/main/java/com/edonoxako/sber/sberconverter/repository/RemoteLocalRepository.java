package com.edonoxako.sber.sberconverter.repository;

import com.edonoxako.sber.sberconverter.repository.api.ApiErrorException;
import com.edonoxako.sber.sberconverter.repository.api.CurrencyApi;
import com.edonoxako.sber.sberconverter.repository.cache.CurrencyCache;
import com.edonoxako.sber.sberconverter.model.CurrencyRate;
import com.edonoxako.sber.sberconverter.util.networkchecker.NetworkChecker;

import java.util.List;

/**
 * Created by Eugeny.Martinenko on 11.07.2017.
 */

public class RemoteLocalRepository implements CurrencyRepository {
    private final NetworkChecker checker;
    private final CurrencyApi api;
    private final CurrencyCache cache;

    public RemoteLocalRepository(NetworkChecker checker, CurrencyApi api, CurrencyCache cache) {
        this.checker = checker;
        this.api = api;
        this.cache = cache;
    }

    @Override
    public List<CurrencyRate> getCurrencyRates() throws ApiErrorException {
        if (checker.networkIsAvailable()) {
            List<CurrencyRate> rates = api.getExchangeRates().asList();
            cache.set(rates);
            return rates;
        } else {
            return cache.getAll();
        }
    }

    @Override
    public List<CurrencyRate> getCurrencyRatesFromCache() {
        return cache.getAll();
    }
}
