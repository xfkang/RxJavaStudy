package com.itbird.rxjava;

/**
 * Created by itbird on 2022/12/14
 */
public class ObservableJust<T> extends Observable<T> {
    private final T value;

    public ObservableJust(T t) {
        this.value = t;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        ScalarDisposable<T> sd = new ScalarDisposable<T>(observer, value);
        observer.onSubscribe();
        sd.run();
    }

    @Override
    protected T call() {
        return value;
    }

    private class ScalarDisposable<T> {
        private Observer<? super T> observer;
        private T value;

        public ScalarDisposable(Observer<? super T> observer, T value) {
            this.observer = observer;
            this.value = value;
        }

        public void run() {
            try {
                observer.onNext(value);
                observer.onComplete();
            } catch (Exception e) {
                observer.onError(e);
            }
        }
    }
}
