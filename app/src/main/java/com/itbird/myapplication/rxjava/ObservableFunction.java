package com.itbird.myapplication.rxjava;

/**
 * Created by itbird on 2022/9/14
 */
public class ObservableFunction<T, R> extends Observable<R> {
    Observable<T> tObservable;
    Function<T, R> function;

    public ObservableFunction(Observable<T> tObservable, Function<T, R> function) {
        this.tObservable = tObservable;
        this.function = function;
    }

    @Override
    protected void subscribeActual(Observerr<R> observer) {
        tObservable.subscribe(new MapObserver<R, T>(observer, function));
    }

    private class MapObserver<R, T> implements Observerr<T> {
        Observerr<R> observer;
        Function<T, R> function;

        public MapObserver(Observerr<R> observer, Function<T, R> function) {
            this.observer = observer;
            this.function = function;
        }


        @Override
        public void onNext(T t) {
            R r = function.apply(t);
            observer.onNext(r);
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
    }
}
