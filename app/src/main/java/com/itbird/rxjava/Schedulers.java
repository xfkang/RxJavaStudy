package com.itbird.rxjava;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by itbird on 2022/12/16
 */
public class Schedulers implements SchedulerInterface {

    static final int CORE_POOL_SIZE = 1;
    static final int MAXIMUM_POOL_SIZE = 20;
    static final int BACKUP_POOL_SIZE = 5;
    static final int KEEP_ALIVE_SECONDS = 3;
    static ThreadPoolExecutor threadPoolExecutor = null;
    static Schedulers schedulers = new Schedulers();
    static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };

    public static SchedulerInterface newThread() {
        threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), sThreadFactory);
        return schedulers;
    }

    @Override
    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }
}
