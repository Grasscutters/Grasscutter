package emu.grasscutter.utils.algorithms;

import java.util.*;

public class KahnsSort {
    public static class Node {
        int source, dest; // Dest is a value, and source too

        public Node(int source, int dest) {
            this.source = source;
            this.dest = dest;
        }
    }

    public static class Graph {
        Map<Integer, List<Integer>> mainList;
        Map<Integer, Integer> degreeList;

        List<Integer> nodeList;

        public Graph(List<Node> nodes, List<Integer> nodeList) {
            mainList = new HashMap<>();
            this.nodeList = nodeList;

            for (int i = 0; i < nodeList.size(); i++) mainList.put(nodeList.get(i), new ArrayList<>());

            degreeList = new HashMap<>();
            for (int i = 0; i < nodeList.size(); i++) degreeList.put(nodeList.get(i), 0);

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

        if (degreeList.values().stream().filter(value -> value != 0).count() != 0)
            return null; // Loop found

        return orderedList;
    }
}
