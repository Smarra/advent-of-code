package org.example.aoc2015;

import org.example.domain.AocProblem;

import java.util.ArrayList;

public class P1 implements AocProblem {

    public long solvePart1(ArrayList<String> input) {
        long floor = 0;

        for (Character chr : input.get(0).toCharArray()) {
            if (chr == ')') {
                floor--;
            } else {
                floor++;
            }
        }

        return floor;
    }

    public long solvePart2(ArrayList<String> input) {
        long floor = 0;
        String str = input.get(0);

        for (int i = 0; i < str.length(); i++) {
            Character chr = str.charAt(i);

            if (chr == ')') {
                floor--;
            } else {
                floor++;
            }

            if (floor == -1) {
                return i + 1;
            }
        }

        return -1;
    }
}
