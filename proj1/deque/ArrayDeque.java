package deque;

public class ArrayDeque<T> {

    private int size;
    private int cap;
    private int first;
    private int last;
    private T[] items;

    public ArrayDeque() {
        this.first = 1;
        this.last = 1;
        this.size = 0;
        this.cap = 8;
        this.items = (T[]) new Object[this.cap];
    }

    /**
     * Adds an item of type T to the front of the deque. You can assume that item is never null.
     */
    public void addFirst(T item) {
        int newPos = this.first - 1;
        if (newPos < 0) {
            newPos += this.cap;
        }

        if (newPos == this.last) {
            resize(this.cap * 2);
            newPos = this.cap - 1;
        }

        this.size++;
        this.items[newPos] = item;
        this.first = newPos;

        if (this.size == 1) {
            this.last = this.first;
        }
    }

    /**
     * Adds an item of type T to the back of the deque. You can assume that item is never null.
     */
    public void addLast(T item) {
        int newPos = (this.last + 1) % this.cap;
        if (newPos == this.first) {
            resize(this.cap * 2);
            newPos = this.last + 1;
        }

        this.size++;
        this.items[newPos] = item;
        this.last = newPos;

        if (this.size == 1) {
            this.first = this.last;
        }
    }

    private void resize(int newCap) {
        T[] newArr = (T[]) new Object[newCap];

        for (int i = this.first, j = 0; j < this.size; i++, j++) {
            newArr[j] = this.items[i % this.cap];
        }

        this.items = newArr;
        this.cap = newCap;
        this.first = 0;
        this.last = this.size - 1;
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
        if (this.isEmpty()) {
            System.out.println();
            return;
        }

        for (int i = this.first, j = 0; j < this.size; i++, j++) {
            System.out.print(this.items[i % this.cap] + " ");
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     */
    public T removeFirst() {

        if (this.isEmpty()) {
            return null;
        }

        if (this.size * 4 < this.cap) {
            this.resize(this.size);
        }

        T res = this.items[this.first];
        this.items[this.first] = null;
        this.first++;
        this.size--;

        return res;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     */
    public T removeLast() {

        if (this.isEmpty()) {
            return null;
        }

        if (this.size * 4 < this.cap) {
            this.resize(this.size);
        }

        T res = this.items[this.last];
        this.items[this.last] = null;
        this.last--;
        this.size--;

        return res;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index) {
        return this.items[(this.first + index) % this.cap];
    }
//
//    /** The Deque objects we’ll make are iterable (i.e. Iterable<T>) so we must provide this method to return an iterator.
//     */
//    public Iterator<T> iterator() {
//
//    }
//
//    /** Returns whether or not the parameter o is equal to the Deque. o is considered equal if it is a Deque and if it contains the same contents (as goverened by the generic T’s equals method) in the same order. (ADDED 2/12: You’ll need to use the instance of keywords for this. Read here for more information)
//     */
//    public boolean equals(Object o){
//        return false;
//    }
}
