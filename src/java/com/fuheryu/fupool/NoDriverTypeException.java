package com.fuheryu.fupool;

/**
 * Created by fuheyu on 2017/8/17.
 */
public class NoDriverTypeException extends RuntimeException {
    private String msg;

    public NoDriverTypeException(String message) {
        super(message);
        msg = message;
    }

}
