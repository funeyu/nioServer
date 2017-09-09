package com.fuheryu.core.handler;

/**
 * Created by fuheyu on 2017/9/9.
 */
public interface EventFactory<T> {

    T newInstance(int i);
}
