package com.itbird.myapplication.rxjava;

/**
 * Created by itbird on 2022/9/14
 */
public abstract class Observable<T> implements ObservableSource<T> {

    public static <T> Observable<T> just(T t) {
        return onAssembly(new ObservableJust<T>(t));
    }

    private static <T> Observable onAssembly(Observable<T> tObservableJust) {
        return tObservableJust;
    }

    @Override
    public void subscribe(Observerr<T> observer) {
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observerr<T> observer);

    public final <R> Observable map(Function<T, R> function) {
        return onAssembly(new ObservableFunction<T, R>(this, function));
    }

    public <T> Observable subscribeOn(Scheduler schedulers) {
        return onAssembly(new ObservableSubscribeOn<T>(this, schedulers));
    }

    public <T> Observable observeOn(Scheduler scheduler) {
        return onAssembly(new ObservableObserveOn<T>(this, scheduler));
    }
}
