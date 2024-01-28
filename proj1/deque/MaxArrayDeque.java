package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private T maxItem;
    private Comparator<T> comp;


    public MaxArrayDeque(Comparator<T> c) {
        this.maxItem = null;
        this.comp = c;
    }

    /**
     * helper function for check new maxItem when adding new items.
     */
    private void checkNewMax(T item) {
        if (this.maxItem == null) {
            this.maxItem = item;
            return;
        }

        if ((this.comp.compare(this.maxItem, item)) < 0) {
            this.maxItem = item;
        }
    }

//    @Override
//    public void addLast(T item) {
//        super.addLast(item);
//        this.checkNewMax(item);
//    }
//
//    @Override
//    public void addFirst(T item) {
//        super.addFirst(item);
//        this.checkNewMax(item);
//    }

    /**
     * helper function for calculate a maxItem with specific Comparator.
     */
    private T calculateMax(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }

        T tmpMax = this.get(0);
        for (int i = 1; i < this.size(); i++) {
            T cur = this.get(i);
            if ((c.compare(tmpMax, cur)) < 0) {
                tmpMax = cur;
            }
        }

        return tmpMax;
    }

//    @Override
//    public T removeLast() {
//        T res = super.removeLast();
//        this.maxItem = this.calculateMax(this.comp);
//
//        return res;
//    }
//
//    @Override
//    public T removeFirst() {
//        T res = super.removeFirst();
//        this.maxItem = this.calculateMax(this.comp);
//
//        return res;
//    }

    public T max() {
        return this.maxItem;
    }

    public T max(Comparator<T> c) {
        return calculateMax(c);
    }
}
