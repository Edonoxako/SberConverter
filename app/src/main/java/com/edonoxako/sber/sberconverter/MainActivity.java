package com.edonoxako.sber.sberconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ConverterView {

    private Spinner spinnerCurrencyLeft;
    private Spinner spinnerCurrencyRight;
    private EditText editNominalLeft;
    private EditText editNominalRight;
    private ImageButton buttonSwapCurrencies;

    private NumberFormat currencyFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        currencyFormatter = new DecimalFormat("#0.00");
    }

    private void initViews() {
        spinnerCurrencyLeft = (Spinner) findViewById(R.id.spinner_currency_left);
        spinnerCurrencyRight = (Spinner) findViewById(R.id.spinner_currency_right);
        editNominalLeft = (EditText) findViewById(R.id.edit_nominal_left);
        editNominalRight = (EditText) findViewById(R.id.edit_nominal_right);
        buttonSwapCurrencies = (ImageButton) findViewById(R.id.button_swap_currencies);
    }

    @Override
    public void showLeftCurrencyCode(int currencyCodeIndex) {
        spinnerCurrencyLeft.setSelection(currencyCodeIndex);
    }

    @Override
    public void showRightCurrencyCode(int currencyCodeIndex) {
        spinnerCurrencyRight.setSelection(currencyCodeIndex);
    }

    @Override
    public void showLeftCurrencyValue(double value) {
        String formattedValue = currencyFormatter.format(value);
        editNominalLeft.setText(formattedValue);
    }

    @Override
    public void showRightCurrencyValue(double value) {
        String formattedValue = currencyFormatter.format(value);
        editNominalRight.setText(formattedValue);
    }

    @Override
    public void setCurrencyRatesList(List<CurrencyRate> rates) {

    }
}
