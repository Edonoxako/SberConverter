package com.edonoxako.sber.sberconverter;

import com.edonoxako.sber.sberconverter.http.HttpClient;
import com.edonoxako.sber.sberconverter.http.SimpleHttpClient;
import com.edonoxako.sber.sberconverter.parser.ResponseParser;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.Rule;
import org.junit.Test;

import java.io.InputStream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by edono on 08.07.2017.
 */

public class HttpClientUnitTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig()
                                                            .dynamicPort());

    @Test
    public void testGetRequest() throws Exception {
        stubFor(get(urlEqualTo("/sample/uri"))
                .willReturn(aResponse()
                        .withStatus(200)));

        ResponseParser parser = mock(ResponseParser.class);
        when(parser.parse(any(InputStream.class), eq(Object.class))).thenReturn(null);

        HttpClient client = new SimpleHttpClient(parser);
        client.getRequest("http://localhost:" + wireMockRule.port() + "/sample/uri", Object.class);

        verify(getRequestedFor(urlEqualTo("/sample/uri")));
    }
}
