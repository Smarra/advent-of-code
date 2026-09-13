package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class P15 implements AocProblem {

    private int hash(String str) {
        int hash = 0;
        for (char chr : str.toCharArray()) {
            hash = (hash + chr) * 17 % 256;
        }
        return hash;
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        String[] parts = input.getFirst().split(",");

        long sum = 0;
        for (String str : parts) {
            sum += hash(str);
        }
        return sum;
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        ArrayList<LinkedHashMap<String, Integer>> boxes = new ArrayList<>(256);
        for (int i = 0; i < 256; i++) {
            boxes.add(new LinkedHashMap<>());
        }
        String[] parts = input.getFirst().split(",");

        int sum = 0;
        for (String str : parts) {
            if (str.contains("=")) {
                String toHash = str.split("=")[0];
                int focal = Integer.parseInt(str.split("=")[1]);
                boxes.get(hash(toHash)).put(toHash, focal);
            } else {
                String toHash = str.substring(0, str.length() - 1);
                boxes.get(hash(toHash)).remove(toHash);
            }
        }

        for (int i = 0; i < 256; i++) {
            LinkedHashMap<String, Integer> box = boxes.get(i);

            int ct = 1;
            for (int focal : boxes.get(i).values()) {
                sum += (i + 1) * ct * focal;
                ct++;
            }
        }
        return sum;
    }
}
