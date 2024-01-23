package deque;

import net.sf.saxon.expr.instruct.Copy;

public class LinkedListDeque<T> {

    private static class LinkedNode<N> {
        private N val;
        private LinkedNode<N> prev;
        private LinkedNode<N> next;

        public LinkedNode() {
            this.val = null;
            this.prev = this;
            this.next = this;
        }

        public LinkedNode(N val, LinkedNode<N> prev, LinkedNode<N> next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }
    }

    private int size;
    private LinkedNode<T> first;
    private LinkedNode<T> last;

    public LinkedListDeque() {
        this.size = 0;

        this.first = new LinkedNode<>();
        this.last = new LinkedNode<>();

        this.first.next = this.last;
        this.last.prev = this.first;
    }

    /**
     * Adds an item of type T to the front of the deque. You can assume that item is never null.
     */
    public void addFirst(T item) {
        LinkedNode<T> node = new LinkedNode<>(item, this.first, this.first.next);

        this.first.next.prev = node;
        this.first.next = node;

        this.size++;
    }

    /**
     * Adds an item of type T to the back of the deque. You can assume that item is never null.
     */
    public void addLast(T item) {
        LinkedNode<T> node = new LinkedNode<>(item, this.last.prev, this.last);

        this.last.prev.next = node;
        this.last.prev = node;

        this.size++;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns the number of items in the deque.
     */
    public int size() {
        return this.size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        LinkedNode<T> node = this.first.next;

        while (node != this.last) {
            System.out.print(node.val + " ");
            node = node.next;
        }

        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     */
    public T removeFirst() {
        if (this.size == 0) {
            System.out.println("nothing to remove!");
            return null;
        }

        this.size--;
        LinkedNode<T> res = this.first.next;
        this.first.next = res.next;
        res.next.prev = this.first;

        return res.val;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     */
    public T removeLast() {
        if (this.size == 0) {
            System.out.println("nothing to remove!");
            return null;
        }

        this.size--;
        LinkedNode<T> res = this.last.prev;
        this.last.prev = res.prev;
        res.prev.next = this.last;

        return res.val;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index) {
        if (index >= this.size) {
            return null;
        }

        LinkedNode<T> node = this.first;
        for (int i = 0; i <= index; i++) {
            node = node.next;
        }

        return node.val;
    }

    /**
     * Same as get, but uses recursion.
     */
    public T getRecursive(int index) {
        if (index >= this.size) {
            return null;
        }

        return getHelper(index, this.first.next);
    }

    /**
     * Helper function for getRecursive.
     */
    private T getHelper(int index, LinkedNode<T> head) {
        if (index == 0) {
            return head.val;
        }

        return getHelper(index - 1, head.next);
    }

    /** The Deque objects we’ll make are iterable (i.e. Iterable<T>) so we must provide this method to return an iterator.
     */
//    public Iterator<T> iterator() {
//
//    }

    /**
     * Returns whether or not the parameter o is equal to the Deque. o is considered equal if it is a Deque and if it contains the same contents (as goverened by the generic T’s equals method) in the same order. (ADDED 2/12: You’ll need to use the instance of keywords for this. Read here for more information)
     */
    public boolean equals(Object o) {
        if (!(o instanceof LinkedListDeque)) {
            return false;
        }

        return false;
    }
}
