package org.example.domain;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public interface AocProblem {
    long solvePart1(ArrayList<String> input) throws NoSuchAlgorithmException;
    long solvePart2(ArrayList<String> input);
}
