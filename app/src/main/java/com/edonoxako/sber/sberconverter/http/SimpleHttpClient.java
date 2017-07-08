package com.edonoxako.sber.sberconverter.http;


import com.edonoxako.sber.sberconverter.parser.ResponseParser;

/**
 * Created by edono on 08.07.2017.
 */

public class SimpleHttpClient implements HttpClient {

    public SimpleHttpClient(ResponseParser parser) {

    }

    @Override
    public <T> T getRequest(String uri, Class<T> clazz) {
        return null;
    }
}
