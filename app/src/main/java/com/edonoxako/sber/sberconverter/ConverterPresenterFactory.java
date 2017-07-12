package com.edonoxako.sber.sberconverter;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public class ConverterPresenterFactory {

    private static final String FRAGMENT_TAG = "presenterFragment";

    public static ConverterPresenter newConverterPresenter(FragmentManager manager) {
        RetainConverterPresenter presenter = (RetainConverterPresenter) manager.findFragmentByTag(FRAGMENT_TAG);

        if (presenter == null) {
            presenter = RetainConverterPresenter.getInstance();
            manager.beginTransaction()
                    .add(presenter, FRAGMENT_TAG)
                    .commit();
        }

        return presenter;
    }
}
