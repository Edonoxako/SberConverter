package com.edonoxako.sber.sberconverter;

import com.edonoxako.sber.sberconverter.http.HttpClient;
import com.edonoxako.sber.sberconverter.http.HttpRequestException;
import com.edonoxako.sber.sberconverter.http.SimpleHttpClient;
import com.edonoxako.sber.sberconverter.parser.ResponseParser;
import com.github.tomakehurst.wiremock.common.Strings;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by edono on 08.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class HttpClientUnitTest {

    private static final String SAMPLE_URI = "/sample/uri";

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig()
                                                            .dynamicPort());
    @Mock
    public ResponseParser parser;

    private HttpClient client;

    @Before
    public void setUp() throws Exception {
        client = new SimpleHttpClient(parser);
        when(parser.parse(any(InputStream.class), eq(Object.class))).thenReturn(null);
    }

    @Test
    public void testGetRequest() throws Exception {
        setUpHostWithStatusCode(200);

        client.getRequest(testUrl(), Object.class);

        verify(getRequestedFor(urlEqualTo(SAMPLE_URI)));
    }

    @Test(expected = HttpRequestException.class)
    public void testErrorCodeOnGetRequest() throws Exception {
        setUpHostWithStatusCode(500);

        client.getRequest(testUrl(), Object.class);

        verify(getRequestedFor(urlEqualTo(SAMPLE_URI)));
    }

    @Test(expected = IOException.class)
    public void testBigDelayOnGetRequest() throws Exception {
        stubFor(get(urlEqualTo(SAMPLE_URI))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withFixedDelay(5000)));

        client.getRequest(testUrl(), Object.class);

        verify(getRequestedFor(urlEqualTo(SAMPLE_URI)));
    }

    @Test
    public void testParseOnGetRequest() throws Exception {
        setUpHostWithStatusCode(200);
        when(parser.parse(any(InputStream.class), eq(String.class))).thenReturn("Parsed response");

        String response = client.getRequest(testUrl(), String.class);

        verify(getRequestedFor(urlEqualTo(SAMPLE_URI)));
        Mockito.verify(parser).parse(any(InputStream.class), eq(String.class));
        assertEquals("Parsed response", response);
    }

    private void setUpHostWithStatusCode(int code) {
        stubFor(get(urlEqualTo(SAMPLE_URI))
                .willReturn(aResponse()
                        .withStatus(code)));
    }

    private String testUrl() {
        return "http://localhost:" + wireMockRule.port() + SAMPLE_URI;
    }
}
