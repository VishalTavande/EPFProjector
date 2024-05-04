package org.vishalta.epfcalculator.metadata;

public enum Type {
    CHARACTER('s'),
    INTEGRAL('i'),
    FLOATING('f'),
    DATETIME('t'),
    NEWLINE('n');

    private char value;
    Type(char c) {
        this.value = c;
    }
}