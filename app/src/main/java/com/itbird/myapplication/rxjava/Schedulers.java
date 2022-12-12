package com.itbird.myapplication.rxjava;

/**
 * Created by itbird on 2022/9/14
 */
public class Schedulers {

    public static Scheduler io() {
        return new IOScheduler();
    }

    public static Scheduler mainThread() {
        return new HandlerScheduler();
    }
}
