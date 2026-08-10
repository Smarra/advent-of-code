package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;

public class P1 implements AocProblem {

    public int lineStartsWithNumber(String line) {
        if (line.startsWith("one")) {
            return 1;
        }
        if (line.startsWith("two")) {
            return 2;
        }
        if (line.startsWith("three")) {
            return 3;
        }
        if (line.startsWith("four")) {
            return 4;
        }
        if (line.startsWith("five")) {
            return 5;
        }
        if (line.startsWith("six")) {
            return 6;
        }
        if (line.startsWith("seven")) {
            return 7;
        }
        if (line.startsWith("eight")) {
            return 8;
        }
        if (line.startsWith("nine")) {
            return 9;
        }

        return -1;
    }

    public long getNumberFromLine(String line, boolean enabledWords) {
        Character last = null;
        Character first = null;
        for (int idx = 0; idx < line.length(); idx++) {
            char chr = line.charAt(idx);
            if (chr >= '0' && chr <= '9') {
                if (first == null) {
                    first = chr;
                }

                last = chr;
            }

            if (enabledWords) {
                int wordNumber = lineStartsWithNumber(line.substring(idx));
                if (wordNumber >= 0) {
                    if (first == null) {
                        first = (char) ('0' + wordNumber);
                    }

                    last = (char) ('0' + wordNumber);
                }
            }
        }

        return (first - '0') * 10 + (last - '0');
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        long sum = 0;
        for (String line : input) {
            sum += getNumberFromLine(line, false);
        }

        return sum;
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        long sum = 0;
        for (String line : input) {
            sum += getNumberFromLine(line, true);
        }

        return sum;
    }
}
