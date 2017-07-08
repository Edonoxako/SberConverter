package com.edonoxako.sber.sberconverter;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 */
public class ExampleUnitTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig()
                                                            .dynamicPort()
                                                            .dynamicHttpsPort());

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}