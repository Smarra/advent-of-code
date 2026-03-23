package org.example.aoc2015;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.domain.AocProblem;

import java.util.ArrayList;


public class P4 implements AocProblem {

    public long solvePart1(ArrayList<String> input) {
        String hash = "ckczppom";
        String md5Hex;

        long ct = 0;
        while (true) {
            md5Hex = DigestUtils.md5Hex(hash + ct);
            if (md5Hex.substring(0, 5).equals("00000")) {
                System.out.println(ct);
                return 0;
            }

            ct++;
        }
    }

    public long solvePart2(ArrayList<String> input) {
        String hash = "ckczppom";
        String md5Hex;

        long ct = 0;
        while (true) {
            md5Hex = DigestUtils.md5Hex(hash + ct);
            if (md5Hex.substring(0, 6).equals("000000")) {
                System.out.println(ct);
                return 0;
            }

            ct++;
        }
    }
}
