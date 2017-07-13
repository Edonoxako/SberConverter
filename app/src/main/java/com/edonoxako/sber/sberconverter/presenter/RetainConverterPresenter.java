package com.edonoxako.sber.sberconverter.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.edonoxako.sber.sberconverter.R;
import com.edonoxako.sber.sberconverter.repository.api.ApiErrorException;
import com.edonoxako.sber.sberconverter.view.ConverterView;
import com.edonoxako.sber.sberconverter.viewmodel.ConverterViewModel;
import com.edonoxako.sber.sberconverter.viewmodel.ConverterViewModelFactory;
import com.edonoxako.sber.sberconverter.model.CurrencyRate;
import com.edonoxako.sber.sberconverter.viewmodel.UnknownCurrencyException;

import java.util.List;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public class RetainConverterPresenter extends Fragment implements ConverterPresenter {

    private static final String TAG = "ConverterPresenter";

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
    public void refreshRates() {
        new GetAllRatesTask().execute();
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


    /*
    * Set of async tasks are needed to put all viewmodel job out of main thread
    */
    private class GetAllRatesTask extends AsyncTask<Void, Void, Result<List<CurrencyRate>>> {

        @Override
        protected Result<List<CurrencyRate>> doInBackground(Void... params) {
            try {
                viewModel.init();
                return new Result<>(viewModel.getAllRates());
            } catch (UnknownCurrencyException | ApiErrorException e) {
                Log.e(TAG, "doInBackground: error during GetAllRatesTask", e);
                return new Result<>(e);
            }
        }

        @Override
        protected void onPostExecute(Result<List<CurrencyRate>> result) {
            super.onPostExecute(result);
            if (result.isError()) {
                view.showMessage(getString(R.string.error_message));
            } else {
                view.setCurrencyRatesList(result.data);
                updateView();
            }
        }
    }

    private class SetRightCurrencyTask extends AsyncTask<Integer, Void, Result> {
        @Override
        protected Result doInBackground(Integer... params) {
            try {
                viewModel.setRightCurrencyIndex(params[0]);
                return new Result();
            } catch (ApiErrorException e) {
                Log.e(TAG, "doInBackground: error during SetRightCurrencyTask", e);
                return new Result(e);
            }
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            if (result.isError()) {
                view.showMessage(getString(R.string.error_message));
            } else {
                updateLeftCurrency();
            }
        }
    }

    private class SetLeftCurrencyTask extends AsyncTask<Integer, Void, Result> {
        @Override
        protected Result doInBackground(Integer... params) {
            try {
                viewModel.setLeftCurrencyIndex(params[0]);
                return new Result();
            } catch (ApiErrorException e) {
                Log.e(TAG, "doInBackground: error during SetRightCurrencyTask", e);
                return new Result(e);
            }
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            if (result.isError()) {
                view.showMessage(getString(R.string.error_message));
            } else {
                updateRightCurrency();
            }
        }
    }

    private class SetRightCurrencyValueTask extends AsyncTask<Double, Void, Result> {
        @Override
        protected Result doInBackground(Double... params) {
            try {
                viewModel.setRightCurrencyValue(params[0]);
                return new Result();
            } catch (ApiErrorException e) {
                Log.e(TAG, "doInBackground: error during SetRightCurrencyValueTask", e);
                return new Result(e);
            }
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            if (result.isError()) {
                view.showMessage(getString(R.string.error_message));
            } else {
                updateLeftCurrency();
            }
        }
    }

    private class SetLeftCurrencyValueTask extends AsyncTask<Double, Void, Result> {
        @Override
        protected Result doInBackground(Double... params) {
            try {
                viewModel.setLeftCurrencyValue(params[0]);
                return new Result();
            } catch (ApiErrorException e) {
                Log.e(TAG, "doInBackground: error during SetLeftCurrencyValueTask", e);
                return new Result(e);
            }
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            if (result.isError()) {
                view.showMessage(getString(R.string.error_message));
            } else {
                updateRightCurrency();
            }
        }
    }

    /*
    * Data class to make result and error handling that are happened in worker thread
    * a little bit easier
    */
    private class Result<T> {
        T data;
        Throwable error;

        public Result() {
        }

        public Result(T data) {
            this.data = data;
        }

        public Result(Throwable error) {
            this.error = error;
        }

        public boolean isError() {
            return error != null;
        }
    }
}
