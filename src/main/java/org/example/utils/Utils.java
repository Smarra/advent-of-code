package org.example.utils;

public class Utils {
    public record Pair(int x, int y) {
        public Pair add(Pair p) {
            return new Pair(this.x + p.x, this.y + p.y);
        }
    }
}
