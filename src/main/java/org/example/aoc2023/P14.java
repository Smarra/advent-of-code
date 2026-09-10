package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class P14 implements AocProblem {
    public long slideNorth(char[][] map, int i, int j) {
        map[i][j] = '.';
        while (i > 0 && map[i - 1][j] == '.') {
            i--;
        }
        map[i][j] = 'O';
        return i;
    }

    public long slideEast(char[][] map, int i, int j) {
        map[i][j] = '.';
        while (j < map[0].length - 1 && map[i][j + 1] == '.') {
            j++;
        }
        map[i][j] = 'O';
        return i;
    }

    public long slideSouth(char[][] map, int i, int j) {
        map[i][j] = '.';
        while (i < map.length - 1 && map[i + 1][j] == '.') {
            i++;
        }
        map[i][j] = 'O';
        return i;
    }

    public long slideWest(char[][] map, int i, int j) {
        map[i][j] = '.';
        while (j > 0 && map[i][j - 1] == '.') {
            j--;
        }
        map[i][j] = 'O';
        return i;
    }

    private void printMap(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        char[][] map = input.stream().map(String::toCharArray).toArray(char[][]::new);
        int n = map.length;
        int m = map[0].length;
        long sum = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 'O') {
                    sum += n - slideNorth(map, i, j);
                }
            }
        }

        return sum;
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        char[][] map = input.stream().map(String::toCharArray).toArray(char[][]::new);
        int n = map.length;
        int m = map[0].length;
        ArrayList<Long> sums = new ArrayList<>();

        for (int cycle = 0; cycle <= 1000; cycle++) {
            long sum = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (map[i][j] == 'O') {
                        sum += n - slideNorth(map, i, j);
                    }
                }
            }

            sum = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (map[i][j] == 'O') {
                        sum += m - slideWest(map, i, j);
                    }
                }
            }

            sum = 0;
            for (int i = n - 1; i >= 0; i--) {
                for (int j = 0; j < m; j++) {
                    if (map[i][j] == 'O') {
                        sum += n - slideSouth(map, i, j);
                    }
                }
            }

            sum = 0;
            for (int i = 0; i < n; i++) {
                for (int j = m - 1; j >= 0; j--) {
                    if (map[i][j] == 'O') {
                        sum += m - slideEast(map, i, j);
                    }
                }
            }
            sums.add(sum);
        }

        for (int i = 0; i < sums.size(); i++) {
            System.out.println(i + 1 + ":" + sums.get(i));
        }

        System.out.println(1000000000 % 42);
        System.out.println(958 % 42);

        return 0;
    }
}
