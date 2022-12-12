package com.itbird.myapplication.rxjava;

import android.util.Log;

import com.itbird.myapplication.MainActivity;

/**
 * Created by itbird on 2022/9/15
 */
public class ObservableSubscribeOn<T> extends Observable<T> {
    Observable tObservable;
    Scheduler schedulers;

    public <T> ObservableSubscribeOn(Observable<T> tObservable, Scheduler schedulers) {
        this.tObservable = tObservable;
        this.schedulers = schedulers;
    }

    @Override
    protected void subscribeActual(Observerr<T> observer) {
        schedulers.scheduleDirect(new SubscribeTask(observer));
    }

    public class SubscribeTask implements Runnable {
        Observerr<T> observer;

        public SubscribeTask(Observerr<T> observer) {
            this.observer = observer;
        }

        @Override
        public void run() {
            Log.d(MainActivity.TAG, "SubscribeTask RUN");
            tObservable.subscribe(observer);
        }
    }
}
