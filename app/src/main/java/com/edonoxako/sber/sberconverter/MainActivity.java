package com.edonoxako.sber.sberconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.edonoxako.sber.sberconverter.model.CurrencyRate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ConverterView {

    private Spinner spinnerCurrencyLeft;
    private Spinner spinnerCurrencyRight;
    private EditText editNominalLeft;
    private EditText editNominalRight;
    private ImageButton buttonSwapCurrencies;

    private ConverterPresenter presenter;

    private NumberFormat currencyFormatter;
    private boolean notUserInput = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        presenter = ConverterPresenterFactory.newConverterPresenter(getSupportFragmentManager());
        currencyFormatter = new DecimalFormat("#0.00");
    }

    private void initViews() {
        spinnerCurrencyLeft = (Spinner) findViewById(R.id.spinner_currency_left);
        spinnerCurrencyRight = (Spinner) findViewById(R.id.spinner_currency_right);
        editNominalLeft = (EditText) findViewById(R.id.edit_nominal_left);
        editNominalRight = (EditText) findViewById(R.id.edit_nominal_right);
        buttonSwapCurrencies = (ImageButton) findViewById(R.id.button_swap_currencies);

        buttonSwapCurrencies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.swapCurrencies();
            }
        });

        spinnerCurrencyRight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.setRightCurrencyIndex(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCurrencyLeft.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.setLeftCurrencyIndex(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editNominalLeft.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (notUserInput) return;
                try {
                    Double value = currencyFormatter.parse(s.toString()).doubleValue();
                    presenter.setLeftCurrencyValue(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editNominalRight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (notUserInput) return;
                try {
                    Double value = currencyFormatter.parse(s.toString()).doubleValue();
                    presenter.setRightCurrencyValue(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void showLeftCurrency(int currencyCodeIndex) {
        spinnerCurrencyLeft.setSelection(currencyCodeIndex);
    }

    @Override
    public void showRightCurrency(int currencyCodeIndex) {
        spinnerCurrencyRight.setSelection(currencyCodeIndex);
    }

    @Override
    public void showLeftCurrencyValue(double value) {
        String formattedValue = currencyFormatter.format(value);
        notUserInput = true;
        editNominalLeft.setText(formattedValue);
        notUserInput = false;
    }

    @Override
    public void showRightCurrencyValue(double value) {
        String formattedValue = currencyFormatter.format(value);
        notUserInput = true;
        editNominalRight.setText(formattedValue);
        notUserInput = false;
    }

    @Override
    public void setCurrencyRatesList(List<CurrencyRate> rates) {
        List<String> charCodes = new ArrayList<>();
        for (CurrencyRate rate : rates) {
            charCodes.add(rate.getCharCode());
        }

        SpinnerAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, charCodes);
        spinnerCurrencyLeft.setAdapter(adapter);
        spinnerCurrencyRight.setAdapter(adapter);
    }
}
