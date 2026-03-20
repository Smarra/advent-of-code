package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.example.*;
import org.example.aoc2015.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        ArrayList<String> input = new ArrayList<>();
        File inputFile = new File("src/main/resources/input.txt");

        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNext()) {
                input.add(scanner.next());
            }

        } catch(FileNotFoundException e) {
            System.out.println("Exception occured: " + e.getMessage());
            System.exit(1);
        }

        P2 problem = new P2();
        long result1 = problem.solvePart1(input);
        System.out.println("Part1: " + result1);

        long result2 = problem.solvePart2(input);
        System.out.println("Part2: " + result2);
    }
}