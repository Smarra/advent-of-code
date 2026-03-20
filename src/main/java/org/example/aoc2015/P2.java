package org.example.aoc2015;

import org.example.domain.AocProblem;

import java.util.ArrayList;

public class P2 implements AocProblem {

    public long solvePart1(ArrayList<String> input) {
        long sum = 0;

        for (String str: input) {
            String[] parts = str.split("x");
            long l = Long.parseLong(parts[0]);
            long w = Long.parseLong(parts[1]);
            long h = Long.parseLong(parts[2]);
            sum += 2 * l * w + 2 * l * h + 2 * w * h;

            if (l >= w && l >= h) sum += w * h;
            else if (w >= l && w >= h) sum += l * h;
            else sum += w * l;
        }

        return sum;
    }

    public long solvePart2(ArrayList<String> input) {
        long sum = 0;

        for (String str: input) {
            String[] parts = str.split("x");
            long l = Long.parseLong(parts[0]);
            long w = Long.parseLong(parts[1]);
            long h = Long.parseLong(parts[2]);
            sum += l * h * w;

            if (l >= w && l >= h) sum += w + w + h + h;
            else if (w >= l && w >= h) sum += l + l + h + h;
            else sum += w + w + l + l;
        }

        return sum;
    }
}
