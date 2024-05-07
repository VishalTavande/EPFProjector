package org.vishalta.epfcalculator.format;

import org.vishalta.epfcalculator.model.EPFBalance;

import java.util.Map;

public abstract class Formatter {
    public abstract void formatEPFProjection(Map<String, EPFBalance> epfProjectionForYear);

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
