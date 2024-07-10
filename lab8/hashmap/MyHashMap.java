package hashmap;

import java.util.*;

/**
 * A hash table-backed Map implementation. Provides amortized constant time
 * access to elements via get(), remove(), and put() in the best case.
 * <p>
 * Assumes null keys will never be inserted, and does not resize down upon remove().
 *
 * @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private final int RESIZE_FACTOR = 2;
    private final int NUM_DEFAULT_BUCKET = 16;
    private final double DEFAULT_LOAD_FACTOR = 0.75;

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int numItems = 0;
    private int numBuckets = NUM_DEFAULT_BUCKET;
    private double loadFactor = DEFAULT_LOAD_FACTOR;
    // You should probably define some more!

    /**
     * Constructors
     */
    public MyHashMap() {
        this.buckets = this.setupBuckets(this.numBuckets);
    }

    public MyHashMap(int initialSize) {
        this.numBuckets = initialSize;
        this.buckets = this.setupBuckets(initialSize);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad     maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.numBuckets = initialSize;
        this.loadFactor = maxLoad;
        this.buckets = this.setupBuckets(initialSize);
    }

    private Collection[] setupBuckets(int numBuckets) {
        Collection<Node>[] buckets = new Collection[numBuckets];
        for (int i = 0; i < numBuckets; i++) {
            buckets[i] = this.createBucket();
        }

        return buckets;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        // TODO: figure out what this method does
        return null;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!


    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        this.numItems = 0;
        this.numBuckets = NUM_DEFAULT_BUCKET;
        this.buckets = this.setupBuckets(this.numBuckets);
    }

    private int getBucketOffset(int keyHashCode, int numBuckets) {
        return Math.floorMod(keyHashCode, numBuckets);
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        V val = this.get(key);

        return val != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        int bucketOffset = this.getBucketOffset(key.hashCode(), this.numBuckets);
        Node keyNode = this.getNode(key, this.buckets[bucketOffset]);

        return keyNode == null ? null : keyNode.value;
    }

    private Node getNode(K key, Collection<Node> bucket) {
        for (Node n : bucket) {
            if (n.key.equals(key)) {
                return n;
            }
        }

        return null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return this.numItems;
    }

    private double calculateCurrentLoadFactor() {
        return (double) this.numItems / this.numBuckets;
    }

    private void resize() {
        int newNumBuckets = this.numBuckets * this.RESIZE_FACTOR;
        Collection<Node>[] newBuckets = this.setupBuckets(newNumBuckets);

        Iterator<K> iter = new HashIterator();
        while (iter.hasNext()) {
            K key = iter.next();
            V val = this.get(key);

            int newBucketOffset = this.getBucketOffset(key.hashCode(), newNumBuckets);
            this.bucketAddNode(newBuckets[newBucketOffset], new Node(key, val));
        }

        this.buckets = newBuckets;
        this.numBuckets = newNumBuckets;
    }

    private void bucketAddNode(Collection<Node> bucket, Node node) {
        bucket.add(node);
    }

    private void myPut(Node node) {
        int bucketOffset = this.getBucketOffset(node.key.hashCode(), this.numBuckets);
        this.bucketAddNode(this.buckets[bucketOffset], node);
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        // this kv has already exists
        V oldValue = this.get(key);
        if (oldValue != null) {
            // put different value, update
            if (!oldValue.equals(value)) {
                int bucketOffset = this.getBucketOffset(key.hashCode(), this.numBuckets);
                for (Node node : this.buckets[bucketOffset]) {
                    if (node.key.equals(key)) {
                        node.value = value;
                        return;
                    }
                }
            }
            return;
        }

        double newLoadFactor = this.calculateCurrentLoadFactor();
        if (newLoadFactor > this.loadFactor) {
            this.resize();
        }

        this.myPut(new Node(key, value));
        this.numItems += 1;
    }

    private class HashIterator implements Iterator<K> {
        private int currCount;
        private int bucketOffset;
        private int iterNumItems;
        private Iterator<Node> bucketIter;

        public HashIterator() {
            this.currCount = 0;
            this.bucketOffset = 0;
            this.iterNumItems = numItems;
            this.bucketIter = buckets[this.bucketOffset].iterator();
        }

        @Override
        public boolean hasNext() {
            return this.currCount < this.iterNumItems;
        }

        @Override
        public K next() {
            this.currCount += 1;
            if (this.bucketIter.hasNext()) {
                return bucketIter.next().key;
            }

            do {
                bucketOffset += 1;
            } while (buckets[this.bucketOffset].isEmpty());

            bucketIter = buckets[this.bucketOffset].iterator();
            return bucketIter.next().key;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new HashIterator();
    }

    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<>();
        Iterator<K> iter = new HashIterator();

        while (iter.hasNext()) {
            keys.add(iter.next());
        }

        return keys;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        int bucketOffset = this.getBucketOffset(key.hashCode(), this.numBuckets);
        Node node = this.getNode(key, this.buckets[bucketOffset]);
        if (node == null) {
            return null;
        }

        this.removeNode(node, bucketOffset);

        return node.value;
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        int bucketOffset = this.getBucketOffset(key.hashCode(), this.numBuckets);
        Node node = this.getNode(key, this.buckets[bucketOffset]);
        if (node == null) {
            return value;
        }
        if (node.value.equals(value)) {
            this.removeNode(node, bucketOffset);
        }

        return value;
    }

    private void removeNode(Node node, int bucketOffset) {
        this.buckets[bucketOffset].remove(node);
        this.numItems -= 1;
    }
}
