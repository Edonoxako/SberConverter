package com.edonoxako.sber.sberconverter;

/**
 * Created by edono on 08.07.2017.
 */

interface HttpClient {
    <T> T getRequest(String uri, Class<T> clazz);
}
