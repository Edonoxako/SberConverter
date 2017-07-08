package com.edonoxako.sber.sberconverter.http;

import java.io.IOException;

/**
 * Created by edono on 08.07.2017.
 */

public interface HttpClient {
    <T> T getRequest(String uri, Class<T> clazz) throws Exception;
    void setHeader(String name, String value);
}
