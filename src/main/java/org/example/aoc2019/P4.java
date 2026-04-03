package org.example.aoc2019;

import org.example.domain.AocProblem;

import java.util.ArrayList;

public class P4 implements AocProblem {

    public boolean isPassCorrect(char[] value, int part) {
        if (value.length != 6) {
            return false;
        }

        boolean doubles = false;
        for (int i = 0; i < value.length - 1; i++) {
            if (value[i] > value[i + 1]) {
                return false;
            }

            if (part == 1) {
                if (value[i] == value[i + 1]) {
                    doubles = true;
                }
            } else {
                if (value[i] == value[i + 1]
                        && (i - 1 < 0 || value[i - 1] != value[i])
                        && (i + 2 >= value.length || value[i + 1] != value[i + 2])
                ) {
                    doubles = true;
                }
            }
        }

        return doubles;
    }

    public long solve(ArrayList<String> input, int part) {
        long sum = 0;
        int left = Integer.parseInt(input.get(0).split("-")[0]);
        int right = Integer.parseInt(input.get(0).split("-")[1]);

        for (int i = left; i < right; i++) {
            char[] chars = ("" + i).toCharArray();
            if (isPassCorrect(chars, part)) {
                sum++;
            }
        }

        return sum;
    }

    public long solvePart1(ArrayList<String> input) {
        return solve(input, 1);
    }

    public long solvePart2(ArrayList<String> input) {
        return solve(input, 2);
    }
}