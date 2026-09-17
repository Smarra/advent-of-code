package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.HashMap;

public class P16 implements AocProblem {
    public record Position(int x, int y) {
        public Position add(Position pos) {
            return new Position(pos.x + x, pos.y + y);
        };
    };

    public Position[] dirs = new Position[] {
            new Position(-1, 0),
            new Position(0, 1),
            new Position(1, 0),
            new Position(0, -1)
    };

    public boolean isValidPos(Position pos, int n, int m) {
        return pos.x >= 0 && pos.x < n && pos.y >= 0 && pos.y < m;
    }

    public void rec(char[][] map, HashMap<Position, Integer> visited, Position p, int dir) {
        if (!isValidPos(p, map.length, map[0].length)) {
            return;
        }

        if (visited.containsKey(p) && (visited.get(p) & (1 << dir)) != 0) {
            return;
        }
        visited.put(p, visited.getOrDefault(p, 0) | (1 << dir));
        int newDir;
        switch (map[p.x][p.y]) {
            case '.':
                rec(map, visited, p.add(dirs[dir]), dir);
                break;
            case '/':
                if (dir == 0) newDir = 1;
                else if (dir == 1) newDir = 0;
                else if (dir == 2) newDir = 3;
                else newDir = 2;
                rec(map, visited, p.add(dirs[newDir]), newDir);
                break;
            case '\\':
                if (dir == 0) newDir = 3;
                else if (dir == 3) newDir = 0;
                else if (dir == 1) newDir = 2;
                else newDir = 1;
                rec(map, visited, p.add(dirs[newDir]), newDir);
                break;
            case '|':
                if (dir == 0 || dir == 2) {
                    rec(map, visited, p.add(dirs[dir]), dir);
                } else {
                    rec(map, visited, p.add(dirs[0]), 0);
                    rec(map, visited, p.add(dirs[2]), 2);
                }
                break;
            case '-':
                if (dir == 1 || dir == 3) {
                    rec(map, visited, p.add(dirs[dir]), dir);
                } else {
                    rec(map, visited, p.add(dirs[1]), 1);
                    rec(map, visited, p.add(dirs[3]), 3);
                }
                break;
        }
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        char[][] map = input.stream().map(String::toCharArray).toArray(char[][]::new);
        HashMap<Position, Integer> visited = new HashMap<>();

        rec(map, visited, new Position(0, 0), 1);
        return visited.size();
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        long max = 0;
        char[][] map = input.stream().map(String::toCharArray).toArray(char[][]::new);

        for (int i = 0; i < map[0].length; i++) {
            HashMap<Position, Integer> visited = new HashMap<>();
            rec(map, visited, new Position(0, i), 2);
            max = Math.max(max, visited.size());

            visited.clear();
            rec(map, visited, new Position(map.length - 1, i), 0);
            max = Math.max(max, visited.size());
        }

        for (int i = 0; i < map.length; i++) {
            HashMap<Position, Integer> visited = new HashMap<>();
            rec(map, visited, new Position(i, 0), 1);
            max = Math.max(max, visited.size());

            visited.clear();
            rec(map, visited, new Position(i, map[0].length), 3);
            max = Math.max(max, visited.size());
        }

        return max;
    }
}
