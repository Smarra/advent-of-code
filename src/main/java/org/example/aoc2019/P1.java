package org.example.aoc2019;

import org.example.domain.AocProblem;

import java.util.ArrayList;

public class P1 implements AocProblem {

    public long solvePart1(ArrayList<String> input) {
        long sum = 0;

        for (String s : input) {
            long nr = Long.parseLong(s);
            long additive = (nr / 3) - 2;
            System.out.println(additive);

            sum += additive;
        }

        return sum;
    }

    public long solvePart2(ArrayList<String> input) {
        long sum = 0;

        for (String s : input) {
            long nr = Long.parseLong(s);
            long additive = 0;

            while (nr > 8) {
                long remn = (nr / 3) - 2;
                additive += remn;
                nr = remn;
            }

            sum += additive;
            System.out.println(additive);
        }

        return sum;
    }
}