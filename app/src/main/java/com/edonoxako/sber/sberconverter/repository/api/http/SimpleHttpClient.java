package com.edonoxako.sber.sberconverter.repository.api.http;


import com.edonoxako.sber.sberconverter.repository.api.parser.ResponseParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by edono on 08.07.2017.
 */

public class SimpleHttpClient implements HttpClient {

    public static final int READ_TIMEOUT = 3000;
    public static final int CONNECT_TIMEOUT = 3000;
    public static final String GET_REQUEST_METHOD = "GET";

    private ResponseParser parser;

    private Map<String, String> headers;

    public SimpleHttpClient(ResponseParser parser) {
        this.parser = parser;
        this.headers = new HashMap<>();
    }

    @Override
    public <T> T getRequest(String uri, Class<T> clazz) throws Exception {
        URL url = new URL(uri);
        T response = null;
        InputStream stream = null;
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            setUpConnection(connection);

            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new HttpRequestException(connection.getResponseCode());
            }

            stream = connection.getInputStream();
            if (stream != null) {
                response = parser.parse(stream, clazz);
            }
        } finally {
            release(stream, connection);
        }

        return response;
    }

    @Override
    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    private void release(InputStream stream, HttpURLConnection connection) throws IOException {
        // Close Stream and disconnect HTTP connection.
        if (stream != null) {
            stream.close();
        }
        if (connection != null) {
            connection.disconnect();
        }
    }

    private void setUpConnection(HttpURLConnection connection) throws ProtocolException {
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setRequestMethod(GET_REQUEST_METHOD);
        connection.setDoInput(true);

        for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
            connection.setRequestProperty(headerEntry.getKey(), headerEntry.getValue());
        }
    }
}
