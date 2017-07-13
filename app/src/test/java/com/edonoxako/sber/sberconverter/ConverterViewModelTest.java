package com.edonoxako.sber.sberconverter;

import com.edonoxako.sber.sberconverter.evaluator.ExchangeRateEvaluator;
import com.edonoxako.sber.sberconverter.model.CurrencyRate;
import com.edonoxako.sber.sberconverter.repository.CurrencyRepository;
import com.edonoxako.sber.sberconverter.util.statekeeper.ConverterStateKeeper;
import com.edonoxako.sber.sberconverter.viewmodel.ConverterViewModel;
import com.edonoxako.sber.sberconverter.viewmodel.ConverterViewModelImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by Eugeny.Martinenko on 11.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConverterViewModelTest {

    public static final int USD_INDEX = 0;
    public static final int RUB_INDEX = 1;
    public static final int EUR_INDEX = 2;

    @Mock
    private ExchangeRateEvaluator evaluator;
    @Mock
    private CurrencyRepository repository;
    @Mock
    private ConverterStateKeeper keeper;

    private CurrencyRate usdRate = new CurrencyRate("A1", 1, "USD", 1, "Доллар", 60.5);
    private CurrencyRate rubRate = new CurrencyRate("A2", 2, "RUB", 1, "Рубль", 1d);
    private CurrencyRate eurRate = new CurrencyRate("A3", 3, "EUR", 1, "Евро", 65.5);

    private ConverterViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        when(repository.getCurrencyRates()).thenReturn(Arrays.asList(usdRate, rubRate, eurRate));
        when(keeper.restoreLeftCurrency()).thenReturn("USD");
        when(keeper.restoreRightCurrency()).thenReturn("RUB");
        when(keeper.restoreLeftCurrencyValue()).thenReturn(1d);
        when(evaluator.evaluate(eq(1d), eq(usdRate), eq(rubRate))).thenReturn(60.5);
        when(evaluator.evaluate(eq(1d), eq(rubRate), eq(usdRate))).thenReturn(1 / 60.5);
        when(evaluator.evaluate(eq(1d), eq(eurRate), eq(usdRate))).thenReturn(65.5 / 60.5);

        viewModel = new ConverterViewModelImpl(evaluator, repository, keeper);
        viewModel.init();
    }

    @Test
    public void testSetLeftCurrency() throws Exception {
        viewModel.setRightCurrencyIndex(RUB_INDEX);
        viewModel.setLeftCurrencyIndex(USD_INDEX);
        viewModel.setLeftCurrencyValue(1d);
        int leftCurrencyIndex = viewModel.getLeftCurrencyIndex();
        int rightCurrencyIndex = viewModel.getRightCurrencyIndex();
        double leftValue = viewModel.getLeftCurrencyValue();
        double rightValue = viewModel.getRightCurrencyValue();

        assertEquals(USD_INDEX, leftCurrencyIndex);
        assertEquals(RUB_INDEX, rightCurrencyIndex);
        assertEquals(1, leftValue, 0.001);
        assertEquals(60.5, rightValue, 0.001);
    }

    @Test
    public void testSetRightCurrency() throws Exception {
        viewModel.setLeftCurrencyIndex(USD_INDEX);
        viewModel.setRightCurrencyIndex(RUB_INDEX);
        viewModel.setRightCurrencyValue(1d);
        int leftCurrencyIndex = viewModel.getLeftCurrencyIndex();
        int rightCurrencyIndex = viewModel.getRightCurrencyIndex();
        double leftValue = viewModel.getLeftCurrencyValue();
        double rightValue = viewModel.getRightCurrencyValue();

        assertEquals(USD_INDEX, leftCurrencyIndex);
        assertEquals(RUB_INDEX, rightCurrencyIndex);
        assertEquals(1 / 60.5, leftValue, 0.001);
        assertEquals(1, rightValue, 0.001);
    }

    @Test
    public void testChangeCurrency() throws Exception {
        viewModel.setLeftCurrencyIndex(RUB_INDEX);
        viewModel.setRightCurrencyIndex(USD_INDEX);
        viewModel.setLeftCurrencyValue(1d);
        viewModel.setLeftCurrencyIndex(EUR_INDEX);

        double leftCurrencyValue = viewModel.getLeftCurrencyValue();
        double rightCurrencyValue = viewModel.getRightCurrencyValue();

        assertEquals(1d, leftCurrencyValue, 0.001);
        assertEquals(65.5 / 60.5, rightCurrencyValue, 0.001);
    }
}
