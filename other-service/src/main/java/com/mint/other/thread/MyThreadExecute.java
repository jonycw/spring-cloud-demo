package com.mint.other.thread;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author cw
 * @date 2019/7/8 18:16
 */
@Component
public class MyThreadExecute {
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 0,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), new MyThreadFactory());

    /**
     * 获取线程组
     * @return threadExecutor
     */
    public ThreadPoolExecutor getThreadExecute() {
        return executor;
    }
}
