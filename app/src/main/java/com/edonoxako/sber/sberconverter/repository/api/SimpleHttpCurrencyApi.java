package com.edonoxako.sber.sberconverter.repository.api;

import com.edonoxako.sber.sberconverter.repository.api.http.HttpClient;
import com.edonoxako.sber.sberconverter.model.Rates;

/**
 * Created by edono on 08.07.2017.
 */

public class SimpleHttpCurrencyApi implements CurrencyApi {

    public static final String RATES_API_PATH = "/scripts/XML_daily.asp";

    private final String hostUrl;
    private final HttpClient client;

    public SimpleHttpCurrencyApi(String hostUrl, HttpClient client) {
        this.hostUrl = hostUrl;
        this.client = client;
        this.client.setHeader("Accept", "text/xml");
    }

    @Override
    public Rates getExchangeRates() throws ApiErrorException {
        Rates response;
        try {
            response = client.getRequest(hostUrl + RATES_API_PATH, Rates.class);
        } catch (Exception e) {
            throw new ApiErrorException(e);
        }
        return response;
    }
}
