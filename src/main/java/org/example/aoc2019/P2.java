package org.example.aoc2019;

import org.example.domain.AocProblem;

import java.util.*;
import java.util.stream.Collectors;

public class P2 implements AocProblem {

    public long solveOpcodes(Integer[] opcodes) {
        for (int i = 0; i < opcodes.length; i+=4) {
            if (opcodes[i] == 1) {
                opcodes[opcodes[i + 3]] = opcodes[opcodes[i + 2]] + opcodes[opcodes[i + 1]];
            } else if (opcodes[i] == 2) {
                opcodes[opcodes[i + 3]] = opcodes[opcodes[i + 2]] * opcodes[opcodes[i + 1]];
            } else {
                break;
            }
        }

        return opcodes[0];
    }

    public long solvePart1(ArrayList<String> input) {
        Integer[] opcodes = Arrays.stream(input.getFirst().split(",")).map(Integer::parseInt).toList().toArray(new Integer[0]);
        opcodes[1] = 12;
        opcodes[2] = 2;

        return solveOpcodes(opcodes);
    }

    public long solvePart2(ArrayList<String> input) {
        Integer[] opcodes = Arrays.stream(input.getFirst().split(",")).map(Integer::parseInt).toList().toArray(new Integer[0]);

        for (int i = 0; i <= 99; i++) {
           for (int j = 0; j <= 99; j++) {
               Integer[] clonedOpcodes = opcodes.clone();
               clonedOpcodes[1] = i;
               clonedOpcodes[2] = j;

               long result = solveOpcodes(clonedOpcodes);
               if (result == 19690720) {
                   return 100 * i + j;
               }
            }
        }


        return 0;
    }
}