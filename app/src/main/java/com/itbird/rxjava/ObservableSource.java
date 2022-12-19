package com.itbird.rxjava;

/**
 * Created by itbird on 2022/12/14
 */
public interface ObservableSource<T> {
    void subscribe(Observer<? super T> observer);
}
