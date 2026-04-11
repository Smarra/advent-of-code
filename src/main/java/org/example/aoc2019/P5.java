package org.example.aoc2019;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.Arrays;

public class P5 implements AocProblem {

    public long solveOpcodes(Integer[] opcodes, int inputCode) {
        for (int i = 0; i < opcodes.length;) {
            int parseMode = opcodes[i];
            int mode = parseMode % 100;
            parseMode = parseMode / 100;

            if (mode == 1) {
                int p1Pos = parseMode % 10 == 0 ? opcodes[i + 1] : i + 1;
                parseMode = parseMode / 10;
                int p2Pos = parseMode % 10 == 0 ? opcodes[i + 2] : i + 2;
                int p3Pos = opcodes[i + 3];

                opcodes[p3Pos] = opcodes[p2Pos] + opcodes[p1Pos];
                i += 4;
            } else if (mode == 2) {
                int p1Pos = parseMode % 10 == 0 ? opcodes[i + 1] : i + 1;
                parseMode = parseMode / 10;
                int p2Pos = parseMode % 10 == 0 ? opcodes[i + 2] : i + 2;
                int p3Pos = opcodes[i + 3];

                opcodes[p3Pos] = opcodes[p2Pos] * opcodes[p1Pos];
                i += 4;
            } else if (mode == 3) {
                opcodes[opcodes[i + 1]] = inputCode;
                i += 2;
            } else if (mode == 4) {
                System.out.println("Output: " + opcodes[opcodes[i + 1]]);
                i += 2;
            } else if (mode == 5) {
                int p1Pos = parseMode % 10 == 0 ? opcodes[i + 1] : i + 1;
                int p2Pos = parseMode / 10 % 10 == 0 ? opcodes[i + 2] : i + 2;

                if (opcodes[p1Pos] != 0) {
                    i = opcodes[p2Pos];
                } else {
                    i += 3;
                }
            } else if (mode == 6) {
                int p1Pos = parseMode % 10 == 0 ? opcodes[i + 1] : i + 1;
                int p2Pos = parseMode / 10 % 10 == 0 ? opcodes[i + 2] : i + 2;

                if (opcodes[p1Pos] == 0) {
                    i = opcodes[p2Pos];
                } else {
                    i += 3;
                }
            } else if (mode == 7) {
                int p1Pos = parseMode % 10 == 0 ? opcodes[i + 1] : i + 1;
                int p2Pos = parseMode / 10 % 10 == 0 ? opcodes[i + 2] : i + 2;
                int p3Pos = opcodes[i + 3];

                if (opcodes[p1Pos] < opcodes[p2Pos]) {
                    opcodes[p3Pos] = 1;
                } else {
                    opcodes[p3Pos] = 0;
                }
                i += 4;
            } else if (mode == 8) {
                int p1Pos = parseMode % 10 == 0 ? opcodes[i + 1] : i + 1;
                int p2Pos = parseMode / 10 % 10 == 0 ? opcodes[i + 2] : i + 2;
                int p3Pos = opcodes[i + 3];

                if (opcodes[p1Pos].equals(opcodes[p2Pos])) {
                    opcodes[p3Pos] = 1;
                } else {
                    opcodes[p3Pos] = 0;
                }
                i += 4;
            }
            else {
                break;
            }
        }

        return 0;
    }

    public long solvePart1(ArrayList<String> input) {
        Integer[] opcodes = Arrays.stream(input.getFirst().split(",")).map(Integer::parseInt).toList().toArray(new Integer[0]);
        solveOpcodes(opcodes, 1);
        return 0;
    }

    public long solvePart2(ArrayList<String> input) {
        Integer[] opcodes = Arrays.stream(input.getFirst().split(",")).map(Integer::parseInt).toList().toArray(new Integer[0]);
        solveOpcodes(opcodes, 5);
        return 0;
    }
}
