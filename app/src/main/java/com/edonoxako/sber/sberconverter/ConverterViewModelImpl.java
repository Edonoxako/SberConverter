package com.edonoxako.sber.sberconverter;

import com.edonoxako.sber.sberconverter.api.ApiErrorException;
import com.edonoxako.sber.sberconverter.evaluator.ExchangeRateEvaluator;
import com.edonoxako.sber.sberconverter.model.CurrencyRate;
import com.edonoxako.sber.sberconverter.repository.CurrencyRepository;

import java.util.Collections;
import java.util.List;

/**
 * Created by Eugeny.Martinenko on 11.07.2017.
 */

public class ConverterViewModelImpl implements ConverterViewModel {

    private final ExchangeRateEvaluator evaluator;
    private final CurrencyRepository repository;
    private final ConverterStateKeeper keeper;

    private List<CurrencyRate> rates;
    private CurrencyRate leftCurrency;
    private CurrencyRate rightCurrency;
    private double leftCurrencyValue;
    private double rightCurrencyValue;

    public ConverterViewModelImpl(ExchangeRateEvaluator evaluator, CurrencyRepository repository, ConverterStateKeeper keeper) {
        this.evaluator = evaluator;
        this.repository = repository;
        this.keeper = keeper;
        this.leftCurrency = findRateByCharCode(keeper.restoreLeftCurrency());
        this.rightCurrency = findRateByCharCode(keeper.restoreRightCurrency());
        setLeftCurrencyValue(keeper.restoreLeftCurrencyValue());
    }

    @Override
    public List<CurrencyRate> getAllRates() {
        if (rates == null) {
            try {
                rates = repository.getCurrencyRates();
            } catch (ApiErrorException e) {
                e.printStackTrace();
                rates = Collections.emptyList();
            }
        }
        return rates;
    }

    private CurrencyRate findRateByCharCode(String charCode) {
        List<CurrencyRate> allRates = getAllRates();
        for (CurrencyRate rate : allRates) {
            if (rate.getCharCode().equals(charCode)) {
                return rate;
            }
        }
        return null;
    }

    @Override
    public void swap() {
        CurrencyRate bufRate = rightCurrency;
        double bufRateValue = rightCurrencyValue;

        rightCurrency = leftCurrency;
        rightCurrencyValue = leftCurrencyValue;

        leftCurrency = bufRate;
        leftCurrencyValue = bufRateValue;
    }

    @Override
    public void setRightCurrencyCharCode(String charCode) {
        List<CurrencyRate> allRates = getAllRates();
        for (CurrencyRate rate : allRates) {
            if (rate.getCharCode().equals(charCode)) {
                rightCurrency = rate;
                setLeftCurrencyValue(rightCurrencyValue);
            }
        }
    }

    @Override
    public void setLeftCurrencyCharCode(String charCode) {
        List<CurrencyRate> allRates = getAllRates();
        for (CurrencyRate rate : allRates) {
            if (rate.getCharCode().equals(charCode)) {
                leftCurrency = rate;
                setRightCurrencyValue(leftCurrencyValue);
            }
        }
    }

    @Override
    public void setLeftCurrencyValue(double value) {
        leftCurrencyValue = value;
        rightCurrencyValue = evaluator.evaluate((int) value, leftCurrency, rightCurrency);
    }

    @Override
    public void setRightCurrencyValue(double value) {
        rightCurrencyValue = value;
        leftCurrencyValue = evaluator.evaluate((int) value, rightCurrency, leftCurrency);
    }

    @Override
    public String getLeftCurrencyCharCode() {
        return leftCurrency.getCharCode();
    }

    @Override
    public String getRightCurrencyCharCode() {
        return rightCurrency.getCharCode();
    }

    @Override
    public double getLeftCurrencyValue() {
        return leftCurrencyValue;
    }

    @Override
    public double getRightCurrencyValue() {
        return rightCurrencyValue;
    }
}
