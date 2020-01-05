/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bambrikii.examples.algorithms.incubator.redblackrtee.print;

import org.bambrikii.examples.algorithms.incubator.redblackrtee.RBColorEnum;
import org.bambrikii.examples.algorithms.incubator.redblackrtee.RBNode;

import java.util.*;

/**
 * @author asd
 */
public class RBTreePrinter {
    public static <T> void print(RBNode<T> n) {
        int height = 1;
        TreePrintWrapper<T> view = new TreePrintWrapper<>();
        Set<RBNode<T>> wasAdded = new HashSet<>();
        LinkedList<RBNode<T>> stack = new LinkedList<>();
        List<RBNode<T>> sorted = new ArrayList<>();
        stack.push(n);
        while (!stack.isEmpty()) {
            final RBNode<T> curr = stack.peek();

            if (wasAdded.contains(curr)) {
                stack.pop();
                height--;
                continue;
            }

            RBNode<T> left = curr.getLeft();
            if (left != null && !wasAdded.contains(left)) {
                stack.push(left);
                height++;
                continue;
            }

            sorted.add(curr);
            view.addNode(height, sorted.size() - 1, curr);
            wasAdded.add(curr);

            RBNode<T> right = curr.getRight();
            if (right != null && !wasAdded.contains(right)) {
                stack.push(right);
                height++;
                continue;
            }
        }

        doPrint(view);
    }

    private static final int MAX_NODE_SIZE = 12;

    private static <T> void doPrint(TreePrintWrapper<T> view) {
        for (RowPrintWrapper<T> row : view.getRows()) {
            NodePrintWrapper<T> prevNode = null;
            for (NodePrintWrapper<T> node : row.getNodes()) {
                RBNode<T> x = node.getNode();
                for (int i = 0; i < node.getX() - (prevNode != null ? prevNode.getX() + 1 : 0); i++) {
                    printOffset(MAX_NODE_SIZE, ".");
                }

                String s = x.getVal() + "[" + RBColorEnum.toString(x.getColor()) + "][" + row.getY() + ":" + node.getX() + "]";
                double offset = ((double) MAX_NODE_SIZE - s.length()) / 2;
                printOffset((int) Math.ceil(offset), " ");
                System.out.print(s);
                printOffset((int) Math.floor(offset), " ");
                prevNode = node;
            }
            System.out.println();
        }
    }

    private static void printOffset(int i2, String s2) {
        for (int j = 0; j < i2; j++) {
            System.out.print(s2);
        }
    }

    private static class TreePrintWrapper<T> {
        private List<RowPrintWrapper<T>> rows = new ArrayList<>();

        public void addNode(int y, int x, RBNode<T> node) {
            int size = rows.size();
            for (int i = 0; i <= y - size; i++) {
                rows.add(new RowPrintWrapper<>(i + size));
            }
            RowPrintWrapper<T> wrapper = rows.get(y);
            wrapper.addNode(x, node);
        }

        public List<RowPrintWrapper<T>> getRows() {
            return rows;
        }
    }

    private static class RowPrintWrapper<T> {
        private final int y;
        private final List<NodePrintWrapper<T>> nodes = new LinkedList<>();

        public RowPrintWrapper(int y) {
            this.y = y;
        }

        public int getY() {
            return y;
        }

        public void addNode(int x, RBNode<T> node) {
            nodes.add(new NodePrintWrapper<>(x, node));
        }

        public List<NodePrintWrapper<T>> getNodes() {
            return nodes;
        }
    }

    private static class NodePrintWrapper<T> {
        private final RBNode<T> node;
        private final int x;

        public NodePrintWrapper(int x, RBNode<T> node) {
            this.x = x;
            this.node = node;
        }

        public int getX() {
            return x;
        }

        public RBNode<T> getNode() {
            return node;
        }
    }
}
