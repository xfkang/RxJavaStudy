package com.itbird.myapplication.rxjava;

/**
 * Created by itbird on 2022/9/14
 */
public interface Observerr<T> {
    void onNext(T t);

    void onError(Throwable throwable);

    void onComplete();

    void onSubscribe();
}
