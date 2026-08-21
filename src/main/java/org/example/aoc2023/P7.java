package org.example.aoc2023;

import org.example.domain.AocProblem;

import java.util.ArrayList;
import java.util.Comparator;

public class P7 implements AocProblem {
    public record Hand(String hand, long bid) {};
    char[] order1 = new char[]{'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'};
    char[] order2 = new char[]{'A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J'};

    public int charCount(char charTarget, String str) {
        int ct = 0;
        for (char chr : str.toCharArray()) {
            if (chr == charTarget) {
                ct++;
            }
        }
        return ct;
    }

    public boolean fiveOfAKind(String hand, boolean jokerEnabled) {
        if (charCount(hand.charAt(0), hand) == 5) {
            return true;
        }

        if (jokerEnabled) {
            for (char chr : hand.toCharArray()) {
                if (charCount(chr, hand) + charCount('J', hand) == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean fourOfAKind(String hand, boolean jokerEnabled) {
        for (char chr : hand.toCharArray()) {
            if (charCount(chr, hand) == 4) {
                return true;
            }

            if (jokerEnabled) {
                if (chr != 'J' && charCount(chr, hand) + charCount('J', hand) == 4) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean fullHouse(String hand, boolean jokerEnabled) {
        char[] chrs = hand.toCharArray();
        for (int i = 0; i < chrs.length; i++) {
            for (int j = i + 1; j < chrs.length; j++) {
                if (chrs[i] == chrs[j]) {
                    continue;
                }
                int ict = charCount(chrs[i], hand);
                int jct = charCount(chrs[j], hand);
                if (ict + jct == 5) {
                    return true;
                }

                if (jokerEnabled && chrs[i] != 'J' && chrs[j] != 'J') {
                    int jjct = charCount('J', hand);
                    if (ict + jct + jjct == 5) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean threeOfAKind(String hand, boolean jokerEnabled) {
        char[] chrs = hand.toCharArray();
        for (int i = 0; i < chrs.length; i++) {
            int ict = charCount(chrs[i], hand);
            if (ict == 3) {
                return true;
            }

            if (jokerEnabled && chrs[i] != 'J') {
                int jjct = charCount('J', hand);
                if (ict + jjct == 3) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean twoPair(String hand, boolean jokerEnabled) {
        char[] chrs = hand.toCharArray();
        for (int i = 0; i < chrs.length; i++) {
            for (int j = i + 1; j < chrs.length; j++) {
                if (chrs[i] == chrs[j]) {
                    continue;
                }
                int ict = charCount(chrs[i], hand);
                int jct = charCount(chrs[j], hand);
                if (ict + jct == 4) {
                    return true;
                }

                if (jokerEnabled && chrs[i] != 'J' && chrs[j] != 'J') {
                    int jjct = charCount('J', hand);
                    if (ict + jct + jjct == 4) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean onePair(String hand, boolean jokerEnabled) {

        char[] chrs = hand.toCharArray();
        for (int i = 0; i < chrs.length; i++) {
            int ict = charCount(chrs[i], hand);
            if (ict == 2) {
                return true;
            }

            if (jokerEnabled) {
                int jjct = charCount('J', hand);
                if (jjct > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getHandOrder(String hand, boolean jokerEnabled) {
        if (fiveOfAKind(hand, jokerEnabled)) {
            return 1;
        }
        if (fourOfAKind(hand, jokerEnabled)) {
            return 2;
        }
        if (fullHouse(hand, jokerEnabled)) {
            return 3;
        }
        if (threeOfAKind(hand, jokerEnabled)) {
            return 4;
        }
        if (twoPair(hand, jokerEnabled)) {
            return 5;
        }
        if (onePair(hand, jokerEnabled)) {
            return 6;
        }
        return 7;
    }

    private void sortList(ArrayList<Hand> hands, boolean jokerEnabled) {
        hands.sort((o1, o2) -> {
            int val1 = getHandOrder(o1.hand, jokerEnabled);
            int val2 = getHandOrder(o2.hand, jokerEnabled);

            if (val1 != val2) {
                return val2 - val1;
            } else {
                char[] hand1 = o1.hand.toCharArray();
                char[] hand2 = o2.hand.toCharArray();
                for (int i = 0; i < hand1.length; i++) {
                    if (hand1[i] == hand2[i]) {
                        continue;
                    }
                    char[] order = jokerEnabled ? order2 : order1;
                    for (char ord : order) {
                        if (ord == hand1[i]) {
                            return 1;
                        }
                        if (ord == hand2[i]) {
                            return -1;
                        }
                    }
                }
                return 0;
            }
        });
    }

    @Override
    public long solvePart1(ArrayList<String> input) {
        ArrayList<Hand> hands = new ArrayList<>();

        for (String line : input) {
            String hand = line.split(" ")[0];
            long bid = Long.parseLong(line.split(" ")[1]);

            hands.add(new Hand(hand, bid));
        }
        sortList(hands, false);

        long result = 0;
        int ct = 1;
        for (Hand hand : hands) {
            System.out.println(hand);
            result += hand.bid * ct;
            ct++;
        }
        return result;
    }

    @Override
    public long solvePart2(ArrayList<String> input) {
        ArrayList<Hand> hands = new ArrayList<>();

        for (String line : input) {
            String hand = line.split(" ")[0];
            long bid = Long.parseLong(line.split(" ")[1]);

            hands.add(new Hand(hand, bid));
        }
        sortList(hands, true);

        long result = 0;
        int ct = 1;
        for (Hand hand : hands) {
            System.out.println(hand);
            result += hand.bid * ct;
            ct++;
        }

        System.out.println(getHandOrder("JJ234", true));

        return result;
    }
}
