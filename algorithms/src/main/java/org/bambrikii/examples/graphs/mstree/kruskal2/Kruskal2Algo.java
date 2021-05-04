package org.bambrikii.examples.graphs.mstree.kruskal2;

import org.bambrikii.examples.graphs.mstree.Edge;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Kruskal2Algo {
    private Map<Integer, List<Edge>> edgesMap = new HashMap<>();
    private Map<Integer, Integer> parents = new HashMap<>();

    public Kruskal2Algo edge(int weight, int from, int to) {
        Edge edge = new Edge(weight, from, to);
        if (!edgesMap.containsKey(from)) {
            edgesMap.put(from, new ArrayList<>());
        }
        edgesMap.get(from).add(edge);
        return this;
    }

    private Integer find(int child) {
        if (parents.get(child) == null) {
            parents.put(child, child);
        }
        Integer parent = parents.get(child);
        if (parents.get(child) != parents.get(parent)) {
            parent = find(parent);
            parents.put(child, parent);
        }
        return parents.get(child);
    }

    private void union(int from, int to) {
        parents.put(from, to);
    }

    public List<Edge> msp() {
        List<Edge> msp = new ArrayList<>();

        ArrayList<Edge> edges = new ArrayList<>(this.edgesMap.values()
                .stream()
                .flatMap(e -> e.stream())
                .sorted(Comparator.comparingInt(o -> o.getW()))
                .collect(Collectors.toList())
        );

        for (Edge edge : edges) {
            int from = edge.getFrom();
            int to = edge.getTo();

            Integer fromParent = find(from);
            Integer toParent = find(to);

            if (fromParent != toParent) {
                msp.add(edge);
                union(from, to);
            }
        }
        return msp;
    }

}
