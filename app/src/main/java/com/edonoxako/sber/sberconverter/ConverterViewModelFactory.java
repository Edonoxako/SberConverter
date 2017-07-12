package com.edonoxako.sber.sberconverter;

import android.content.Context;

import com.edonoxako.sber.sberconverter.api.CurrencyApi;
import com.edonoxako.sber.sberconverter.api.SimpleHttpCurrencyApi;
import com.edonoxako.sber.sberconverter.cache.CurrencyCache;
import com.edonoxako.sber.sberconverter.cache.SQLiteCache;
import com.edonoxako.sber.sberconverter.data.DbHelper;
import com.edonoxako.sber.sberconverter.evaluator.ExchangeRateEvaluator;
import com.edonoxako.sber.sberconverter.http.HttpClient;
import com.edonoxako.sber.sberconverter.http.SimpleHttpClient;
import com.edonoxako.sber.sberconverter.parser.ResponseParser;
import com.edonoxako.sber.sberconverter.parser.XmlResponseParser;
import com.edonoxako.sber.sberconverter.repository.CurrencyRepository;
import com.edonoxako.sber.sberconverter.repository.DefaultNetworkChecker;
import com.edonoxako.sber.sberconverter.repository.NetworkChecker;
import com.edonoxako.sber.sberconverter.repository.RemoteLocalRepository;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public class ConverterViewModelFactory {
    public static ConverterViewModel newConverterViewModel(Context context) {
        ExchangeRateEvaluator evaluator = new ExchangeRateEvaluator();

        NetworkChecker checker = new DefaultNetworkChecker(context);

        ResponseParser parser = new XmlResponseParser();
        HttpClient client = new SimpleHttpClient(parser);
        CurrencyApi api = new SimpleHttpCurrencyApi("http://localhost", client);

        DbHelper dbHelper = new DbHelper(context);
        CurrencyCache cache  = new SQLiteCache(dbHelper);

        CurrencyRepository repository = new RemoteLocalRepository(checker, api, cache);

        ConverterStateKeeper keeper = new SharedPreferencesConverterStateKeeper(context);

        return new ConverterViewModelImpl(evaluator, repository, keeper);
    }
}
