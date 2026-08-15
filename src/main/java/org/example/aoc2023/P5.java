package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.Arrays;

public class P5 implements AocProblem {
    public record Range(long destStart, long sourceStart, long length){};

    public long getLocation(ArrayList<ArrayList<Range>> iter, long value) {
        for (ArrayList<Range> ranges : iter) {
            for (Range range : ranges) {
                if (value >= range.sourceStart && value < range.sourceStart + range.length) {
                    value = range.destStart + (value - range.sourceStart);
                    break;
                }
            }
        }

        return value;
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        String[] seedsStr = input.getFirst().split(":")[1].trim().split(" ");
        Long[] seeds = Arrays.stream(seedsStr).map(Long::parseLong).toArray(Long[]::new);
        ArrayList<ArrayList<Range>> iter = new ArrayList<>();
        ArrayList<Range> acc = null;
        int row = 1;

        while(row < input.size()) {
            if (input.get(row).isEmpty()) {
                if (acc != null) {
                    iter.add(acc);
                }
                row += 2; // skip the name, it doesn't matter for now
                acc = new ArrayList<>();
            }
            Long[] rangeArray = Arrays
                    .stream(input.get(row).split(" "))
                    .map(Long::parseLong)
                    .toArray(Long[]::new);
            Range range = new Range(rangeArray[0], rangeArray[1], rangeArray[2]);
            acc.add(range);

            row++;
        }

        if (acc != null) {
            iter.add(acc);
        }

        long min = Long.MAX_VALUE;
        for (Long seed : seeds) {
            long value = getLocation(iter, seed);
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        ArrayList<ArrayList<Range>> iter = new ArrayList<>();
        ArrayList<Range> acc = null;
        int row = 1;

        while(row < input.size()) {
            if (input.get(row).isEmpty()) {
                if (acc != null) {
                    iter.add(acc);
                }
                row += 2; // skip the name, it doesn't matter for now
                acc = new ArrayList<>();
            }
            Long[] rangeArray = Arrays
                    .stream(input.get(row).split(" "))
                    .map(Long::parseLong)
                    .toArray(Long[]::new);
            Range range = new Range(rangeArray[0], rangeArray[1], rangeArray[2]);
            acc.add(range);

            row++;
        }

        if (acc != null) {
            iter.add(acc);
        }

        String[] seedsStr = input.getFirst().split(":")[1].trim().split(" ");
        Long[] seeds = Arrays.stream(seedsStr).map(Long::parseLong).toArray(Long[]::new);
        long min = Long.MAX_VALUE;
        for (int i = 0; i < seeds.length; i+=2) {
            for (long j = seeds[i]; j < seeds[i] + seeds[i+1]; j++) {
                long value = getLocation(iter, j);
                if (value < min) {
                    min = value;
                }
            }
        }
        return min;
    }
}
