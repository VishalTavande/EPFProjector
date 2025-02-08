package org.vishalta.epfcalculator.format;

import org.vishalta.epfcalculator.model.EPFBalance;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;

public abstract class Formatter {
    public abstract void formatEPFProjection(Map<String, EPFBalance> epfProjectionForYear);

    private final Currency inr = Currency.getInstance("INR");
    private final Locale loc = new Locale("hi", "IN");
    private final NumberFormat inrFormatter = NumberFormat.getCurrencyInstance(loc);

    public NumberFormat getNumberFormatter() {
        inrFormatter.setCurrency(inr);
        return inrFormatter;
    }


    public static Formatter getInstance(String name) {
        if(TabularFormatter.class.getSimpleName().equals(name)) {
            return new TabularFormatter();
        }
        if(HTMLFormatter.class.getSimpleName().equals(name)) {
            return new HTMLFormatter();
        }
        throw new RuntimeException("Formatter is not found or incorrect value is provided, please check if the application.properties file has correct formatter value.");
    }
}
