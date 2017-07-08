package com.edonoxako.sber.sberconverter.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Objects;

/**
 * Created by edono on 08.07.2017.
 */

@Root
public class CurrencyRate {
    @Attribute(name = "ID") private String id;
    @Element(name = "NumCode") private int numCode;
    @Element(name = "CharCode") private String charCode;
    @Element(name = "Nominal") private int nominal;
    @Element(name = "Name") private String name;
    @Element(name = "Value") private Double value;

    public CurrencyRate() {}

    public CurrencyRate(String id, int numCode, String charCode, int nominal, String name, Double value) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public int getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyRate that = (CurrencyRate) o;
        return getNumCode() == that.getNumCode() &&
                getNominal() == that.getNominal() &&
                Double.compare(that.getValue(), getValue()) == 0 &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getCharCode(), that.getCharCode()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumCode(), getCharCode(), getNominal(), getName(), getValue());
    }

    @Override
    public String toString() {
        return "CurrencyRate{" +
                "id='" + id + '\'' +
                ", numCode=" + numCode +
                ", charCode='" + charCode + '\'' +
                ", nominal=" + nominal +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
