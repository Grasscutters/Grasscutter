package emu.grasscutter.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public final class SparseSet {
    /*
     * A convenience class for constructing integer sets out of large ranges
     * Designed to be fed literal strings from this project only -
     * can and will throw exceptions to tell you to fix your code if you feed it garbage. :)
     */
    private static class Range {
        private final int min, max;

        public Range(int min, int max) {
            if (min > max) {
                throw new IllegalArgumentException("Range passed minimum higher than maximum - " + Integer.toString(min) + " > " + Integer.toString(max));
            }
            this.min = min;
            this.max = max;
        }

        public boolean check(int value) {
            return value >= this.min && value <= this.max;
        }
    }

    private final List<Range> rangeEntries;
    private final Set<Integer> denseEntries;

    public SparseSet(String csv) {
        this.rangeEntries = new ArrayList<>();
        this.denseEntries = new TreeSet<>();

        for (String token : csv.replace("\n", "").replace(" ", "").split(",")) {
            String[] tokens = token.split("-");
            switch (tokens.length) {
                case 1:
                    this.denseEntries.add(Integer.parseInt(tokens[0]));
                    break;
                case 2:
                    this.rangeEntries.add(new Range(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid token passed to SparseSet initializer - " + token + " (split length " + Integer.toString(tokens.length) + ")");
            }
        }
    }

    public boolean contains(int i) {
        for (Range range : this.rangeEntries) {
            if (range.check(i)) {
                return true;
            }
        }
        return this.denseEntries.contains(i);
    }
}