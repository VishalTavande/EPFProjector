package org.vishalta.epfcalculator.util;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.generate;

public class Utils {

    public static String generatePattern(int size) {
        return generate(() -> "=")
                .limit(size)
                .collect(joining());
    }

    public static String generatePattern(int size, char ch) {
        return generate(() -> String.valueOf(ch))
                .limit(size)
                .collect(joining());
    }
}
