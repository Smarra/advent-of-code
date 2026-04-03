package org.example.aoc2019;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

record Pair(int x, int y) {
    public Pair add(Pair p) {
        return new Pair(this.x + p.x, this.y + p.y);
    }
};

public class P3 implements AocProblem {

    public Pair move(Character direction, Pair cur) {
        if (direction == 'U') {
            return cur.add(new Pair(-1, 0));
        } else if (direction == 'R') {
            return cur.add(new Pair(0, 1));
        } else if (direction == 'D') {
            return cur.add(new Pair(1, 0));
        } else {
            return cur.add(new Pair(0, -1));
        }
    }

    public long solvePart1(ArrayList<String> input) {
        String[] w1 = input.get(0).split(",");
        String[] w2 = input.get(1).split(",");
        HashSet<Pair> ws1 = new HashSet<>();
        HashSet<Pair> ws2 = new HashSet<>();
        HashSet<Pair> intersections = new HashSet<>();

        Pair cur = new Pair(0, 0);
        for (String op : w1) {
            Character direction = op.charAt(0);
            int distance = Integer.parseInt(op.substring(1));

            for (int i = 0; i < distance; i++) {
                cur = move(direction, cur);
                ws1.add(cur);
            }
        }

        cur = new Pair(0, 0);
        for (String op : w2) {
            Character direction = op.charAt(0);
            int distance = Integer.parseInt(op.substring(1));

            for (int i = 0; i < distance; i++) {
                cur = move(direction, cur);
                ws2.add(cur);

                if (ws1.contains(cur)) {
                    intersections.add(cur);
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for (Pair p : intersections) {
            int manh = Math.abs(p.x()) + Math.abs(p.y());

            if (manh < min) {
                min = manh;
            }
        }

        return min;
    }

    public long solvePart2(ArrayList<String> input) {
        String[] w1 = input.get(0).split(",");
        String[] w2 = input.get(1).split(",");
        HashMap<Pair, Integer> ws1 = new HashMap<>();
        HashMap<Pair, Integer> ws2 = new HashMap<>();
        HashMap<Pair, Integer> intersections = new HashMap<>();

        Pair cur = new Pair(0, 0);
        int curDistance = 0;
        for (String op : w1) {
            Character direction = op.charAt(0);
            int distance = Integer.parseInt(op.substring(1));

            for (int i = 0; i < distance; i++) {
                curDistance++;
                cur = move(direction, cur);
                ws1.put(cur, curDistance);
            }
        }

        cur = new Pair(0, 0);
        curDistance = 0;
        for (String op : w2) {
            Character direction = op.charAt(0);
            int distance = Integer.parseInt(op.substring(1));

            for (int i = 0; i < distance; i++) {
                curDistance++;
                cur = move(direction, cur);
                ws2.put(cur, curDistance);

                if (ws1.containsKey(cur)) {
                    intersections.put(cur, ws1.get(cur) + ws2.get(cur));
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for (Pair p : intersections.keySet()) {
            int dist = intersections.get(p);

            if (dist < min) {
                min = dist;
            }
        }

        return min;
    }
}