package com.edonoxako.sber.sberconverter.http;


import com.edonoxako.sber.sberconverter.parser.ResponseParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by edono on 08.07.2017.
 */

public class SimpleHttpClient implements HttpClient {

    public static final int READ_TIMEOUT = 3000;
    public static final int CONNECT_TIMEOUT = 3000;
    public static final String GET_REQUEST_METHOD = "GET";

    private ResponseParser parser;

    public SimpleHttpClient(ResponseParser parser) {
        this.parser = parser;
    }

    @Override
    public <T> T getRequest(String uri, Class<T> clazz) throws IOException, HttpRequestException {
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
    }
}
