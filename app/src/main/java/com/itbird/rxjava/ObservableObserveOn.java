package com.itbird.rxjava;


import android.os.Handler;
import android.os.Looper;


/**
 * Created by itbird on 2022/12/16
 */
public class ObservableObserveOn<T> extends Observable<T> {
    Observable<T> observable;
    SchedulerInterface schedulers;


    public ObservableObserveOn(Observable<T> observable, SchedulerInterface schedulers) {
        this.observable = observable;
        this.schedulers = schedulers;
    }


    @Override
    protected void subscribeActual(Observer observer) {
        observable.subscribe(new ObservableObserveOnS(schedulers, observable, observer));
    }


    @Override
    protected T call() {
        return observable.call();
    }


    private class ObservableObserveOnS<T> implements Runnable, Observer<T> {
        Observable<T> observable;
        Observer observer;
        SchedulerInterface schedulers;
        T value;


        public ObservableObserveOnS(SchedulerInterface schedulers, Observable<T> observable, Observer observer) {
            this.observable = observable;
            this.observer = observer;
            this.schedulers = schedulers;
        }


        @Override
        public void run() {
            try {
                observer.onNext(value);
                observer.onComplete();
            } catch (Exception e) {
                observer.onError(e);
            }
        }


        @Override
        public void onSubscribe() {
            schedule();
        }


        @Override
        public void onError(Throwable e) {
            schedule();
        }


        @Override
        public void onComplete() {
            schedule();
        }


        @Override
        public void onNext(T value) {
            this.value = value;
            schedule();
        }


        void schedule() {
            schedulers.execute(this);
        }
    }
}