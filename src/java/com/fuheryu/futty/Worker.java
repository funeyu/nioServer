package com.fuheryu.futty;

/**
 * Created by fuheyu on 2018/3/28.
 */
public interface Worker extends Runnable {

    void executeInIoThread(Runnable task);
}
