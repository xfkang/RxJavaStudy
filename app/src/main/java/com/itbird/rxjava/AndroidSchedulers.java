package com.itbird.rxjava;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by itbird on 2022/12/16
 */
public class AndroidSchedulers implements SchedulerInterface {
    private static Handler handler;
    private static AndroidSchedulers mInstance = new AndroidSchedulers();

    public static SchedulerInterface mainThread() {
        handler = new Handler(Looper.getMainLooper());
        return mInstance;
    }

    @Override
    public void execute(Runnable runnable) {
        handler.post(runnable);
    }
}
