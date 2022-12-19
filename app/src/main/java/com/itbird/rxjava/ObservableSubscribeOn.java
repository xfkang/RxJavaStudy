package com.itbird.rxjava;

import android.util.Log;

/**
 * Created by itbird on 2022/12/16
 */
public class ObservableSubscribeOn<T> extends Observable<T> {
    Observable<T> observable;
    SchedulerInterface schedulers;
    private String TAG = ObservableSubscribeOn.class.getSimpleName();

    public <t> ObservableSubscribeOn(Observable<T> observable, SchedulerInterface schedulers) {
        this.observable = observable;
        this.schedulers = schedulers;
    }

    @Override
    protected void subscribeActual(Observer observer) {
        Log.d(TAG, "Thread.currentThread().getName() = " + Thread.currentThread().getName());
        schedulers.execute(new SubscribeOnTask(observable, observer));
    }

    @Override
    protected T call() {
        return observable.call();
    }

    private class SubscribeOnTask implements Runnable {
        Observable<T> observable;
        Observer observer;

        public SubscribeOnTask(Observable<T> observable, Observer observer) {
            this.observable = observable;
            this.observer = observer;
        }

        @Override
        public void run() {
            Log.d(TAG, "Thread.currentThread().getName() = " + Thread.currentThread().getName());
            observable.subscribe(observer);
        }
    }
}
