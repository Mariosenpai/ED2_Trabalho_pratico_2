package com.pratica.ed2.estrutura;

import java.util.ArrayList;

public class RubroNegra <Chave extends Comparable<Chave>, Valor> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    class Node {
        Chave chave;
        ArrayList<Valor> val;
        Node    left, right;
        int     N;             // número de nós nesta subárvore
        boolean color;         // cor do link que aponta para este nó

        Node(Chave chave, Valor val, int N, boolean color) {
            this.chave = chave;
            this.val   = new ArrayList<>();
            this.val.add(val);
            this.N     = N;
            this.color = color;
        }
    }


    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public void put(Chave chave, Valor val) {
        root = put(root, chave, val);
        root.color = BLACK;
    }

    private Node put(Node h, Chave chave, Valor val) {
        if (h == null)
            return new Node(chave, val, 1, RED);

        int cmp = chave.compareTo(h.chave);
        if      (cmp < 0) h.left  = put(h.left, chave, val);
        else if (cmp > 0) h.right = put(h.right, chave, val);
        else              h.val.add(val);

        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.N;
    }

    public ArrayList<Valor> get(Chave key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        return get(root, key);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private ArrayList<Valor> get(Node x, Chave key) {
        while (x != null) {
            int cmp = key.compareTo(x.chave);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.val;
        }
        return null;
    }


}
