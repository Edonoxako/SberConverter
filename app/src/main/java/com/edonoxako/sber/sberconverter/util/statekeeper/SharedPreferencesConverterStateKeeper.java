package com.edonoxako.sber.sberconverter.util.statekeeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public class SharedPreferencesConverterStateKeeper implements ConverterStateKeeper {

    private static final String PREF_LEFT_CURRENCY = "leftCurrency";
    private static final String PREF_RIGHT_CURRENCY = "rightCurrency";
    private static final String PREF_LEFT_CURRENCY_VALUE = "leftCurrencyValue";

    private static final String DEFAULT_LEFT_CURRENCY = "EUR";
    private static final String DEFAULT_RIGHT_CURRENCY = "USD";
    private static final int DEFAULT_LEFT_CURRENCY_VALUE = 1;

    private final SharedPreferences preferences;

    public SharedPreferencesConverterStateKeeper(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void saveState(String leftCurrencyCharCode, String rightCurrencyCharCode, double leftCurrencyValue) {
        preferences.edit()
                .putString(PREF_LEFT_CURRENCY, leftCurrencyCharCode)
                .putString(PREF_RIGHT_CURRENCY, rightCurrencyCharCode)
                .putFloat(PREF_LEFT_CURRENCY_VALUE, (float) leftCurrencyValue)
                .apply();
    }

    @Override
    public String restoreLeftCurrency() {
        return preferences.getString(PREF_LEFT_CURRENCY, DEFAULT_LEFT_CURRENCY);
    }

    @Override
    public String restoreRightCurrency() {
        return preferences.getString(PREF_RIGHT_CURRENCY, DEFAULT_RIGHT_CURRENCY);
    }

    @Override
    public double restoreLeftCurrencyValue() {
        return preferences.getFloat(PREF_LEFT_CURRENCY_VALUE, DEFAULT_LEFT_CURRENCY_VALUE);
    }
}
