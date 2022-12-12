package com.itbird.myapplication.rxjava;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by itbird on 2022/9/15
 */
public class HandlerScheduler extends Scheduler {
    private Handler handler;

    @Override
    void createThreadWorker() {
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void scheduleDirect(Runnable runnable) {
        createThreadWorker();
        handler.post(runnable);
    }
}
