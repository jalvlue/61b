package gh2;

import deque.ArrayDeque;
import deque.Deque;

public class GuitarString {
    /**
     * Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday.
     */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {

        int cap = (int) Math.round(GuitarString.SR / frequency);

        this.buffer = new ArrayDeque<>();
        for (int i = 0; i < cap; i++) {
            this.buffer.addFirst((double) 0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {

        int dequeSize = this.buffer.size();
        double randomNoise;

        for (int i = 0; i < dequeSize; i++) {
            this.buffer.removeFirst();

            randomNoise = Math.random() - 0.5;
            this.buffer.addLast(randomNoise);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {

        double first = this.buffer.removeFirst();
        double second = this.buffer.get(0);
        double newSample = (first + second) * 0.5 * GuitarString.DECAY;

        this.buffer.addLast(newSample);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return this.buffer.get(0);
    }
}
