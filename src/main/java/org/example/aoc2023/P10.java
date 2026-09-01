package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.*;

public class P10 implements AocProblem {
    private record Position(int x, int y, int direction){ }

    int[] xx = new int[]{-1, 0, 1, 0};
    int[] yy = new int[]{0, 1, 0, -1};

    char[][] validDirections = new char[][] {
            new char[]{'S', '|', 'F', '7'},
            new char[]{'S', '-', '7', 'J'},
            new char[]{'S', '|', 'J', 'L'},
            new char[]{'S', '-', 'L', 'F'},
    };

    private boolean isValidCoord(int x, int y, int xl, int yl) {
        return x >= 0 && x < xl && y >= 0 && y < yl;
    }

    private boolean isValidDirection(char currentChr, char[] valids) {
        for (char chr : valids) {
            if (chr == currentChr) return true;
        }
        return false;
    }

    private Position getDirection(char currentChr, Position pos) {
        int x = pos.x;
        int y = pos.y;
        int direction = pos.direction;
        switch(currentChr) {
            case '|': return new Position(x + xx[direction], y, direction);
            case '-': return new Position(x, y + yy[direction], direction);
            case 'J':
                if (direction == 1) return new Position(x - 1, y, 0);
                else return new Position(x, y - 1, 3);
            case 'L':
                if (direction == 2) return new Position(x, y + 1, 1);
                else return new Position(x - 1, y, 0);
            case '7':
                if (direction == 1) return new Position(x + 1, y, 2);
                else return new Position(x, y - 1, 3);
            case 'F':
                if (direction == 0) return new Position(x, y + 1, 1);
                else return new Position(x + 1, y, 2);
        }

        return null;
    }

    // Now accepts the local boolean tracker matrix
    private int visit(char[][] map, Position pos, Stack<Position> path) {
        int depth = 1;
        path.add(pos);

        while (map[pos.x][pos.y] != 'S') {
            Position nextPos = getDirection(map[pos.x][pos.y], pos);
            if (nextPos != null) {
                pos = nextPos;
                depth++;
                path.add(pos);
            }
        }

        return depth;
    }

    private Stack<Position> computePath(ArrayList<String> input) {
        char[][] map = new char[input.size()][];

        int x = 0, y = 0;
        for (int i = 0; i < input.size(); i++) {
            map[i] = input.get(i).toCharArray();
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'S') {
                    x = i;
                    y = j;
                }
            }
        }

        Stack<Position> path = new Stack<>();
        for (int i = 0; i < 4; i++) {
            int newX = x + xx[i];
            int newY = y + yy[i];
            char[] valids = validDirections[i];

            if (isValidCoord(newX, newY, map.length, map[0].length) &&
                    isValidDirection(map[newX][newY], valids)) {
                int depth = visit(map, new Position(newX, newY, i), path);
                if (depth > 1) {
                    break;
                }
            }
        }

        return path;
    }

    private long geometricArea(Stack<Position> path) {
        long area = 0;
        for (int i = 0; i < path.size(); i++) {
            long x1 = path.get(i).x;
            long y1 = path.get(i).y;
            long x2 = path.get((i + 1) % path.size()).x;
            long y2 = path.get((i + 1) % path.size()).y;
            area += (x1 * y2 - x2 * y1);
        }
        return Math.abs(area);
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        return computePath(input).size() / 2;
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        Stack<Position> path = computePath(input);
        return (geometricArea(path) - path.size()) / 2 + 1;
    }
}
