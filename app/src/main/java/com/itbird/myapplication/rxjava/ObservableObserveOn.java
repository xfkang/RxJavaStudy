package com.itbird.myapplication.rxjava;

/**
 * Created by itbird on 2022/9/15
 */
public class ObservableObserveOn<T> extends Observable<T> {
    Observable tObservable;
    Scheduler scheduler;

    public <T> ObservableObserveOn(Observable<T> tObservable, Scheduler scheduler) {
        this.tObservable = tObservable;
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(Observerr<T> observer) {
        tObservable.subscribe(new ObserveOnObserver<>(observer));
    }

    private class ObserveOnObserver<T> implements Observerr<T>, Runnable {
        final Observerr<T> observer;
        T t;


        public ObserveOnObserver(Observerr<T> observer) {
            this.observer = observer;
        }

        @Override
        public void onNext(T t) {
            this.t = t;
            scheduler.scheduleDirect(this);
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {

        }

        @Override
        public void onSubscribe() {

        }

        @Override
        public void run() {
            observer.onNext(t);
        }
    }
}
