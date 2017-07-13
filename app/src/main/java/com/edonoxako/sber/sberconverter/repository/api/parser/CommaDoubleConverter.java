package com.edonoxako.sber.sberconverter.repository.api.parser;


import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by edono on 08.07.2017.
 */

public class CommaDoubleConverter implements Converter<Double> {

    private NumberFormat format;

    public CommaDoubleConverter() {
        format = NumberFormat.getInstance(new Locale("RU"));
    }

    @Override
    public Double read(InputNode node) throws Exception {
        String value = node.getValue();
        Number number = format.parse(value);
        return number.doubleValue();
    }

    @Override
    public void write(OutputNode node, Double value) throws Exception {
        String formattedValue = this.format.format(value);
        node.setValue(formattedValue);
    }
}
