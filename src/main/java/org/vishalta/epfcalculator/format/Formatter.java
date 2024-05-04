package org.vishalta.epfcalculator.format;

import org.vishalta.epfcalculator.model.Balance;

import java.util.List;

public abstract class Formatter {
    public abstract void printEPFProjection(List<Balance> epfProjectionForYear, int currentYear, double currentBalance);

    public static Formatter getInstance(String name) {
        if(TabularFormatter.class.getSimpleName().equals(name)) {
            return new TabularFormatter();
        }
        return null;
    }
}
