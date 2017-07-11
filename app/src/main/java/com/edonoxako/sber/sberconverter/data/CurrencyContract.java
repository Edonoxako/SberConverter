package com.edonoxako.sber.sberconverter.data;

import android.provider.BaseColumns;

/**
 * Created by Eugeny.Martinenko on 10.07.2017.
 */

public interface CurrencyContract {

    String DB_NAME = "CurrencyRatesDb";

    interface RatesTable extends BaseColumns {
        String TABLE_NAME = "Rates";

        String COLUMN_RATE_ID = "rateId";
        String COLUMN_NUM_CODE = "numCode";
        String COLUMN_CHAR_CODE = "charCode";
        String COLUMN_NOMINAL = "nominal";
        String COLUMN_NAME = "name";
        String COLUMN_VALUE = "value";
    }

    String TEXT_TYPE = " TEXT";
    String INTEGER_TYPE = " INTEGER";
    String REAL_TYPE = " REAL";
    String COMMA_SEP = ",";


    String SQL_CREATE_RATES =
            "CREATE TABLE " + RatesTable.TABLE_NAME + " (" +
                    RatesTable._ID + " INTEGER PRIMARY KEY, " +
                    RatesTable.COLUMN_RATE_ID + TEXT_TYPE + COMMA_SEP +
                    RatesTable.COLUMN_NUM_CODE + INTEGER_TYPE + COMMA_SEP +
                    RatesTable.COLUMN_CHAR_CODE + TEXT_TYPE + COMMA_SEP +
                    RatesTable.COLUMN_NOMINAL + INTEGER_TYPE + COMMA_SEP +
                    RatesTable.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    RatesTable.COLUMN_VALUE + REAL_TYPE +
                    " );";

    String SQL_DELETE_RATES =
            "DROP TABLE IF EXISTS " + RatesTable.TABLE_NAME;
}
