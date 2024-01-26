package deque;

import org.junit.Test;

import static org.junit.Assert.*;


public class MaxArrayDequeTest {
    @Test
    public void basicTest() {
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(new IntegerComparator());
        assertTrue(mad.isEmpty());
        assertNull(mad.max());

        for (int i = 0; i < 11; i++) {
            mad.addLast(i);
        }
        assertEquals(10, (int) mad.max());

        int lastItem = mad.removeLast();
        assertEquals(10, lastItem);
        assertEquals(9, (int) mad.max());

        mad.addLast(100);
        assertEquals(100, (int) mad.max());

        assertEquals(0, (int) mad.max(new IntegerReverseComparator()));
    }
}
