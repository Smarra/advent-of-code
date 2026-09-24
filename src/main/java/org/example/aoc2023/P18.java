package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.*;

public class P18 implements AocProblem {

    public record Move(Character dir, long steps) {};
    public record Pos(int x, int y){
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

    public HashMap<Character, Integer> dirToPos = new HashMap<>() {{
        put('U', 0);
        put('R', 1);
        put('D', 2);
        put('L', 3);
    }};

    public void fill(HashSet<Pos> visited, Pos start) {
        Queue<Pos> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Pos p = queue.poll();

            for (int i = 0; i < 4; i++) {
                Pos newP = p.add(xy[i]);
                if (!visited.contains(newP)) {
                    visited.add(newP);
                    queue.add(newP);
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

        HashSet<Pos> visited = new HashSet<>();
        Pos curr = new Pos(0, 0);
        for (Move move : moves) {
            long steps = move.steps;
            while (steps != 0) {
                curr = curr.add(xy[dirToPos.get(move.dir)]);
                visited.add(curr);
                steps--;
            }
        }

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;

        for (Pos p : visited) {
            if (p.x < minX) {
                minX = p.x;
                minY = p.y;
            } else if (p.x == minX && p.y < minY) {
                minY = p.y;
            }
        }
        fill(visited, new Pos(minX + 1, minY + 1));

        return visited.size();
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

        long x1, y1, x2 = 0, y2 = 0;
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
