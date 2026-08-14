package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.HashMap;

public class P3 implements AocProblem {

    public record Pair(int x, int y, long nr){};

    public ArrayList<Pair> getSymbols(ArrayList<String> input, int x, int y, int length) {
        ArrayList<Pair> symbols = new ArrayList<>();

        if (x > 0) {
            char[] line = input.get(x - 1).toCharArray();
            for (int i = y - 1; i <= y + length; i++) {
                if (i >= 0 && i < line.length) {
                    if (line[i] != '.' && (line[i] < '0' || line[i] > '9')) {
                        symbols.add(new Pair(x - 1, i, 0));
                    }
                }
            }
        }

        if (x + 1 < input.size()) {
            char[] line = input.get(x + 1).toCharArray();
            for (int i = y - 1; i <= y + length; i++) {
                if (i >= 0 && i < line.length) {
                    if (line[i] != '.' && (line[i] < '0' || line[i] > '9')) {
                        symbols.add(new Pair(x + 1, i, 0));
                    }
                }
            }
        }

        if (y > 0) {
            char chr = input.get(x).charAt(y - 1);
            if (chr != '.' && (chr < '0' || chr > '9')) {
                symbols.add(new Pair(x, y - 1, 0));
            }
        }

        if (y + length < input.get(0).length()) {
            char chr = input.get(x).charAt(y + length);
            if (chr != '.' && (chr < '0' || chr > '9')) {
                symbols.add(new Pair(x, y + length, 0));
            }
        }

        return symbols;
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        long sum = 0;

        for (int i = 0; i < input.size(); i++) {
            char[] line = input.get(i).toCharArray();
            for (int j = 0; j < line.length; j++) {
                if (line[j] >= '0' && line[j] <= '9') {
                    long nr = 0;
                    int length = 0;
                    while (j < line.length && line[j] >= '0' && line[j] <= '9') {
                        nr = nr * 10 + (line[j] - '0');
                        length++;
                        j++;
                    }

                    if (!getSymbols(input, i, j - length, length).isEmpty()) {
                        sum += nr;
                    }
                }
            }
        }
        return sum;
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        long sum = 0;
        HashMap<Pair, ArrayList<Pair>> map = new HashMap<>();

        for (int i = 0; i < input.size(); i++) {
            char[] line = input.get(i).toCharArray();
            for (int j = 0; j < line.length; j++) {
                if (line[j] >= '0' && line[j] <= '9') {
                    long nr = 0;
                    int length = 0;
                    while (j < line.length && line[j] >= '0' && line[j] <= '9') {
                        nr = nr * 10 + (long)(line[j] - '0');
                        length++;
                        j++;
                    }

                    ArrayList<Pair> symbols = getSymbols(input, i, j - length, length);
                    for (Pair symbol : symbols) {
                        if (map.containsKey(symbol)) {
                            map.get(symbol).add(new Pair(i, j - length, nr));
                        } else {
                            ArrayList<Pair> list = new ArrayList<>();
                            list.add(new Pair(i, j - length, nr));
                            map.put(symbol, list);
                        }
                    }
                }
            }
        }

        for (Pair symbol : map.keySet()) {
            if (map.get(symbol).size() == 2) {
                sum += map.get(symbol).get(0).nr * map.get(symbol).get(1).nr;
            }
        }

        return sum;
    }
}
