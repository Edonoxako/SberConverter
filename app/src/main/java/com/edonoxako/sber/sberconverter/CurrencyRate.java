package com.edonoxako.sber.sberconverter;

/**
 * Created by Eugeny.Martinenko on 10.07.2017.
 */

public class CurrencyRate {

    private int numCode;
    private String charCode;
    private int nominal;
    private String name;
    private double value;

    public CurrencyRate(int numCode, String charCode, int nominal, String name, double value) {
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
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

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "CurrencyRate{" +
                "numCode=" + numCode +
                ", charCode='" + charCode + '\'' +
                ", nominal=" + nominal +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyRate that = (CurrencyRate) o;

        if (numCode != that.numCode) return false;
        if (nominal != that.nominal) return false;
        if (Double.compare(that.value, value) != 0) return false;
        if (charCode != null ? !charCode.equals(that.charCode) : that.charCode != null)
            return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = numCode;
        result = 31 * result + (charCode != null ? charCode.hashCode() : 0);
        result = 31 * result + nominal;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
