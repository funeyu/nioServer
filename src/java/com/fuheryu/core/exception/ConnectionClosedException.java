package com.fuheryu.core.exception;

/**
 * Created by fuheyu on 2017/8/13.
 */
public class ConnectionClosedException extends Exception {
    public ConnectionClosedException(String msg) {
        super(msg);
    }
}
