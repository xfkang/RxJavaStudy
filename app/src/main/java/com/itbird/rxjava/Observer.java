package com.itbird.rxjava;

/**
 * Created by itbird on 2022/12/14
 */
public interface Observer<T> {
    void onSubscribe();

    void onError(Throwable e);

    void onComplete();

    void onNext(T value);
}
