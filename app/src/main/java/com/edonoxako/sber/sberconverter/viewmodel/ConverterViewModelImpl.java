package com.edonoxako.sber.sberconverter.viewmodel;

import com.edonoxako.sber.sberconverter.util.statekeeper.ConverterStateKeeper;
import com.edonoxako.sber.sberconverter.repository.api.ApiErrorException;
import com.edonoxako.sber.sberconverter.evaluator.ExchangeRateEvaluator;
import com.edonoxako.sber.sberconverter.model.CurrencyRate;
import com.edonoxako.sber.sberconverter.repository.CurrencyRepository;

import java.util.List;

/**
 * Created by Eugeny.Martinenko on 11.07.2017.
 */

public class ConverterViewModelImpl implements ConverterViewModel {

    private final ExchangeRateEvaluator evaluator;
    private final CurrencyRepository repository;
    private final ConverterStateKeeper keeper;

    private List<CurrencyRate> rates;
    private int firstCurrencyIndex;
    private int secondCurrencyIndex;
    private double firstCurrencyValue;
    private double secondCurrencyValue;

    public ConverterViewModelImpl(ExchangeRateEvaluator evaluator, CurrencyRepository repository, ConverterStateKeeper keeper) {
        this.evaluator = evaluator;
        this.repository = repository;
        this.keeper = keeper;
    }

    @Override
    public void init() throws UnknownCurrencyException, ApiErrorException {
        this.firstCurrencyIndex = findCurrencyIndexByCharCode(keeper.restoreFirstCurrency());
        this.secondCurrencyIndex = findCurrencyIndexByCharCode(keeper.restoreSecondCurrency());
        setFirstCurrencyValue(keeper.restoreFirstCurrencyValue());
    }

    @Override
    public List<CurrencyRate> getAllRates() throws ApiErrorException {
        if (rates == null || rates.isEmpty()) {
            rates = repository.getCurrencyRates();
        }
        return rates;
    }

    private int findCurrencyIndexByCharCode(String charCode) throws UnknownCurrencyException, ApiErrorException {
        List<CurrencyRate> allRates = getAllRates();
        for (int i = 0; i < allRates.size(); i++) {
            CurrencyRate rate = allRates.get(i);
            if (rate.getCharCode().equals(charCode)) {
                return i;
            }
        }
        throw new UnknownCurrencyException("Unknown currency: " + charCode);
    }

    public void setSecondCurrencyIndex(int index) throws ApiErrorException {
        secondCurrencyIndex = index;
        evaluateFirstCurrencyValue();
    }

    public void setFirstCurrencyIndex(int index) throws ApiErrorException {
        firstCurrencyIndex = index;
        evaluateSecondCurrencyValue();
    }

    public void setFirstCurrencyValue(double value) throws ApiErrorException {
        firstCurrencyValue = value;
        evaluateSecondCurrencyValue();
    }

    public void setSecondCurrencyValue(double value) throws ApiErrorException {
        secondCurrencyValue = value;
        evaluateFirstCurrencyValue();
    }

    public int getFirstCurrencyIndex() {
        return firstCurrencyIndex;
    }

    public int getSecondCurrencyIndex() {
        return secondCurrencyIndex;
    }

    public double getFirstCurrencyValue() {
        return firstCurrencyValue;
    }

    public double getSecondCurrencyValue() {
        return secondCurrencyValue;
    }

    @Override
    public void saveState() {
        if (rates != null && !rates.isEmpty()) {
            CurrencyRate leftCurrency = rates.get(firstCurrencyIndex);
            CurrencyRate rightCurrency = rates.get(secondCurrencyIndex);

            keeper.saveState(leftCurrency.getCharCode(),
                    rightCurrency.getCharCode(),
                    getFirstCurrencyValue());
        }
    }

    private void evaluateFirstCurrencyValue() throws ApiErrorException {
        CurrencyRate leftCurrency = getAllRates().get(firstCurrencyIndex);
        CurrencyRate rightCurrency = getAllRates().get(secondCurrencyIndex);
        firstCurrencyValue = evaluator.evaluate(secondCurrencyValue, rightCurrency, leftCurrency);
    }

    private void evaluateSecondCurrencyValue() throws ApiErrorException {
        CurrencyRate leftCurrency = getAllRates().get(firstCurrencyIndex);
        CurrencyRate rightCurrency = getAllRates().get(secondCurrencyIndex);
        secondCurrencyValue = evaluator.evaluate(firstCurrencyValue, leftCurrency, rightCurrency);
    }
}
