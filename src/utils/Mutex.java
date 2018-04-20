package utils;

import java.util.concurrent.Semaphore;

/**
 * Mutex implementation using semaphore.
 *
 */
public class Mutex {
    private final Semaphore s = new Semaphore(1);

    /**
     * Acquire the mutex.
     * @throws InterruptedException if the current thread is interrupted
     */
    public void acquire() throws InterruptedException {
        this.s.acquire();
    }

    /**
     * Release the mutex.
     */
    public void release() {
        this.s.release();
    }
}
