package com.edonoxako.sber.sberconverter;

/**
 * Created by Eugeny.Martinenko on 12.07.2017.
 */

public class Currency {

    private String charCode;
    private String name;
    private double value;
    private double nominal;

    public Currency(String charCode, String name, double value, double nominal) {
        this.charCode = charCode;
        this.name = name;
        this.value = value;
        this.nominal = nominal;
    }

    public String getCharCode() {
        return charCode;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public double getNominal() {
        return nominal;
    }

    public void setNominal(double nominal) {
        this.nominal = nominal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        if (Double.compare(currency.value, value) != 0) return false;
        if (Double.compare(currency.nominal, nominal) != 0) return false;
        if (charCode != null ? !charCode.equals(currency.charCode) : currency.charCode != null)
            return false;
        return name != null ? name.equals(currency.name) : currency.name == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = charCode != null ? charCode.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(nominal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "charCode='" + charCode + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", nominal=" + nominal +
                '}';
    }
}
