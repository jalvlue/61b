package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correctList = new AListNoResizing<>();
        BuggyAList<Integer> buggyList = new BuggyAList<>();

        correctList.addLast(4);
        correctList.addLast(5);
        correctList.addLast(6);

        buggyList.addLast(4);
        buggyList.addLast(5);
        buggyList.addLast(6);

        assertEquals(correctList.size(), buggyList.size());

        assertEquals(correctList.removeLast(), buggyList.removeLast());
        assertEquals(correctList.removeLast(), buggyList.removeLast());
        assertEquals(correctList.removeLast(), buggyList.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();

        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                broken.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                System.out.println("correctSize: " + L.size());
                System.out.println("buggySize: " + broken.size());
            } else if (L.size() != 0 && operationNumber == 2) {
                System.out.println("correctGetLast: " + L.getLast());
                System.out.println("buggyGetLast: " + broken.getLast());
            } else if (L.size() != 0 && operationNumber == 3) {
                System.out.println("correctRemoveLast: " + L.removeLast());
                System.out.println("buggyRemoveLast: "+broken.removeLast());
            }
        }
    }
}
