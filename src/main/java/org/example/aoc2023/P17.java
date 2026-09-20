package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class P17 implements AocProblem {
    public record Axis(int x, int y){
        public Axis add(Axis newPos) {
            return new Axis(x + newPos.x, y + newPos.y);
        }
    };
    public Axis[] next = new Axis[] {
            new Axis(-1, 0),
            new Axis(0, 1),
            new Axis(1, 0),
            new Axis(0, -1)
    };

    public record Position(Axis xy, int direction, int steps){};

    public record PositionAndDistance(Position p, int d) implements Comparable<PositionAndDistance> {
        @Override
        public int compareTo(PositionAndDistance o) {
            return this.d - o.d;
        }
    };

    boolean isPositionValid(Axis p, int[][] map) {
        int n = map.length;
        int m = map[0].length;
        return p.x >= 0 && p.x < n && p.y >= 0 && p.y < m;
    }

    ArrayList<Position> getValidNextPositions(Position p, int[][] map, int part) {
        ArrayList<Position> rp = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (part == 1 && (Math.abs(p.direction - i) == 2 || (i == p.direction && p.steps == 3))) {
                continue;
            }

            if (part == 2 && (Math.abs(p.direction - i) == 2 || (i == p.direction && p.steps == 10) || (i != p.direction && p.steps < 4))) {
                continue;
            }

            Axis nextP = p.xy.add(next[i]);
            if (isPositionValid(nextP, map)) {
                if (i == p.direction) {
                    rp.add(new Position(nextP, i, p.steps + 1));
                } else {
                    rp.add(new Position(nextP, i, 1));
                }
            }
        }

        return rp;
    }

    public long solve(ArrayList<String> input, int part) {
        int[][] map = new int[input.size()][];
        HashMap<Position, Integer> dist = new HashMap<>();
        PriorityQueue<PositionAndDistance> queue = new PriorityQueue<>();
        for (int i = 0; i < input.size(); i++) {
            char[] chrs = input.get(i).toCharArray();
            map[i] = new int[chrs.length];
            for (int j = 0; j < chrs.length; j++) {
                map[i][j] = chrs[j] - '0';
            }
        }
        dist.put(new Position(new Axis(0 ,0), 0, 0), 0);
        dist.put(new Position(new Axis(0 ,1), 1, 1), map[0][1]);
        dist.put(new Position(new Axis(1 ,0), 2, 1), map[1][0]);
        queue.add(new PositionAndDistance(new Position(new Axis(0 ,1), 1, 1), map[0][1]));
        queue.add(new PositionAndDistance(new Position(new Axis(1 ,0), 2, 1), map[1][0]));

        while (!queue.isEmpty()) {
            PositionAndDistance currPos = queue.poll();
            if (currPos.p.xy.x == map.length - 1 && currPos.p.xy.y == map[0].length - 1) {
                if (part == 1 || currPos.p.steps >= 4) {
                    return dist.get(currPos.p);
                }
            }

            ArrayList<Position> validNextPositions = getValidNextPositions(currPos.p, map, part);
            for (Position np : validNextPositions) {
                int newDist = currPos.d + map[np.xy.x][np.xy.y];
                if (newDist < dist.getOrDefault(np, Integer.MAX_VALUE)) {
                    dist.put(np, newDist);
                    queue.add(new PositionAndDistance(np, newDist));
                }
            }
        }

        return 0;
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        return solve(input, 1);
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        return solve(input, 2);
    }
}
