package org.example.aoc2023;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class P18 implements AocProblem {

    public record Move(Character dir, long steps) {};
    public record Pos(int x, int y){
        public boolean isValid(int n, int m) {
            return x >= 0 && x <= n && y >= 0 && y <= m;
        }

        public Pos add(Pos p) {
            return new Pos(x + p.x, y + p.y);
        }
    };

    public Pos[] xy = new Pos[] {
            new Pos(-1, 0),
            new Pos(0, 1),
            new Pos(1, 0),
            new Pos(0, -1)
    };

    public void fill(int[][] map, Pos p, int n, int m) {
        Queue<Pos> queue = new LinkedList<>();
        queue.add(p);

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();

            if (!p.isValid(n, m)) continue;

            if (map[pos.x][pos.y] == 0) {
                map[pos.x][pos.y] = 1;
                for (int i = 0; i < 4; i++) {
                    queue.add(pos.add(xy[i]));
                }
            }
        }
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        ArrayList<Move> moves = new ArrayList<>();
        for (String line : input) {
            String[] parts = line.split(" ");
            moves.add(new Move(parts[0].charAt(0), Integer.parseInt(parts[1])));
        }

        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        int startX = 0, startY = 0;
        for (Move move : moves) {
            if (move.dir == 'U' || move.dir == 'D') {
                startX += move.dir == 'U' ? (-1) * move.steps : move.steps;
                minX = Math.min(minX, startX);
                maxX = Math.max(maxX, startX);
            }
            if (move.dir == 'L' || move.dir == 'R') {
                startY += move.dir == 'L' ? (-1) * move.steps : move.steps;
                minY = Math.min(minY, startY);
                maxY = Math.max(maxY, startY);
            }
        }

        int n = minX * (-1) + maxX;
        int m = minY * (-1) + maxY;
        startX = minX * (-1);
        startY = minY * (-1);


        int[][] map = new int[n + 1][m + 1];
        map[startX][startY] = 1;
        for (Move move : moves) {
            long steps = move.steps;
            if (move.dir == 'U') {
                while (steps != 0) {
                    startX--;
                    map[startX][startY] = 1;
                    steps--;
                }
            }
            if (move.dir == 'D') {
                while (steps != 0) {
                    startX++;
                    map[startX][startY] = 1;
                    steps--;
                }
            }
            if (move.dir == 'L') {
                while (steps != 0) {
                    startY--;
                    map[startX][startY] = 1;
                    steps--;
                }
            }
            if (move.dir == 'R') {
                while (steps != 0) {
                    startY++;
                    map[startX][startY] = 1;
                    steps--;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 1 && map[i][j + 1] == 1 && map[i + 1][j] == 1 && map[i+1][j+1] == 0) {
                    fill(map, new Pos(i + 1, j + 1), n, m);
                    i = n; j = m;
                }
            }
        }

        int ct = 0;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                if (map[i][j] == 1) ct++;
            }
        }

        return ct;
    }


    char[] dirs = new char[] {'R', 'D', 'L', 'U'};
    public long fromHex(String str) {
        long v = 0;
        long exp = 1;
        char[] chrs = str.toCharArray();
        for (int i = chrs.length - 1; i >= 0; i--) {
            if (chrs[i] >= 'a') {
                v += exp * (chrs[i] - 'a' + 10);
            } else {
                v += exp * (chrs[i] - '0');
            }

            exp *= 16;
        }

        return v;
    }


    @Override
    public long solvePart2(ArrayList<String> input) {
        ArrayList<Move> moves = new ArrayList<>();
        for (String line : input) {
            String[] parts = line.split(" ");

            long steps = fromHex(parts[2].substring(2, parts[2].length() - 2));
            Character dir = parts[2].substring(parts[2].length() - 2, parts[2].length() - 1).charAt(0);
            moves.add(new Move(dirs[dir - '0'], steps));
        }

        long x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        long area = 0, perimiter = 0;
        for (Move m : moves) {
            x1 = x2;
            y1 = y2;

            if (m.dir == 'U') x2 -= m.steps;
            if (m.dir == 'D') x2 += m.steps;
            if (m.dir == 'R') y2 += m.steps;
            if (m.dir == 'L') y2 -= m.steps;

            area += x1 * y2 - x2 * y1;

            perimiter += m.steps;
        }

        area = Math.abs(area) / 2;
        return area + (perimiter / 2) + 1;
    }
}
