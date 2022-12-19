package com.itbird.rxjava;

/**
 * Created by itbird on 2022/12/15
 */
public class ObservableMap<T, R> extends Observable<T> {
    Function<T, R> function;
    Observable<T> observable;

    public ObservableMap(Observable<T> observable, Function<T, R> function) {
        this.observable = observable;
        this.function = function;
    }

    @Override
    protected void subscribeActual(Observer observer) {
        observable.subscribe(new ObserverMap(observer, function));
    }

    @Override
    protected T call() {
        return observable.call();
    }

    private class ObserverMap<T, R> implements Observer<T> {
        Observer<R> observer;
        Function<T, R> function;

        public ObserverMap(Observer<R> observer, Function<T, R> function) {
            this.observer = observer;
            this.function = function;
        }

        @Override
        public void onSubscribe() {
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }

        @Override
        public void onNext(T value) {
            try {
                R r = function.apply(value);
                observer.onNext(r);
                observer.onComplete();
            } catch (Exception e) {
                observer.onError(e);
            }
        }
    }
}
