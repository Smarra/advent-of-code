package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.HashSet;

public class P4 implements AocProblem {
    @Override
    public long solvePart1(ArrayList<String> input) {
        long sum = 0;

        for (String line : input) {
            String[] numbersRaw = line.split(":")[1].split("\\|");
            String[] winningNrs = numbersRaw[0].trim().split(" ");
            String[] myNrs = numbersRaw[1].trim().split(" ");


            HashSet<Integer> winning = new HashSet<>();
            long curr = 0;
            for (String nr : winningNrs) {
                if (!nr.isEmpty()) {
                    winning.add(Integer.parseInt(nr));
                }
            }

            for (String nr : myNrs) {
                if (!nr.isEmpty()) {
                    Integer currNr = Integer.parseInt(nr);
                    if (winning.contains(currNr)) {
                        if (curr == 0) {
                            curr = 1;
                        } else {
                            curr *= 2;
                        }
                    }
                }
            }

            sum += curr;
        }

        return sum;
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        ArrayList<Long> sums = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            sums.add(1L);
        }

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            String[] numbersRaw = line.split(":")[1].split("\\|");
            String[] winningNrs = numbersRaw[0].trim().split(" ");
            String[] myNrs = numbersRaw[1].trim().split(" ");

            HashSet<Integer> winning = new HashSet<>();
            int ct = 0;
            for (String nr : winningNrs) {
                if (!nr.isEmpty()) {
                    winning.add(Integer.parseInt(nr));
                }
            }

            for (String nr : myNrs) {
                if (!nr.isEmpty() && winning.contains(Integer.parseInt(nr))) {
                    ct++;
                }
            }

            for (int j = 1; j <= ct && i + j < input.size(); j++) {
                sums.set(i + j, sums.get(i + j) + sums.get(i));
            }
        }

        long finalSum = 0;
        for (Long sum : sums) {
            finalSum += sum;
        }

        return finalSum;
    }
}
