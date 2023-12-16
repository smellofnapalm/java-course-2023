package edu.hw8.task2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

class FixedThreadPool implements ThreadPool {
    private final int numThreads;
    private final Thread[] threads;
    private final BlockingQueue<Runnable> taskQueue;
    private final AtomicBoolean isDown = new AtomicBoolean(false);

    FixedThreadPool(int numThreads) {
        this.numThreads = numThreads;
        this.threads = new Thread[numThreads];
        this.taskQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void start() {
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(getDefaultRunnable());
            threads[i].start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        try {
            while (!taskQueue.contains(runnable)) {
                taskQueue.put(runnable);
            }
        } catch (InterruptedException e) {
            execute(runnable);
        }
    }

    @Override
    public void close() {
        isDown.set(true);
    }

    private Runnable getDefaultRunnable() {
        return () -> {
            while (!isDown.get() && !Thread.currentThread().isInterrupted()) {
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
    }
}

