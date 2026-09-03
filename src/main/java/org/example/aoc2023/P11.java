package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;

public class P11 implements AocProblem {

    private record Pos(int x, int y){};

    @Override
    public long solvePart1(ArrayList<String> input) {
        long sum = 0L;
        int n = input.size();
        int m = input.getFirst().length();
        ArrayList<Pos> pos = new ArrayList<>();


        // rows
        for (int i = 0; i < n; i++) {
            String row = input.get(i);
            if (row.equals(".".repeat(m))) {
                input.add(i, row);
                i++;
                n++;
            }
        }

        // columns
        for (int j = 0; j < m; j++) {
            boolean contains = true;
            for (int i = 0; i < n; i++) {
                if (input.get(i).charAt(j) == '#') {
                    contains = false;
                    pos.add(new Pos(i, j));
                }
            }

            if (contains) {
                System.out.println(j);
                for (int i = 0; i < n; i++) {
                    String newStr = input.get(i).substring(0, j) + "." + input.get(i).substring(j, m);
                    input.remove(i);
                    input.add(i, newStr);
                }

                j++;
                m++;
            }
        }

        for (int i = 0; i < pos.size(); i++) {
            Pos pi = pos.get(i);
            for (int j = i + 1; j < pos.size(); j++) {
                Pos pj = pos.get(j);
                long distx = Math.abs(pi.x - pj.x);
                long disty = Math.abs(pi.y - pj.y);
                long dist = distx + disty;
                sum += dist;
            }
        }

        return sum;
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        long sum = 0L;
        int n = input.size();
        int m = input.getFirst().length();
        ArrayList<Integer> rows = new ArrayList<>();
        ArrayList<Integer> cols = new ArrayList<>();
        ArrayList<Pos> pos = new ArrayList<>();

        // rows
        for (int i = 0; i < n; i++) {
            String row = input.get(i);
            if (row.equals(".".repeat(m))) {
                rows.add(i);
            }
        }

        // columns
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
                        dist += 999999;
                    }
                }

                for (int k = 0; k < cols.size(); k++) {
                    int col = cols.get(k);
                    if (yy <= col && col < YY) {
                        dist += 999999;
                    }
                }

                sum += dist;
            }
        }
        return sum;
    }
}
