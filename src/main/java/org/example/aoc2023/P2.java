package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.HashMap;

public class P2 implements AocProblem {

    public boolean eval(HashMap<String, Integer> map) {
        if (map.containsKey("red") && map.get("red") > 12) {
            return false;
        }

        if (map.containsKey("green") && map.get("green") > 13) {
            return false;
        }

        if (map.containsKey("blue") && map.get("blue") > 14) {
            return false;
        }

        return true;
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        long sum = 0;

        for (String lineRaw : input) {
            Integer id = Integer.parseInt(lineRaw.split(":")[0].split(" ")[1]);
            String[] gamesRaw = lineRaw.split(":")[1].trim().split(";");
            boolean breaked = false;

            for (String gameRaw: gamesRaw) {
                String[] games = gameRaw.split(",");

                for (String game: games) {
                    HashMap<String, Integer> map = new HashMap<>();
                    String[] parts = game.trim().split(" ");
                    if (map.containsKey(parts[1])) {
                        Integer curr = map.get(parts[1]);
                        map.put(parts[1], curr + Integer.parseInt(parts[0]));
                    } else {
                        map.put(parts[1], Integer.parseInt(parts[0]));
                    }

                    if (!eval(map)) {
                        System.out.println(game);
                        breaked = true;
                        break;
                    }
                }

                if (breaked == true) {
                    break;
                }
            }

            if (!breaked) {
                sum += id;
            }
        }

        return sum;
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        long sum = 0;

        for (String lineRaw : input) {
            Integer id = Integer.parseInt(lineRaw.split(":")[0].split(" ")[1]);
            String[] gamesRaw = lineRaw.split(":")[1].trim().split(";");
            HashMap<String, Integer> map = new HashMap<>();

            for (String gameRaw: gamesRaw) {
                String[] games = gameRaw.split(",");

                for (String game: games) {
                    String[] parts = game.trim().split(" ");
                    Integer val = Integer.parseInt(parts[0]);

                    if (map.containsKey(parts[1])) {
                        Integer curr = map.get(parts[1]);
                        if (val > curr) {
                            map.put(parts[1], val);
                        }
                    } else {
                        map.put(parts[1], Integer.parseInt(parts[0]));
                    }
                }
            }

            long mult = 1;
            for (String key : map.keySet()) {
                System.out.print("Key: " + key + " value: " + map.get(key) + ";");
                mult *= map.get(key);
            }
            System.out.println();

            sum += mult;
        }

        return sum;
    }
}
