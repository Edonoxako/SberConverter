package com.edonoxako.sber.sberconverter;

import com.edonoxako.sber.sberconverter.model.CurrencyRate;
import com.edonoxako.sber.sberconverter.model.Rates;
import com.edonoxako.sber.sberconverter.parser.ResponseParser;
import com.edonoxako.sber.sberconverter.parser.XmlResponseParser;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

/**
 * Created by edono on 08.07.2017.
 */

public class ParserUnitTest {

    private ResponseParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new XmlResponseParser();
    }

    @Test
    public void testParseSingleRate() throws Exception {
        CurrencyRate rate = parser.parse(toInputStream(singleCurrencyRate()), CurrencyRate.class);

        assertEquals(840, rate.getNumCode());
        assertEquals("USD", rate.getCharCode());
        assertEquals(1, rate.getNominal());
        assertEquals("Доллар США", rate.getName());
        assertEquals(60.3792, rate.getValue(), 0.001);
    }

    @Test
    public void testParseRatesList() throws Exception {
        CurrencyRate audRate = new CurrencyRate("R01010", 36, "AUD", 1, "Австралийский доллар", 45.8701d);
        CurrencyRate gbpRate = new CurrencyRate("R01035", 826, "GBP", 1, "Фунт стерлингов Соединенного королевства", 78.2273d);
        CurrencyRate usdRate = new CurrencyRate("R01235", 840, "USD", 1, "Доллар США", 60.3792);
        CurrencyRate eurRate = new CurrencyRate("R01239", 978, "EUR", 1, "Евро", 68.9470d);
        CurrencyRate jpyRate = new CurrencyRate("R01820", 392, "JPY", 100, "Японских иен", 53.0783d);

        Rates rates = parser.parse(toInputStream(currencyRatesArray()), Rates.class);

        assertEquals(audRate, rates.asList().get(0));
        assertEquals(gbpRate, rates.asList().get(1));
        assertEquals(usdRate, rates.asList().get(2));
        assertEquals(eurRate, rates.asList().get(3));
        assertEquals(jpyRate, rates.asList().get(4));
    }

    private InputStream toInputStream(String string) {
        return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
    }

    private String singleCurrencyRate() {
        return "<Valute ID=\"R01235\">" +
                   "<NumCode>840</NumCode>" +
                   "<CharCode>USD</CharCode>" +
                   "<Nominal>1</Nominal>" +
                   "<Name>Доллар США</Name>" +
                   "<Value>60,3792</Value>" +
                "</Valute>";
    }

    private String currencyRatesArray() {
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
