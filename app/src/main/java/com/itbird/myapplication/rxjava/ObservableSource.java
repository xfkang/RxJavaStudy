package com.itbird.myapplication.rxjava;


/**
 * Created by itbird on 2022/9/14
 */
public interface ObservableSource<T> {
    void subscribe(Observerr<T> observer);
}
