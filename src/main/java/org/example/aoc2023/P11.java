package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;

public class P11 implements AocProblem {

    private record Pos(int x, int y){};

    private long solve(ArrayList<String> input, long additionalDistance) {
        long sum = 0L;
        int n = input.size();
        int m = input.getFirst().length();
        ArrayList<Integer> rows = new ArrayList<>();
        ArrayList<Integer> cols = new ArrayList<>();
        ArrayList<Pos> pos = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String row = input.get(i);
            if (row.equals(".".repeat(m))) {
                rows.add(i);
            }
        }

        for (int j = 0; j < m; j++) {
            boolean contains = true;
            for (int i = 0; i < n; i++) {
                if (input.get(i).charAt(j) == '#') {
                    contains = false;
                    pos.add(new Pos(i, j));
                }
            }

            if (contains) {
                cols.add(j);
            }
        }

        for (int i = 0; i < pos.size(); i++) {
            Pos pi = pos.get(i);
            for (int j = i + 1; j < pos.size(); j++) {
                Pos pj = pos.get(j);
                long XX = Math.max(pi.x, pj.x);
                long xx = Math.min(pi.x, pj.x);
                long YY = Math.max(pi.y, pj.y);
                long yy = Math.min(pi.y, pj.y);
                long dist = (XX - xx) + (YY - yy);

                for (int k = 0; k < rows.size(); k++) {
                    int row = rows.get(k);
                    if (xx <= row && row < XX) {
                        dist += additionalDistance;
                    }
                }

                for (int k = 0; k < cols.size(); k++) {
                    int col = cols.get(k);
                    if (yy <= col && col < YY) {
                        dist += additionalDistance;
                    }
                }

                sum += dist;
            }
        }
        return sum;
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        return solve(input, 1);
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        return solve(input, 999999);
    }
}
