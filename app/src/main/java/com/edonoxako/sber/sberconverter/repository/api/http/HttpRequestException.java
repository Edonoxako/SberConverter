package com.edonoxako.sber.sberconverter.repository.api.http;

/**
 * Created by edono on 08.07.2017.
 */

public class HttpRequestException extends Exception {

    public HttpRequestException() {
    }

    public HttpRequestException(int statusCode) {
        super("Response status code: " + statusCode);
    }
}
