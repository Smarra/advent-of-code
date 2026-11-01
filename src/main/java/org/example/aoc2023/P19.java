package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.*;

public class P19 implements AocProblem {

    public static long sum = 0;
    public record Condition(Character var, Boolean bigger, Boolean equal, Integer value){
        public Condition reverse() {
            return new Condition(var, !bigger, !equal, value);
        }

        @Override
        public String toString() {
            String sign;
            if (bigger && equal) sign = ">=";
            else if (bigger && !equal) sign = ">";
            else if (!bigger && equal) sign = "<=";
            else sign = "<";

            return var + sign + value;
        }
    };
    public record WorkflowEntry(Condition cond, String workflowNameDestination){};
    public record Workflow(String name, ArrayList<WorkflowEntry> entries){};
    public record Bound(Integer lower, Integer upper){};

    private void getWorkflows(ArrayList<String> input, HashMap<String, Workflow> ws, ArrayList<HashMap<Character, Integer>> ratings) {
        int ct = 0;
        for (; ct < input.size(); ct++) {
            String line = input.get(ct);
            if (line.isEmpty()) {
                break;
            }

            String[] parts = line.split("\\{|\\}");
            String[] condsRaw = parts[1].split(",");

            ArrayList<WorkflowEntry> entries = new ArrayList<>();
            for (String condRaw : condsRaw) {
                if (condRaw.contains(":")) {
                    String[] condSplit = condRaw.split(":");
                    Integer value = Integer.parseInt(condSplit[0].substring(2));
                    Boolean bigger = condSplit[0].charAt(1) == '>';
                    Condition cond = new Condition(condSplit[0].charAt(0),  bigger, false, value);

                    entries.add(new WorkflowEntry(cond, condSplit[1]));
                } else {
                    entries.add(new WorkflowEntry(null, condRaw));
                }

            }

            ws.put(parts[0], new Workflow(parts[0], entries));
        }

        ct++;
        for (; ct < input.size(); ct++) {
            HashMap<Character, Integer> rats = new HashMap<>();
            String line = input.get(ct);
            String[] parts = line.split("\\{|\\}")[1].split(",");
            for (String part : parts) {
                String[] rating = part.split("=");
                rats.put(rating[0].charAt(0), Integer.parseInt(rating[1]));
            }

            ratings.add(rats);
        }
    }

    private boolean evalConditionWithRatings(Condition cond, HashMap<Character, Integer> ratings) {
        Integer actualValue = ratings.get(cond.var);
        return (cond.bigger && actualValue > cond.value) || (!cond.bigger && actualValue < cond.value);
    }

    private boolean solve(HashMap<String, Workflow> ws, HashMap<Character, Integer> ratings, String name) {
        Workflow w = ws.get(name);

        for (WorkflowEntry entry : w.entries) {
            if (entry.cond == null || evalConditionWithRatings(entry.cond, ratings)) {
                if (entry.workflowNameDestination.equals("A")) return true;
                if (entry.workflowNameDestination.equals("R")) return false;
                else return solve(ws, ratings, entry.workflowNameDestination);
            }
        }

        return false;
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        long sum = 0;
        HashMap<String, Workflow> ws = new HashMap<>();
        ArrayList<HashMap<Character, Integer>> ratingsList = new ArrayList<>();
        getWorkflows(input, ws, ratingsList);
        for (HashMap<Character, Integer> ratings : ratingsList) {
            if (solve(ws, ratings, "in")) {
                for (Integer value : ratings.values()) {
                    sum += value;
                }
            }
        }

        return sum;
    }

    private long compute(ArrayList<Condition> conditions) {
        HashMap<Character, Bound> bounds = new HashMap<>();
        bounds.put('x', new Bound(1, 4000));
        bounds.put('m', new Bound(1, 4000));
        bounds.put('a', new Bound(1, 4000));
        bounds.put('s', new Bound(1, 4000));

        for (Condition cond : conditions) {
            Character var = cond.var;
            Bound bound = bounds.get(var);

            if (cond.bigger && cond.equal && cond.value > bound.lower) bounds.put(var, new Bound(cond.value, bound.upper));
            if (cond.bigger && !cond.equal && cond.value > bound.lower) bounds.put(var, new Bound(cond.value + 1, bound.upper));
            if (!cond.bigger && cond.equal && cond.value < bound.upper) bounds.put(var, new Bound(bound.lower, cond.value));
            if (!cond.bigger && !cond.equal && cond.value < bound.upper) bounds.put(var, new Bound(bound.lower, cond.value - 1));
        }

        long p = 1;
        for (Bound bound : bounds.values()) {
            p *= (bound.upper - bound.lower + 1);
        }

        return p;
    }

    public void solve2(HashMap<String, Workflow> ws, String name, ArrayList<Condition> conditions) {
        if (name.equals("A")) {
            sum += compute(conditions);
            return;
        } else if (name.equals("R")) {
            return;
        }
        Workflow w = ws.get(name);

        for (WorkflowEntry entry : w.entries) {
            if (entry.cond == null) {
                if (entry.workflowNameDestination.equals("A")) {
                    sum += compute(conditions);
                } else if (entry.workflowNameDestination.equals("R")) {
                } else {
                    solve2(ws, entry.workflowNameDestination, conditions);
                }

                for (int i = 0; i < w.entries.size() - 1; i++) {
                    conditions.removeLast();
                }
                return;
            }


            conditions.addLast(entry.cond);
            solve2(ws, entry.workflowNameDestination, conditions);
            conditions.removeLast();
            conditions.addLast(entry.cond.reverse());
        }
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        HashMap<String, Workflow> ws = new HashMap<>();
        ArrayList<HashMap<Character, Integer>> ratingsList = new ArrayList<>();
        getWorkflows(input, ws, ratingsList);

        solve2(ws, "in", new ArrayList<Condition>());
        return sum;
    }
}
