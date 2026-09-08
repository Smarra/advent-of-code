package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.Arrays;

public class P12 implements AocProblem {

    private long back(char[] s, int idx, int gidx, int[] pos, int pidx, Long[][][] memo) {
        long ct = 0;

        if (idx == s.length) {
            if (gidx > 0) {
                if (pidx == pos.length - 1 && pos[pidx] == gidx) {
                    return 1;
                }
            } else {
                if (pidx == pos.length) {
                    return 1;
                }
            }
            return 0;
        }
        
        if (memo[idx][pidx][gidx] != null) {
            return memo[idx][pidx][gidx];
        }

        if (s[idx] == '.' || s[idx] == '?') {
            if (gidx > 0) {
                if (pidx < pos.length && pos[pidx] == gidx) {
                    ct += back(s, idx + 1, 0, pos, pidx + 1, memo);
                }
            } else {
                ct += back(s, idx + 1, 0, pos, pidx, memo);
            }
        }

        if (s[idx] == '#' || s[idx] == '?') {
            if (pidx < pos.length && gidx + 1 <= pos[pidx]) {
                ct += back(s, idx + 1, gidx + 1, pos, pidx, memo);
            }
        }

        memo[idx][pidx][gidx] = ct;
        return ct;
    }

    private long solve(char[] s, int[] pos) {
        int maxGroup = 0;
        for (int p : pos) maxGroup = Math.max(maxGroup, p);

        Long[][][] memo = new Long[s.length][pos.length + 1][maxGroup + 1];
        return back(s, 0, 0, pos, 0, memo);
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        long sum = 0L;

        for (String line : input) {
            char[] s = line.split(" ")[0].toCharArray();
            int[] pos = Arrays
                    .stream(line.split(" ")[1].split(","))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();
            sum += solve(s, pos);
        }

        return sum;
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        long sum = 0L;

        for (String line : input) {
            String aux = line.split(" ")[0].concat("?").repeat(5);
            char[] s = aux.substring(0, aux.length() - 1).toCharArray();
            aux = line.split(" ")[1].concat(",").repeat(5);
            int[] pos = Arrays
                    .stream(aux.substring(0, aux.length() - 1).split(","))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();

            for (char chr : s) {
                System.out.print(chr);
            }
            System.out.print(" ");
            for (int nr : pos) {
                System.out.print(nr + ",");
            }
            System.out.println();

            long res = solve(s, pos);
            System.out.println(res);
            sum += res;
        }

        return sum;
    }
}
