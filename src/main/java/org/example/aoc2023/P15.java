package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;

public class P15 implements AocProblem {
    @Override
    public long solvePart1(ArrayList<String> input) {
        String[] parts = input.getFirst().split(",");

        long sum = 0;
        for (String str : parts) {
            int hash = 0;
            for (char chr : str.toCharArray()) {
                hash = (hash + chr) * 17 % 256;
            }
            System.out.println(hash);
            sum += hash;
        }
        return sum;
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        return 0;
    }
}
