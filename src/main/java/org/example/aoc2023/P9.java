package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class P9 implements AocProblem {
    public long solve(ArrayList<String> input, boolean part1) {
        long sum = 0;

        for (String line : input) {
            ArrayList<ArrayList<Long>> m = new ArrayList<>();
            ArrayList<Long> l = Arrays.stream(line.split(" "))
                    .map(Long::parseLong)
                    .collect(Collectors.toCollection(ArrayList::new));

            m.add(l);

            for (int i = l.size() - 1; i >= 1; i--) {
                ArrayList<Long> ll = new ArrayList<>();
                for (int j = 0; j < l.size() - 1; j++) {
                    ll.add(l.get(j + 1) - l.get(j));
                }

                m.add(ll);
                l = ll;
            }

            Long diff = 0L;
            for (int i = m.size() - 2; i >= 0; i--) {
                if (part1) {
                    diff += m.get(i).get(m.size() - i - 1);
                } else {
                    diff = m.get(i).get(0) - diff;
                }
            }

            sum += diff;
        }

        return sum;
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        return solve(input, true);
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        return solve(input, false);
    }
}
