package com.edonoxako.sber.sberconverter.parser;

import java.io.InputStream;

/**
 * Created by edono on 08.07.2017.
 */

public interface ResponseParser {
    <T> T parse(InputStream source, Class<T> clazz);
}
