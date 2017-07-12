package com.edonoxako.sber.sberconverter;

import android.content.Context;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public class SharedPreferencesConverterStateKeeper implements ConverterStateKeeper {
    public SharedPreferencesConverterStateKeeper(Context context) {

    }

    @Override
    public void saveState(String leftCurrencyCharCode, String rightCurrencyCharCode) {

    }

    @Override
    public String restoreLeftCurrency() {
        return null;
    }

    @Override
    public String restoreRightCurrency() {
        return null;
    }

    @Override
    public double restoreLeftCurrencyValue() {
        return 0;
    }
}
