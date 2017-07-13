package com.edonoxako.sber.sberconverter.util.statekeeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public class SharedPreferencesConverterStateKeeper implements ConverterStateKeeper {

    private static final String PREF_FIRST_CURRENCY = "firstCurrency";
    private static final String PREF_SECOND_CURRENCY = "secondCurrency";
    private static final String PREF_FIRST_CURRENCY_VALUE = "firstCurrencyValue";

    private static final String DEFAULT_FIRST_CURRENCY = "EUR";
    private static final String DEFAULT_SECOND_CURRENCY = "USD";
    private static final int DEFAULT_FIRST_CURRENCY_VALUE = 1;

    private final SharedPreferences preferences;

    public SharedPreferencesConverterStateKeeper(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void saveState(String firstCurrencyCharCode, String secondCurrencyCharCode, double firstCurrencyValue) {
        preferences.edit()
                .putString(PREF_FIRST_CURRENCY, firstCurrencyCharCode)
                .putString(PREF_SECOND_CURRENCY, secondCurrencyCharCode)
                .putFloat(PREF_FIRST_CURRENCY_VALUE, (float) firstCurrencyValue)
                .apply();
    }

    @Override
    public String restoreFirstCurrency() {
        return preferences.getString(PREF_FIRST_CURRENCY, DEFAULT_FIRST_CURRENCY);
    }

    @Override
    public String restoreSecondCurrency() {
        return preferences.getString(PREF_SECOND_CURRENCY, DEFAULT_SECOND_CURRENCY);
    }

    @Override
    public double restoreFirstCurrencyValue() {
        return preferences.getFloat(PREF_FIRST_CURRENCY_VALUE, DEFAULT_FIRST_CURRENCY_VALUE);
    }
}
