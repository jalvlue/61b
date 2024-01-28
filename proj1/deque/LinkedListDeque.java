package deque;


import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {

    private static class LinkedNode<N> {
        private N val;
        private LinkedNode<N> prev;
        private LinkedNode<N> next;

        LinkedNode() {
            this.val = null;
            this.prev = this;
            this.next = this;
        }

        LinkedNode(N val, LinkedNode<N> prev, LinkedNode<N> next) {
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
    @Override
    public void addFirst(T item) {
        LinkedNode<T> node = new LinkedNode<>(item, this.first, this.first.next);

        this.first.next.prev = node;
        this.first.next = node;

        this.size++;
    }

    /**
     * Adds an item of type T to the back of the deque. You can assume that item is never null.
     */
    @Override
    public void addLast(T item) {
        LinkedNode<T> node = new LinkedNode<>(item, this.last.prev, this.last);

        this.last.prev.next = node;
        this.last.prev = node;

        this.size++;
    }

    /**
     * Returns the number of items in the deque.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    @Override
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
    @Override
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
    @Override
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
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     */
    @Override
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

    /**
     * The Deque objects we’ll make are iterable (i.e. Iterable<T>)
     * so we must provide this method to return an iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIter();
    }

    private class LinkedListIter implements Iterator<T> {
        int pos;

        LinkedListIter() {
            this.pos = 0;
        }

        public boolean hasNext() {
            return this.pos < size;
        }

        public T next() {
            if (this.hasNext()) {
                return get(this.pos++);
            }
            return null;
        }
    }

    /**
     * Returns whether or not the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque and if it contains the same contents
     * (as goverened by the generic T’s equals method) in the same order.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deque)) {
            return false;
        }

        Deque<T> other = (Deque<T>) o;
        if (this.size != other.size()) {
            return false;
        }

        T item1, item2;

        for (int i = 0; i < this.size(); i++) {
            item1 = this.get(i);
            item2 = other.get(i);

            if (!item1.equals(item2)) {
                return false;
            }
        }

        return true;
    }
}
