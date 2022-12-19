package com.itbird.rxjava;

/**
 * Created by itbird on 2022/12/15
 */
public interface Function<T, R> {
    R apply(T value) throws Exception;
}
