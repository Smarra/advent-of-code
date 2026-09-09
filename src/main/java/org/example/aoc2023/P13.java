package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class P13 implements AocProblem {
    private long solve(List<String> grid, long ignoreLine) {
        int n = grid.size();

        // 1. Check horizontal
        for (int dim = n / 2; dim >= 1; dim--) {
            boolean ok = true;

            for (int i = 1; i <= dim; i++) {
                String l1 = grid.get(dim - i);
                String l2 = grid.get(dim + i - 1);

                if (!l1.equals(l2)) {
                    ok = false;
                    break;
                }
            }

            if (ok && ignoreLine != dim) {
                return dim;
            }

            ok = true;
            for (int i = 1; i <= dim; i++) {
                String l1 = grid.get(n - 1 - dim + i);
                // 8 1 -> 8 - 1 - 1 + 1 = 7
                // 8 2 -> 8 - 1 - 2 + 1 = 6 -> 8 - 1 - 2 + 2 = 7
                String l2 = grid.get(n - dim - i);
                // 8 1 -> 8 - 1 - 1 = 6
                // 8 2 -> 8 - 2 - 1 = 5 -> 8 - 2 - 2 = 4

                if (!l1.equals(l2)) {
                    ok = false;
                    break;
                }
            }

            if (ok && ignoreLine != n - dim) {
                return n - dim;
            }
        }

        return 0;
    }

    public List<String> transposeRight(List<String> input) {
        int n = input.size();
        int m = input.getFirst().length();
        ArrayList<String> result = new ArrayList<>();

        for (int j = 0; j < m; j++) {
            StringBuilder str = new StringBuilder();
            for (int i = n - 1; i >= 0; i--) {
                str.append(input.get(i).charAt(j));
            }

            result.add(str.toString());
        }

        return result;
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        long sum = 0L;
        int i = 0, j = 0;

        for (String line : input) {
            if (line.isEmpty()) {
                ArrayList<String> subList = new ArrayList<>(input.subList(i, j));
                long v1 = solve(subList, -1);
                long v2 = solve(transposeRight(subList), -1);
                sum += v1 > v2 ? v1 * 100 : v2;
                i = j + 1;
                j = j + 1;
            } else {
                j++;
            }
        }

        return sum;
    }

    private char flipChar(char chr) {
        if (chr == '#') return '.';
        else return '#';
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        long sum = 0L;
        int i = 0, j = 0;

        for (String line : input) {
            if (line.isEmpty()) {
                ArrayList<String> subList = new ArrayList<>(input.subList(i, j));
                int n = subList.size();
                int m = subList.getFirst().length();
                long v1 = solve(subList, -1);
                long v2 = solve(transposeRight(subList), -1);
                for (int x = 0; x < n; x++) {
                    for (int y = 0; y < m; y++) {
                        String original = subList.get(x);
                        String newStr = original.substring(0, y) + flipChar(original.charAt(y)) + original.substring(y + 1, m);
                        subList.add(x, newStr);
                        subList.remove(x + 1);
                        long vv1 = solve(subList, v1);
                        long vv2 = solve(transposeRight(subList), v2);

                        subList.remove(x);
                        subList.add(x, original);

                        if (vv1 != 0 && vv1 != v1) {
                            x = n;
                            y = m;
                            sum += vv1 * 100;
                        } else if (vv2 != 0 && vv2 != v2) {
                            x = n;
                            y = m;
                            sum += vv2;
                        }
                    }
                }

                i = j + 1;
                j = j + 1;
            } else {
                j++;
            }
        }

        return sum;
    }
}
