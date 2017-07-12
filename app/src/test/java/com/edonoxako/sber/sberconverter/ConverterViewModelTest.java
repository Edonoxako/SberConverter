package com.edonoxako.sber.sberconverter;

import com.edonoxako.sber.sberconverter.evaluator.ExchangeRateEvaluator;
import com.edonoxako.sber.sberconverter.model.CurrencyRate;
import com.edonoxako.sber.sberconverter.repository.CurrencyRepository;

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

    @Mock
    private ExchangeRateEvaluator evaluator;
    @Mock
    private CurrencyRepository repository;
    @Mock
    private ConverterStateKeeper keeper;

    private CurrencyRate usdRate = new CurrencyRate("A1", 1, "USD", 1, "Доллар", 60.5);
    private CurrencyRate rubRate = new CurrencyRate("A2", 2, "RUB", 1, "Рубль", 1d);

    private ConverterViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        when(repository.getCurrencyRates()).thenReturn(Arrays.asList(usdRate, rubRate));
        when(keeper.restoreLeftCurrency()).thenReturn("USD");
        when(keeper.restoreRightCurrency()).thenReturn("RUB");
        when(keeper.restoreLeftCurrencyValue()).thenReturn(1d);
        when(evaluator.evaluate(eq(1), eq(usdRate), eq(rubRate))).thenReturn(60.5);
        when(evaluator.evaluate(eq(1), eq(rubRate), eq(usdRate))).thenReturn(1 / 60.5);

        viewModel = new ConverterViewModelImpl(evaluator, repository, keeper);
    }

    @Test
    public void testSetLeftCurrency() throws Exception {
        viewModel.setRightCurrencyCharCode("RUB");
        viewModel.setLeftCurrencyCharCode("USD");
        viewModel.setLeftCurrencyValue(1d);
        String leftCharCode = viewModel.getLeftCurrencyCharCode();
        String rightCharCode = viewModel.getRightCurrencyCharCode();
        double leftValue = viewModel.getLeftCurrencyValue();
        double rightValue = viewModel.getRightCurrencyValue();

        assertEquals("USD", leftCharCode);
        assertEquals("RUB", rightCharCode);
        assertEquals(1, leftValue, 0.001);
        assertEquals(60.5, rightValue, 0.001);
    }

    @Test
    public void testSetRightCurrency() throws Exception {
        viewModel.setLeftCurrencyCharCode("USD");
        viewModel.setRightCurrencyCharCode("RUB");
        viewModel.setRightCurrencyValue(1d);
        String leftCharCode = viewModel.getLeftCurrencyCharCode();
        String rightCharCode = viewModel.getRightCurrencyCharCode();
        double leftValue = viewModel.getLeftCurrencyValue();
        double rightValue = viewModel.getRightCurrencyValue();

        assertEquals("USD", leftCharCode);
        assertEquals("RUB", rightCharCode);
        assertEquals(1 / 60.5, leftValue, 0.001);
        assertEquals(1, rightValue, 0.001);
    }

    @Test
    public void testSwap() throws Exception {
        viewModel.setLeftCurrencyCharCode("USD");
        viewModel.setRightCurrencyCharCode("RUB");
        viewModel.setLeftCurrencyValue(1d);
        viewModel.swap();

        String leftCharCode = viewModel.getLeftCurrencyCharCode();
        String rightCharCode = viewModel.getRightCurrencyCharCode();
        double leftValue = viewModel.getLeftCurrencyValue();
        double rightValue = viewModel.getRightCurrencyValue();

        assertEquals("RUB", leftCharCode);
        assertEquals("USD", rightCharCode);
        assertEquals(60.5, leftValue, 0.001);
        assertEquals(1, rightValue, 0.001);
    }
}