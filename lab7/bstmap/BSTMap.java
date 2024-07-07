package bstmap;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    // represents a single BST node
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

    private boolean containsKeyHelper(BSTNode node, K key) {
        if (node == null) {
            return false;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return this.containsKeyHelper(node.left, key);
        } else if (cmp > 0) {
            return this.containsKeyHelper(node.right, key);
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

    private V getHelper(BSTNode node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return this.getHelper(node.left, key);
        } else if (cmp > 0) {
            return this.getHelper(node.right, key);
        } else {
            return node.val;
        }
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        return this.getHelper(this.root, key);
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return this.size;
    }

    private BSTNode putNode(BSTNode node, K key, V value) {
        if (node == null) {
            return new BSTNode(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = putNode(node.left, key, value);
        } else if (cmp > 0) {
            node.right = putNode(node.right, key, value);
        } else {
            // the key have already exists, just update it
            node.val = value;
        }

        return node;
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
        System.out.printf("key: %s, val: %s\n", root.key, root.val);
        printInOrderHelper(root.right);
    }

    public void printInOrder() {
        printInOrderHelper(this.root);
    }

    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        V val = this.get(key);
        if (!val.equals(value)) {
            return null;
        }

        return this.remove(key);
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        V ret = this.get(key);
        if (ret == null) {
            return null;
        }

        this.size -= 1;
        this.root = this.removeKeyNode(this.root, key);
        return ret;
    }

    private BSTNode removeKeyNode(BSTNode curr, K key) {
        if (curr == null) {
            return null;
        }

        int cmp = key.compareTo(curr.key);
        if (cmp == 0) {
            // remove this node
            return deleteNode(curr);
        } else if (cmp < 0) {
            // key < curr.key
            curr.left = removeKeyNode(curr.left, key);
        } else {
            // key > curr.key
            curr.right = removeKeyNode(curr.right, key);
        }

        return curr;
    }

    private BSTNode deleteNode(BSTNode node) {
        if (node.left == null) {
            return node.right;
        }
        if (node.right == null) {
            return node.left;
        }

        BSTNode newRoot = detachMax(node.left);
        if (newRoot == node.left) {
            newRoot.right = node.right;
            return newRoot;
        }

        newRoot.left = node.left;
        newRoot.right = node.right;
        return newRoot;
    }

    private BSTNode detachMax(BSTNode node) {
        BSTNode prev = node;
        while (node.right != null) {
            prev = node;
            node = node.right;
        }

        if (prev == node) {
            return node;
        }

        prev.right = node.left;
        return node;
    }

    private class BSTIterator implements Iterator<K> {
        private final Stack<BSTNode> stack;

        public BSTIterator(BSTNode root) {
            this.stack = new Stack<>();
            this.pushAllLeft(root);
        }

        private void pushAllLeft(BSTNode node) {
            while (node != null) {
                this.stack.push(node);
                node = node.left;
            }
        }

        public boolean hasNext() {
            return !this.stack.isEmpty();
        }

        public K next() {
            BSTNode node = this.stack.pop();
            pushAllLeft(node.right);
            return node.key;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator(this.root);
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<>();
        Iterator<K> iter = new BSTIterator(this.root);

        while (iter.hasNext()) {
            keys.add(iter.next());
        }

        return keys;
    }
}
