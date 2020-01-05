package org.bambrikii.examples.algorithms.incubator.redblackrtee;

import org.bambrikii.examples.algorithms.incubator.redblacktree2.RotationDecorator;
import org.bambrikii.examples.algorithms.incubator.redblacktree2.RotationDecoratorFactory;

import java.util.Comparator;

public abstract class AbstractRBTree<T> {
    protected RBNode<T> root;
    protected final Comparator<T> comparator;

    public AbstractRBTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public RBNode<T> getRoot() {
        return root;
    }

    public abstract void insert(T val);

    public abstract void delete(T val);

    protected RBNode<T> insertBefore(T val) {
        if (root == null) {
            root = new RBNode(val);
            return root;
        }
        return insert(root, val);
    }

    private RBNode<T> insert(RBNode<T> p, T val) {
        int cmp = comparator.compare(p.getVal(), val);
        if (cmp == 0) {
            throw new IllegalArgumentException("Node's " + p + " value is equal to " + val + "! But shouldn't!");
        }
        RBNode<T> x = new RBNode<>(val);
        x.setParent(p);
        RotationDecorator rotationDecorator = RotationDecoratorFactory.byDirection(cmp);
        if (rotationDecorator.getLeft(p) == null) {
            rotationDecorator.setLeft(p, x);
            return x;
        }
        return insert(rotationDecorator.getLeft(p), val);
    }
}
