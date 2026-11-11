package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class P20 implements AocProblem {

    public abstract class Module {
        public String name;
        public ArrayList<String> destinations;

        public Module(String name, ArrayList<String> destinations) {
            this.name = name;
            this.destinations = destinations;
        }

        public void addInput(String input, Boolean result) {}

        public Boolean processSignal(Boolean highSignal) {
            return null;
        }
    }

    public class FlipFlop extends Module {
        private Boolean on = false;

        public FlipFlop(String name, ArrayList<String> destinations) {
            super(name, destinations);
        }

        @Override
        public Boolean processSignal(Boolean highSignal) {
            if (highSignal) {
                return null;
            }

            on = !on;
            return on;
        }
    }

    public class Conjunction extends Module {
        HashMap<String, Boolean> inputs = new HashMap<>();

        public Conjunction(String name, ArrayList<String> destinations) {
            super(name, destinations);
        }

        @Override
        public void addInput(String input, Boolean result) {
            inputs.put(input, result);
        }

        @Override
        public Boolean processSignal(Boolean highSignal) {
            for (Boolean value : inputs.values()) {
                if (value == false) {
                    return true;
                }
            }

            return false;
        }

    }


    public record Signal(String name, Boolean highPulse){};

    @Override
    public long solvePart1(ArrayList<String> in) {
        HashMap<String, Module> modules = new HashMap<>();
        ArrayList<Signal> queue = new ArrayList<>();
        ArrayList<String> broadcastList = new ArrayList<>();
        long lowPulse = 0;
        long highPulse = 0;

        for (String line : in) {
            String[] parts = line.split(" -> ");
            if (parts[0].equals("broadcaster")) {
                String[] outputs = parts[1].split(", ");
                broadcastList.addAll(List.of(outputs));
            } else if (parts[0].startsWith("%")) {
                String input = parts[0].substring(1);
                String[] outputs = parts[1].split(", ");
                modules.put(input, new FlipFlop(input, new ArrayList<>(List.of(outputs))));
            } else if (parts[0].startsWith("&")) {
                String input = parts[0].substring(1);
                String[] outputs = parts[1].split(", ");
                modules.put(input, new Conjunction(input, new ArrayList<>(List.of(outputs))));
            } else {
                throw new RuntimeException("Invalid input!");
            }
        }

        for (Module conjunction : modules.values()) {
            if (conjunction instanceof Conjunction) {
                for (Module module : modules.values()) {
                    for (String destination : module.destinations) {
                        if (destination.equals(conjunction.name)) {
                            conjunction.addInput(module.name, false);
                        }
                    }
                }
            }
        }

        for (long i = 0; i < 10000; i++) {
            lowPulse++;
            for (String item : broadcastList) {
                Module module = modules.get(item);
                Boolean result = module.processSignal(false);
                lowPulse++;
                for (String destination : module.destinations) {
//                    System.out.println(item + " -> " + result + " -> " + destination);
                    queue.add(new Signal(destination, result));
                    if (modules.containsKey(destination)) {
                        modules.get(destination).addInput(item, result);
                    }
                }
            }

            while (!queue.isEmpty()) {
                Signal signal = queue.getFirst();
                if (signal.highPulse == true) highPulse++;
                else lowPulse++;
                queue.removeFirst();
                Module module = modules.get(signal.name);
                if (module == null) {
                    continue;
                }

                Boolean result = module.processSignal(signal.highPulse);
                if (result != null) {
                    for (String destination : module.destinations) {
//                        System.out.println(signal.name + " -> " + result + " -> " + destination);
                        queue.add(new Signal(destination, result));
                        // jg, rh, jm, and hf
                        if (module.name.equals("jg") && result == true) {
                            System.out.println("jg: " + (i + 1));
                        }
                        if (module.name.equals("rh") && result == true) {
                            System.out.println("rh: " + (i + 1));
                        }
                        if (module.name.equals("jm") && result == true) {
                            System.out.println("jm: " + (i + 1));
                        }
                        if (module.name.equals("hf") && result == true) {
                            System.out.println("hf: " + (i + 1));
                        }
                        if (modules.containsKey(destination)) {
                            modules.get(destination).addInput(signal.name, result);
                        }
                    }
                }
            }
        }

        return lowPulse * highPulse;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long aux = b;
            b = a % b;
            a = aux;
        }

        return a;
    }

    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        long a = 3793;
        long b = 3947;
        long c = 4003;
        long d = 4019;

        return lcm(lcm(lcm(a, b), c), d);
    }
}
