package emu.grasscutter.utils;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class WeightedList<E> {
    private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private double total = 0;

    public WeightedList() {

    }

    public WeightedList<E> add(double weight, E result) {
        if (weight <= 0) return this;
        this.total += weight;
        this.map.put(this.total, result);
        return this;
    }

    public E next() {
        double value = ThreadLocalRandom.current().nextDouble() * this.total;
        return this.map.higherEntry(value).getValue();
    }

    public int size() {
        return this.map.size();
    }
}