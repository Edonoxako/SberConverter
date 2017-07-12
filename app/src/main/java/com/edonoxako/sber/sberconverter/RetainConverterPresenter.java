package com.edonoxako.sber.sberconverter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public class RetainConverterPresenter extends Fragment implements ConverterPresenter {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setRightCurrencyCode(String currencyCode) {

    }

    @Override
    public void setLeftCurrencyCode(String currencyCode) {

    }

    @Override
    public void setRightCurrencyValue(double value) {

    }

    @Override
    public void setLeftCurrencyValue(double value) {

    }

    @Override
    public void swapCurrencies() {

    }
}
