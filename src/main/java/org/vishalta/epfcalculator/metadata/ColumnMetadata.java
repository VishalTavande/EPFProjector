package org.vishalta.epfcalculator.metadata;

public class ColumnMetadata {
    private final String name;
    private final int width;
    private final Type type;

    public ColumnMetadata(String name, int width, Type type) {
        this.name = name;
        this.width = width;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public Type getType() {
        return type;
    }
}