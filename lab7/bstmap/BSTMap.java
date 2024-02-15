package bstmap;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTNode {
        private K key;
        private V val;
        private BSTNode left, right;

        public BSTNode(K key, V val) {
            this.key = key;
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    private BSTNode root;
    private int size;

    public BSTMap() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    private boolean containsKeyHelper(BSTNode root, K key) {
        if (root == null) {
            return false;
        }

        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            return this.containsKeyHelper(root.left, key);
        } else if (cmp > 0) {
            return this.containsKeyHelper(root.right, key);
        } else {
            return true;
        }
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return containsKeyHelper(this.root, key);
    }

    private V getNode(BSTNode root, K key) {
        if (root == null) {
            return null;
        }

        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            return this.getNode(root.left, key);
        } else if (cmp > 0) {
            return this.getNode(root.right, key);
        } else {
            return root.val;
        }
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        return this.getNode(this.root, key);
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return this.size;
    }

    private BSTNode putNode(BSTNode root, K key, V value) {
        if (root == null) {
            return new BSTNode(key, value);
        }

        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            root.left = putNode(root.left, key, value);
        } else if (cmp > 0) {
            root.right = putNode(root.right, key, value);
        } else {
            root.val = value;
        }

        return root;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        this.root = this.putNode(this.root, key, value);
        this.size++;
    }

    private void printInOrderHelper(BSTNode root) {
        if (root == null) {
            return;
        }

        printInOrderHelper(root.left);
        System.out.println("key: " + root.key + ", value: " + root.val);
        printInOrderHelper(root.right);
    }

    public void printInOrder() {
        printInOrderHelper(this.root);
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }
}
