package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.Arrays;

public class P12 implements AocProblem {

    private int check(char[] s, int[] pos) {
        int idx = 0;
        int ct = 0;
        for (char c : s) {
            if (c == '?') {
                return 0;
            }

            if (c == '.') {
                if (ct > 0) {
                    if (pos[idx] != ct) {
                        return -1;
                    }

                    idx++;
                    ct = 0;
                }
                continue;
            }

            ct++;

            if (idx >= pos.length) {
                return -1;
            }
        }

        if (ct > 0) {
            if (pos[idx] != ct) {
                return -1;
            }

            idx++;
        }

        if (idx != pos.length) {
            return -1;
        }

        return 1;
    }

    private long back(char[] s, int[] pos, int idx, long ct) {
        int status = check(s, pos);
        if (status == -1) {
            return ct;
        } else if (status == 1) {
            return ct + 1;
        }

        if (idx == s.length) {
            return ct;
        }

        if (s[idx] == '?') {
            s[idx] = '.';
            ct = back(s, pos, idx + 1, ct);

            s[idx] = '#';
            ct = back(s, pos, idx + 1, ct);
            s[idx] = '?';
        } else {
            ct = back(s, pos, idx + 1, ct);
        }

        return ct;
    }

    private long solve(char[] s, int[] pos) {
        return back(s, pos, 0, 0);
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
