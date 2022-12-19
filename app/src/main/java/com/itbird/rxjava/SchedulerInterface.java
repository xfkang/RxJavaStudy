package com.itbird.rxjava;

/**
 * Created by itbird on 2022/12/16
 */
public interface SchedulerInterface {
    void execute(Runnable runnable);
}
