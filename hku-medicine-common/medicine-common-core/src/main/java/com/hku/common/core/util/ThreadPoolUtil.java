package com.hku.common.core.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pengzhengfa
 */

public class ThreadPoolUtil {
    /**
     * 线程池核心线程数
     */
    private static int CORE_POOL_SIZE = 5;
    /**
     * 线程池最大线程数
     */
    private static int MAX_POOL_SIZE = 100;
    /**
     * 额外线程空状态生存时间
     */
    private static int KEEP_ALIVE_TIME = 10000;
    /**
     * 阻塞队列,当核心线程都被占用,且阻塞队列已满的情况下,才会开启额外线程
     */
    private static BlockingQueue workQueue = new ArrayBlockingQueue(10);
    /**
     * 线程池
     */
    private static ThreadPoolExecutor threadPool;
    /**
     * 线程工厂
     */
    private static ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger integer = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "engine thread:" + integer.getAndIncrement());
        }
    };

    static {
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, workQueue, threadFactory);
    }

    private ThreadPoolUtil() {

    }

    public static void execute(Runnable runnable) {
        threadPool.execute(runnable);
    }

    public static void execute(FutureTask futureTask) {
        threadPool.execute(futureTask);
    }

    public static void cancel(FutureTask futureTask) {
        futureTask.cancel(true);
    }
}