package com.edonoxako.sber.sberconverter.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import com.edonoxako.sber.sberconverter.R;
import com.edonoxako.sber.sberconverter.model.CurrencyRate;
import com.edonoxako.sber.sberconverter.presenter.ConverterPresenter;
import com.edonoxako.sber.sberconverter.presenter.ConverterPresenterFactory;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ConverterView {

    private AppCompatSpinner spinnerCurrencyFirst;
    private AppCompatSpinner spinnerCurrencySecond;
    private AppCompatEditText editNominalFirst;
    private AppCompatEditText editNominalSecond;
    private AppCompatTextView textErrorMessage;
    private LinearLayoutCompat layoutFirstCurrency;
    private LinearLayoutCompat layoutSecondCurrency;
    private AppCompatButton buttonRefresh;
    private FrameLayout layoutRefreshContainer;

    private ConverterPresenter presenter;

    private NumberFormat currencyFormatter;

    private boolean notUserCurrencyValueInput = false;
    private boolean notUserFirstCurrencyInput = false;
    private boolean notUserSecondCurrencyInput = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        presenter = ConverterPresenterFactory.newConverterPresenter(getSupportFragmentManager());

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator('.');
        currencyFormatter = new DecimalFormat("#.##", otherSymbols);
    }

    private void initViews() {
        spinnerCurrencyFirst = (AppCompatSpinner) findViewById(R.id.spinner_currency_first);
        spinnerCurrencySecond = (AppCompatSpinner) findViewById(R.id.spinner_currency_second);
        editNominalFirst = (AppCompatEditText) findViewById(R.id.edit_nominal_first);
        editNominalSecond = (AppCompatEditText) findViewById(R.id.edit_nominal_second);
        textErrorMessage = (AppCompatTextView) findViewById(R.id.text_error);
        layoutFirstCurrency = (LinearLayoutCompat) findViewById(R.id.layout_first_currency);
        layoutSecondCurrency = (LinearLayoutCompat) findViewById(R.id.layout_second_currency);
        buttonRefresh = (AppCompatButton) findViewById(R.id.refresh_button);
        layoutRefreshContainer = (FrameLayout) findViewById(R.id.layout_refresh_container);

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.refreshRates();
            }
        });

        spinnerCurrencySecond.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!notUserSecondCurrencyInput) {
                    presenter.setSecondCurrencyIndex(position);
                    notUserSecondCurrencyInput = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCurrencySecond.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notUserSecondCurrencyInput = false;
                return false;
            }
        });

        spinnerCurrencyFirst.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notUserFirstCurrencyInput = false;
                return false;
            }
        });

        spinnerCurrencyFirst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!notUserFirstCurrencyInput) {
                    presenter.setFirstCurrencyIndex(position);
                    notUserFirstCurrencyInput = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editNominalFirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (notUserCurrencyValueInput) {
                    notUserCurrencyValueInput = false;
                    return;
                }
                try {
                    Double value;
                    if (s.toString().isEmpty()) {
                        value = 0d;
                    } else {
                        value = currencyFormatter.parse(s.toString()).doubleValue();
                    }
                    presenter.setFirstCurrencyValue(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editNominalSecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (notUserCurrencyValueInput) {
                    notUserCurrencyValueInput = false;
                    return;
                }
                try {
                    Double value;
                    if (s.toString().isEmpty()) {
                        value = 0d;
                    } else {
                        value = currencyFormatter.parse(s.toString()).doubleValue();
                    }
                    presenter.setSecondCurrencyValue(value);
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
    public void showFirstCurrency(int currencyCodeIndex) {
        spinnerCurrencyFirst.setSelection(currencyCodeIndex);
    }

    @Override
    public void showSecondCurrency(int currencyCodeIndex) {
        spinnerCurrencySecond.setSelection(currencyCodeIndex);
    }

    @Override
    public void showFirstCurrencyValue(double value) {
        String formattedValue = currencyFormatter.format(value);
        notUserCurrencyValueInput = true;
        editNominalFirst.setText(formattedValue);
    }

    @Override
    public void showSecondCurrencyValue(double value) {
        String formattedValue = currencyFormatter.format(value);
        notUserCurrencyValueInput = true;
        editNominalSecond.setText(formattedValue);
    }

    @Override
    public void setCurrencyRatesList(List<CurrencyRate> rates) {
        hideErrorMessage();

        List<String> charCodes = new ArrayList<>();
        for (CurrencyRate rate : rates) {
            charCodes.add(String.format("%s (%s)", rate.getName(), rate.getCharCode()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, charCodes);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinnerCurrencyFirst.setAdapter(adapter);
        spinnerCurrencySecond.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        displayErrorMessage();
        textErrorMessage.setText(message);
    }

    private void hideErrorMessage() {
        textErrorMessage.setVisibility(View.GONE);
        layoutRefreshContainer.setVisibility(View.GONE);
        layoutFirstCurrency.setVisibility(View.VISIBLE);
        layoutSecondCurrency.setVisibility(View.VISIBLE);
    }

    private void displayErrorMessage() {
        layoutFirstCurrency.setVisibility(View.GONE);
        layoutSecondCurrency.setVisibility(View.GONE);
        textErrorMessage.setVisibility(View.VISIBLE);
        layoutRefreshContainer.setVisibility(View.VISIBLE);
    }
}
