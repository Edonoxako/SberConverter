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
    private int leftCurrencyIndex;
    private int rightCurrencyIndex;
    private double leftCurrencyValue;
    private double rightCurrencyValue;

    public ConverterViewModelImpl(ExchangeRateEvaluator evaluator, CurrencyRepository repository, ConverterStateKeeper keeper) {
        this.evaluator = evaluator;
        this.repository = repository;
        this.keeper = keeper;
    }

    @Override
    public void init() {
        this.leftCurrencyIndex = findCurrencyIndexByCharCode(keeper.restoreLeftCurrency());
        this.rightCurrencyIndex = findCurrencyIndexByCharCode(keeper.restoreRightCurrency());
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

    private int findCurrencyIndexByCharCode(String charCode) {
        List<CurrencyRate> allRates = getAllRates();
        for (int i = 0; i < allRates.size(); i++) {
            CurrencyRate rate = allRates.get(i);
            if (rate.getCharCode().equals(charCode)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void swap() {
        int bufIndex = rightCurrencyIndex;
        double bufValue = rightCurrencyValue;

        rightCurrencyIndex = leftCurrencyIndex;
        rightCurrencyValue = leftCurrencyValue;

        leftCurrencyIndex = bufIndex;
        leftCurrencyValue = bufValue;
    }

    @Override
    public void setRightCurrencyIndex(int index) {
        rightCurrencyIndex = index;
        setLeftCurrencyValue(rightCurrencyValue);
    }

    @Override
    public void setLeftCurrencyIndex(int index) {
        leftCurrencyIndex = index;
        setRightCurrencyValue(leftCurrencyValue);
    }

    @Override
    public void setLeftCurrencyValue(double value) {
        leftCurrencyValue = value;
        CurrencyRate leftCurrency = getAllRates().get(leftCurrencyIndex);
        CurrencyRate rightCurrency = getAllRates().get(rightCurrencyIndex);
        rightCurrencyValue = evaluator.evaluate((int) value, leftCurrency, rightCurrency);
    }

    @Override
    public void setRightCurrencyValue(double value) {
        rightCurrencyValue = value;
        CurrencyRate leftCurrency = getAllRates().get(leftCurrencyIndex);
        CurrencyRate rightCurrency = getAllRates().get(rightCurrencyIndex);
        leftCurrencyValue = evaluator.evaluate((int) value, rightCurrency, leftCurrency);
    }

    @Override
    public int getLeftCurrencyIndex() {
        return leftCurrencyIndex;
    }

    @Override
    public int getRightCurrencyIndex() {
        return rightCurrencyIndex;
    }

    @Override
    public double getLeftCurrencyValue() {
        return leftCurrencyValue;
    }

    @Override
    public double getRightCurrencyValue() {
        return rightCurrencyValue;
    }

    @Override
    public void saveState() {
        CurrencyRate leftCurrency = getAllRates().get(leftCurrencyIndex);
        CurrencyRate rightCurrency = getAllRates().get(rightCurrencyIndex);

        keeper.saveState(leftCurrency.getCharCode(),
                rightCurrency.getCharCode(),
                getLeftCurrencyValue());
    }
}
