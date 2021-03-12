package com.mint.other.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cw
 * @date 2019/7/8 18:20
 */
public class MyThreadFactory implements ThreadFactory {
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("my_thread_" + ATOMIC_INTEGER.getAndIncrement());
        return thread;
    }
}
