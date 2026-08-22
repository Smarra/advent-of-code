package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.HashMap;

public class P8 implements AocProblem {

    public record Direction(String source, String left, String right){};

    private int getSteps(String source, HashMap<String, Direction> map, char[] coordinates, boolean part2) {
        String current = source;
        int steps = 0;
        while ((part2 && current.charAt(2) != 'Z') || (!part2 && !current.equals("ZZZ"))) {
            char coordinate = coordinates[steps % coordinates.length];
            Direction direction = map.get(current);
            String next;

            if (coordinate == 'L') {
                next = direction.left;
            } else {
                next = direction.right;
            }

            steps++;
            current = next;
        }

        return steps;
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        char[] coordinates = input.get(0).toCharArray();
        HashMap<String, Direction> map = new HashMap<>();

        for (String str : input.subList(2, input.size())) {
            String[] parts = str.split("[()]")[1].split("[ ,]");
            String source = str.split(" = ")[0];
            String left = parts[0];
            String right = parts[2];
            map.put(source, new Direction(source, left, right));
        }

        return getSteps("AAA", map, coordinates, false);
    }

    public long getLcm(long a, long b) {
        return a * (b / getGcd(a, b));
    }

    public long getGcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        char[] coordinates = input.get(0).toCharArray();
        HashMap<String, Direction> map = new HashMap<>();
        ArrayList<String> sources = new ArrayList<>();

        for (String str : input.subList(2, input.size())) {
            String[] parts = str.split("[()]")[1].split("[ ,]");
            String source = str.split(" = ")[0];
            String left = parts[0];
            String right = parts[2];

            if (source.charAt(2) == 'A') {
                sources.add(source);
            }
            map.put(source, new Direction(source, left, right));
        }

        long lcm = 1;
        for (String source : sources) {
            long steps = getSteps(source, map, coordinates, true);
            lcm = getLcm(lcm, steps);

        }
        return lcm;
    }
}
