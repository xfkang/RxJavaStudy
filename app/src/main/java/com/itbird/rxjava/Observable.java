package com.itbird.rxjava;


/**
 * Created by itbird on 2022/12/14
 */
public abstract class Observable<T> implements ObservableSource<T> {

    public static <T> Observable just(T s) {
        return new ObservableJust<T>(s);
    }

    protected abstract void subscribeActual(Observer<? super T> observer);

    @Override
    public void subscribe(Observer<? super T> observer) {
        subscribeActual(observer);
    }

    public <R> Observable map(Function<T, R> function) {
        return new ObservableMap<T, R>(this, function);
    }

    protected abstract T call();

    public <T> Observable subscribeOn(SchedulerInterface schedulers) {
        return new ObservableSubscribeOn<T>((Observable<T>) this, schedulers);
    }

    public <T> Observable observeOn(SchedulerInterface schedulers) {
        return new ObservableObserveOn<T>((Observable<T>) this, schedulers);
    }
}
