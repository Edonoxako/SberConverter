package com.edonoxako.sber.sberconverter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import java.util.List;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public class RetainConverterPresenter extends Fragment implements ConverterPresenter {

    public static RetainConverterPresenter getInstance() {
        return new RetainConverterPresenter();
    }


    private ConverterView view;
    private ConverterViewModel viewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ConverterView) {
            view = (ConverterView) context;
        } else {
            throw new ClassCastException("Activity doesn't implement ConverterView interface");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ConverterViewModelFactory.newConverterViewModel(getContext());
        new GetAllRatesTask().execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.saveState();
    }

    @Override
    public void loadCurrencies() {
        new GetAllRatesTask().execute();
    }

    @Override
    public void setRightCurrencyIndex(int currencyIndex) {
        new SetRightCurrencyTask().execute(currencyIndex);
    }

    @Override
    public void setLeftCurrencyIndex(int currencyIndex) {
        new SetLeftCurrencyTask().execute(currencyIndex);
    }

    @Override
    public void setRightCurrencyValue(double value) {
        new SetRightCurrencyValueTask().execute(value);
    }

    @Override
    public void setLeftCurrencyValue(double value) {
        new SetLeftCurrencyValueTask().execute(value);
    }

    @Override
    public void swapCurrencies() {
        new SwapTask().execute();
    }

    private void updateView() {
        updateLeftCurrency();
        updateRightCurrency();
    }

    private void updateLeftCurrency() {
        view.showLeftCurrency(viewModel.getLeftCurrencyIndex());
        view.showLeftCurrencyValue(viewModel.getLeftCurrencyValue());
    }

    private void updateRightCurrency() {
        view.showRightCurrency(viewModel.getRightCurrencyIndex());
        view.showRightCurrencyValue(viewModel.getRightCurrencyValue());
    }

    private class GetAllRatesTask extends AsyncTask<Void, Void, List<CurrencyRate>> {

        @Override
        protected List<CurrencyRate> doInBackground(Void... params) {
            viewModel.init();
            return viewModel.getAllRates();
        }

        @Override
        protected void onPostExecute(List<CurrencyRate> currencyRates) {
            super.onPostExecute(currencyRates);
            view.setCurrencyRatesList(currencyRates);
            updateView();
        }
    }

    private class SetRightCurrencyTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            viewModel.setRightCurrencyIndex(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateLeftCurrency();
        }
    }

    private class SetLeftCurrencyTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            viewModel.setLeftCurrencyIndex(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateRightCurrency();
        }
    }

    private class SetRightCurrencyValueTask extends AsyncTask<Double, Void, Void> {
        @Override
        protected Void doInBackground(Double... params) {
            viewModel.setRightCurrencyValue(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateLeftCurrency();
        }
    }

    private class SetLeftCurrencyValueTask extends AsyncTask<Double, Void, Void> {
        @Override
        protected Void doInBackground(Double... params) {
            viewModel.setLeftCurrencyValue(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateRightCurrency();
        }
    }

    private class SwapTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            viewModel.swap();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateView();
        }
    }
}
