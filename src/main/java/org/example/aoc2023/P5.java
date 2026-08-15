package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.Arrays;

public class P5 implements AocProblem {
    public record Range(long destStart, long sourceStart, long length){};
    public record RangeRaw(long start, long end){};

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

    // this assumes the currRange is included in the inputRange
    public RangeRaw turnRange(Range inputRange, RangeRaw currRange) {
        return new RangeRaw(
            inputRange.destStart + (currRange.start - inputRange.sourceStart),
            inputRange.destStart + (currRange.end - inputRange.sourceStart)
        );
    }

    public long getLocation(ArrayList<ArrayList<Range>> iter, RangeRaw rawRange) {
        ArrayList<RangeRaw> stillLeftToVerify = new ArrayList<>();
        stillLeftToVerify.add(rawRange);

        ArrayList<RangeRaw> nextRound = new ArrayList<>();

        for (ArrayList<Range> inputRanges : iter) {
            for (Range inputRange : inputRanges) {
                RangeRaw comp = new RangeRaw(inputRange.sourceStart, inputRange.sourceStart + inputRange.length);

                for (int i = 0; i < stillLeftToVerify.size(); i++) {
                    RangeRaw currRange = stillLeftToVerify.get(i);

                    // 0. outside
                    if (currRange.start >= comp.end || currRange.end <= comp.start) {
                        continue;
                    }

                    // 1. completely included
                    if (currRange.start >= comp.start && currRange.end <= comp.end) {
                        nextRound.add(turnRange(inputRange, currRange));
                        stillLeftToVerify.remove(i);
                        i--;
                        continue;
                    }

                    // 3. right
                    if (currRange.end > comp.end) {
                        long leftRange = Long.max(currRange.start, comp.start);
                        RangeRaw centre = new RangeRaw(leftRange, comp.end);
                        nextRound.add(turnRange(inputRange, centre));

                        RangeRaw right = new RangeRaw(comp.end, currRange.end);
                        stillLeftToVerify.add(right);
                    }

                    // 4. or left
                    if (currRange.start < comp.start) {
                        long rightRange = Long.min(currRange.end, comp.end);
                        RangeRaw centre = new RangeRaw(comp.start, rightRange);

                        RangeRaw newRange = turnRange(inputRange, centre);
                        if (!nextRound.contains(newRange)) nextRound.add(newRange);

                        RangeRaw left = new RangeRaw(currRange.start, comp.start);
                        stillLeftToVerify.add(left);
                    }

                    stillLeftToVerify.remove(i);
                    i--;
                }
            }

            nextRound.addAll(stillLeftToVerify);
            stillLeftToVerify.clear();
            stillLeftToVerify.addAll(nextRound);
            nextRound.clear();
        }

        long min = Long.MAX_VALUE;
        for (RangeRaw range : stillLeftToVerify) {
            if (min > range.start) {
                min = range.start;
            }
        }
        return min;
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
            RangeRaw range = new RangeRaw(seeds[i], seeds[i] + seeds[i+1]);
            long value = getLocation(iter, range);
            if (value < min) {
                min = value;
            }
        }
        return min;
    }
}
