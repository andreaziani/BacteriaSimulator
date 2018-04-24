package model.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import model.simulator.task.Task;

public class TaskWithMutex {
    private final List<Mutex> mutex;

    public TaskWithMutex(final int nMutex) {
        this.mutex = new ArrayList<>();
        IntStream.range(0, nMutex).forEach(x -> this.mutex.add(new Mutex()));
    }

    public void perform(final int mutexIndex, final Task t) throws InterruptedException {
        this.mutex.get(mutexIndex).acquire();
        try {
            t.execute();
        } finally {
            this.mutex.get(mutexIndex).release();
        }
    }
}
