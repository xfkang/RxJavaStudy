package com.itbird.myapplication.rxjava;

import android.util.Log;

import com.itbird.myapplication.MainActivity;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by itbird on 2022/9/15
 */
public abstract class Scheduler {
    ThreadPoolExecutor threadPoolExecutor;

    abstract void createThreadWorker();

    public void scheduleDirect(Runnable runnable) {
        Log.d(MainActivity.TAG, "Scheduler scheduleDirect1");
        if (threadPoolExecutor == null) {
            createThreadWorker();
        }

        if (runnable == null) {
            return;
        }

        Log.d(MainActivity.TAG, "Scheduler scheduleDirect2");
        threadPoolExecutor.execute(runnable);
    }
}
