package com.edonoxako.sber.sberconverter.model;

import android.content.pm.LabeledIntent;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;
import java.util.Objects;

/**
 * Created by edono on 08.07.2017.
 */
@Root
public class Rates {

    @Attribute(name = "Date", required = false) private String date;
    @Attribute(name = "name", required = false) private String name;
    @ElementList(inline = true, entry = "Valute") private List<CurrencyRate> rates;

    public Rates() {
    }

    public Rates(String date, String name, List<CurrencyRate> rates) {
        this.date = date;
        this.name = name;
        this.rates = rates;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public List<CurrencyRate> asList() {
        return rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rates rates1 = (Rates) o;
        return Objects.equals(getDate(), rates1.getDate()) &&
                Objects.equals(getName(), rates1.getName()) &&
                Objects.equals(rates, rates1.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getName(), rates);
    }

    @Override
    public String toString() {
        return "Rates{" +
                "date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", rates=" + rates +
                '}';
    }
}
