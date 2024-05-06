package org.vishalta.epfcalculator.format;

import org.vishalta.epfcalculator.model.Balance;
import org.vishalta.epfcalculator.model.EPFBalance;

import java.util.List;
import java.util.Map;

public abstract class Formatter {
    public abstract void formatEPFProjection(Map<String, EPFBalance> epfProjectionForYear);

    public static Formatter getInstance(String name) {
        if(TabularFormatter.class.getSimpleName().equals(name)) {
            return new TabularFormatter();
        }
        return null;
    }
}
