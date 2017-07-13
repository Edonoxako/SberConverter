package com.edonoxako.sber.sberconverter.repository.api.parser;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.Registry;
import org.simpleframework.xml.convert.RegistryStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.io.InputStream;

/**
 * Created by edono on 08.07.2017.
 */

public class XmlResponseParser implements ResponseParser {
    @Override
    public <T> T parse(InputStream source, Class<T> clazz) throws Exception {
        Registry registry = new Registry();
        registry.bind(Double.class, CommaDoubleConverter.class);
        Strategy strategy = new RegistryStrategy(registry);
        Serializer serializer = new Persister(strategy);
        return serializer.read(clazz, source);
    }
}
