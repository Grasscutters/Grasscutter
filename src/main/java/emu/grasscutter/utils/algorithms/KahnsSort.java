package emu.grasscutter.utils.algorithms;

import java.util.*;

public class KahnsSort {
    public static class Node {
        final int source;
        final int dest; // Dest is a value, and source too

        public Node(int source, int dest) {
            this.source = source;
            this.dest = dest;
        }
    }

    public static class Graph {
        final Map<Integer, List<Integer>> mainList;
        final Map<Integer, Integer> degreeList;

        final List<Integer> nodeList;

        public Graph(List<Node> nodes, List<Integer> nodeList) {
            mainList = new HashMap<>();
            this.nodeList = nodeList;

            for (Integer value : nodeList) mainList.put(value, new ArrayList<>());

            degreeList = new HashMap<>();
            for (Integer integer : nodeList) degreeList.put(integer, 0);

            for (Node node : nodes) {
                mainList.get(node.source).add(node.dest);
                degreeList.replace(node.dest, degreeList.get(node.dest) + 1);
            }
        }
    }

    public static List<Integer> doSort(Graph graph) {
        List<Integer> orderedList = new ArrayList<>();
        Map<Integer, Integer> degreeList = graph.degreeList;

        Stack<Integer> zeroStack = new Stack<>();
        degreeList.forEach(
                (key, value) -> {
                    if (value == 0) zeroStack.add(key);
                });

        while (!zeroStack.isEmpty()) {
            int element = zeroStack.pop();

            // If the list is empty then this node
            if (!graph.mainList.get(element).isEmpty()) orderedList.add(element);
            for (int topElement : graph.mainList.get(element)) {
                degreeList.replace(topElement, degreeList.get(topElement) - 1);

                if (degreeList.get(topElement) == 0) zeroStack.add(topElement);
            }
        }

        if (degreeList.values().stream().anyMatch(value -> value != 0)) return null; // Loop found

        return orderedList;
    }
}
