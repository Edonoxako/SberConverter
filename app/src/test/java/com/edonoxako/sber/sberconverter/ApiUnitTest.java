package com.edonoxako.sber.sberconverter;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 */
public class ApiUnitTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig()
                                                            .dynamicPort());

    @Test
    public void getCurrencyDataWhenResponseIsOk() throws Exception {
        stubFor(get(urlEqualTo("/scripts/XML_daily.asp"))
                .withHeader("Accept", equalTo("text/xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml")
                        .withBody(mockResponseData())));

        CurrencyRate audRate = new CurrencyRate(36, "AUD", 1, "Австралийский доллар", 45.8701d);
        CurrencyRate gbpRate = new CurrencyRate(826, "GBP", 1, "Фунт стерлингов Соединенного королевства", 78.2273d);
        CurrencyRate usdRate = new CurrencyRate(840, "USD", 1, "Доллар США", 60.3792);
        CurrencyRate eurRate = new CurrencyRate(978, "EUR", 1, "Евро", 68.9470d);
        CurrencyRate jpyRate = new CurrencyRate(392, "JPY", 100, "Японских иен", 53.0783d);

        CurrencyApi api = new SimpleHttpCurrencyApi("http://localhost:" + wireMockRule.port());
        List<CurrencyRate> rates = api.getExchangeRates();

        assertEquals(audRate, rates.get(0));
        assertEquals(gbpRate, rates.get(1));
        assertEquals(usdRate, rates.get(2));
        assertEquals(eurRate, rates.get(3));
        assertEquals(jpyRate, rates.get(4));
    }

    private String mockResponseData() {
        return "<ValCurs Date=\"08.07.2017\" name=\"Foreign Currency Market\">\n" +
                  "<Valute ID=\"R01010\">\n" +
                    "<NumCode>036</NumCode>\n" +
                    "<CharCode>AUD</CharCode>\n" +
                    "<Nominal>1</Nominal>\n" +
                    "<Name>Австралийский доллар</Name>\n" +
                    "<Value>45,8701</Value>\n" +
                  "</Valute>\n" +
                  "<Valute ID=\"R01035\">\n" +
                    "<NumCode>826</NumCode>\n" +
                    "<CharCode>GBP</CharCode>\n" +
                    "<Nominal>1</Nominal>\n" +
                    "<Name>Фунт стерлингов Соединенного королевства</Name>\n" +
                    "<Value>78,2273</Value>\n" +
                  "</Valute>\n" +
                  "<Valute ID=\"R01235\">\n" +
                    "<NumCode>840</NumCode>\n" +
                    "<CharCode>USD</CharCode>\n" +
                    "<Nominal>1</Nominal>\n" +
                    "<Name>Доллар США</Name>\n" +
                    "<Value>60,3792</Value>\n" +
                  "</Valute>\n" +
                  "<Valute ID=\"R01239\">\n" +
                    "<NumCode>978</NumCode>\n" +
                    "<CharCode>EUR</CharCode>\n" +
                    "<Nominal>1</Nominal>\n" +
                    "<Name>Евро</Name>\n" +
                    "<Value>68,9470</Value>\n" +
                  "</Valute>\n" +
                  "<Valute ID=\"R01820\">\n" +
                    "<NumCode>392</NumCode>\n" +
                    "<CharCode>JPY</CharCode>\n" +
                    "<Nominal>100</Nominal>\n" +
                    "<Name>Японских иен</Name>\n" +
                    "<Value>53,0783</Value>\n" +
                  "</Valute>\n" +
                "</ValCurs>";
    }
}