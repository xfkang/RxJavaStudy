package com.itbird.myapplication.rxjava;

/**
 * Created by itbird on 2022/9/14
 */
public interface Function<T, R> {
    R apply(T t);
}
