package org.example.aoc2015;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.HashSet;

public class P3 implements AocProblem {

    record MyPair(int x, int y) {};

    public long solvePart1(ArrayList<String> input) {
        HashSet<MyPair> visited = new HashSet<>();
        long sum = 0;
        int cx = 0;
        int cy = 0;
        visited.add(new MyPair(cx, cy));

        for (Character chr : input.get(0).toCharArray()) {
            if (chr == '^') {
                cx--;
            } else if (chr == '>') {
                cy++;
            } else if (chr == 'v') {
                cx++;
            } else {
                cy--;
            }

            visited.add(new MyPair(cx, cy));
        }


        return visited.size();
    }

    public long solvePart2(ArrayList<String> input) {
        HashSet<MyPair> visited = new HashSet<>();
        long sum = 0;
        int scx = 0, rcx = 0;
        int scy = 0, rcy = 0;
        boolean santasTurn = true;
        visited.add(new MyPair(scx, scy));

        for (Character chr : input.get(0).toCharArray()) {
            if (santasTurn) {
                if (chr == '^') {
                    scx--;
                } else if (chr == '>') {
                    scy++;
                } else if (chr == 'v') {
                    scx++;
                } else {
                    scy--;
                }

                visited.add(new MyPair(scx, scy));
            } else {
                if (chr == '^') {
                    rcx--;
                } else if (chr == '>') {
                    rcy++;
                } else if (chr == 'v') {
                    rcx++;
                } else {
                    rcy--;
                }

                visited.add(new MyPair(rcx, rcy));
            }

            santasTurn = !santasTurn;
        }
        return visited.size();
    }
}
