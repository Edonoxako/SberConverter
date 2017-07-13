package com.edonoxako.sber.sberconverter.repository.cache;

import com.edonoxako.sber.sberconverter.repository.cache.data.DbHelper;
import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import java.util.List;

/**
 * Created by Eugeny.Martinenko on 10.07.2017.
 */

public class SQLiteCache implements CurrencyCache {

    private DbHelper dbHelper;

    public SQLiteCache(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void set(List<CurrencyRate> rates) {
        dbHelper.resetRates(rates);
    }

    @Override
    public CurrencyRate getByCharCode(String chatCode) {
        return dbHelper.queryRateByCharCode(chatCode);
    }

    @Override
    public List<CurrencyRate> getAll() {
        return dbHelper.queryAll();
    }
}
