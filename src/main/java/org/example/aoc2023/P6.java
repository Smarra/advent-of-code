package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.Arrays;

public class P6 implements AocProblem {
    public long getPossibleSolutions(long time, long distance) {
        int ct = 0;
        for (int i = 0; i <= time; i++) {
            long acc = i;
            long startingTime = i;
            long currDistance = acc * (time - startingTime);

            if (currDistance > distance) {
                ct++;
            }
        }

        return ct;
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        String[] aux;
        aux =input.get(0).split(":")[1].replaceAll(" +", " ").trim().split(" ");
        Long[] times = Arrays.stream(aux)
                .map(Long::parseLong)
                .toArray(Long[]::new);
        aux = input.get(1).split(":")[1].replaceAll(" +", " ").trim().split(" ");
        Long[] distances = Arrays.stream(aux)
                .map(Long::parseLong)
                .toArray(Long[]::new);

        long result = 1;
        for (int i = 0; i < times.length; i++) {
            long time = times[i];
            long distance = distances[i];

            long noSolutions = getPossibleSolutions(time, distance);
            result *= noSolutions;
        }
        return result;
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        String[] aux;
        aux =input.get(0).split(":")[1].replaceAll(" +", "").trim().split(" ");
        Long[] times = Arrays.stream(aux)
                .map(Long::parseLong)
                .toArray(Long[]::new);
        aux = input.get(1).split(":")[1].replaceAll(" +", "").trim().split(" ");
        Long[] distances = Arrays.stream(aux)
                .map(Long::parseLong)
                .toArray(Long[]::new);

        long result = 1;
        for (int i = 0; i < times.length; i++) {
            long time = times[i];
            long distance = distances[i];

            long noSolutions = getPossibleSolutions(time, distance);
            result *= noSolutions;
        }
        return result;
    }
}
