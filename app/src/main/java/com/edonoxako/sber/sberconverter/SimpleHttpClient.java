package com.edonoxako.sber.sberconverter;

/**
 * Created by edono on 08.07.2017.
 */

class SimpleHttpClient implements HttpClient {

    public SimpleHttpClient(ResponseParser parser) {

    }

    @Override
    public <T> T getRequest(String uri, Class<T> clazz) {
        return null;
    }
}
