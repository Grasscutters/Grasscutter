package emu.grasscutter.utils.objects;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class WeightedList<E> {
    private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private double total = 0;

    public WeightedList() {}

    public WeightedList<E> add(double weight, E result) {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, result);
        return this;
    }

    public E next() {
        double value = ThreadLocalRandom.current().nextDouble() * total;
        return map.higherEntry(value).getValue();
    }

    public int size() {
        return map.size();
    }
}
