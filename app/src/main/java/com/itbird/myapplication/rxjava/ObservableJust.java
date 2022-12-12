package com.itbird.myapplication.rxjava;

/**
 * Created by itbird on 2022/9/14
 */
public class ObservableJust<T> extends Observable<T> {
    T value;

    public ObservableJust(T t) {
        value = t;
    }

    @Override
    protected void subscribeActual(Observerr<T> observer) {
        ScalarDisposable disposable = new ScalarDisposable(value, observer);
        observer.onSubscribe();
        disposable.run();
    }

    private class ScalarDisposable<T> {
        T value;
        Observerr<T> observer;

        public ScalarDisposable(T value, Observerr<T> observer) {
            this.observer = observer;
            this.value = value;
        }


        public void run() {
            observer.onNext(value);
            observer.onComplete();
        }
    }
}
