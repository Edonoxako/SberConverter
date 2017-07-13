package com.edonoxako.sber.sberconverter.viewmodel;

import android.content.Context;

import com.edonoxako.sber.sberconverter.util.statekeeper.ConverterStateKeeper;
import com.edonoxako.sber.sberconverter.util.statekeeper.SharedPreferencesConverterStateKeeper;
import com.edonoxako.sber.sberconverter.repository.api.CurrencyApi;
import com.edonoxako.sber.sberconverter.repository.api.SimpleHttpCurrencyApi;
import com.edonoxako.sber.sberconverter.repository.cache.CurrencyCache;
import com.edonoxako.sber.sberconverter.repository.cache.SQLiteCache;
import com.edonoxako.sber.sberconverter.repository.cache.data.DbHelper;
import com.edonoxako.sber.sberconverter.evaluator.ExchangeRateEvaluator;
import com.edonoxako.sber.sberconverter.repository.api.http.HttpClient;
import com.edonoxako.sber.sberconverter.repository.api.http.SimpleHttpClient;
import com.edonoxako.sber.sberconverter.repository.api.parser.ResponseParser;
import com.edonoxako.sber.sberconverter.repository.api.parser.XmlResponseParser;
import com.edonoxako.sber.sberconverter.repository.CurrencyRepository;
import com.edonoxako.sber.sberconverter.util.networkchecker.DefaultNetworkChecker;
import com.edonoxako.sber.sberconverter.util.networkchecker.NetworkChecker;
import com.edonoxako.sber.sberconverter.repository.RemoteLocalRepository;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public class ConverterViewModelFactory {

    private static final String HOST_CBR = "http://www.cbr.ru";

    public static ConverterViewModel newConverterViewModel(Context context) {
        ExchangeRateEvaluator evaluator = new ExchangeRateEvaluator();

        NetworkChecker checker = new DefaultNetworkChecker(context);

        ResponseParser parser = new XmlResponseParser();
        HttpClient client = new SimpleHttpClient(parser);
        CurrencyApi api = new SimpleHttpCurrencyApi(HOST_CBR, client);

        DbHelper dbHelper = new DbHelper(context);
        CurrencyCache cache  = new SQLiteCache(dbHelper);

        CurrencyRepository repository = new RemoteLocalRepository(checker, api, cache);

        ConverterStateKeeper keeper = new SharedPreferencesConverterStateKeeper(context);

        return new ConverterViewModelImpl(evaluator, repository, keeper);
    }
}
