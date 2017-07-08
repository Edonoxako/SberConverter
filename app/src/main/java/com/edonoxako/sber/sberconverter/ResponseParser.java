package com.edonoxako.sber.sberconverter;

import android.widget.TextView;

import java.io.InputStream;

/**
 * Created by edono on 08.07.2017.
 */

interface ResponseParser {
    <T> T parse(InputStream source, Class<T> clazz);
}
