package com.fuheryu.db.fupool;

/**
 * Created by fuheyu on 2017/8/17.
 */
public class NoConnectionTypeException extends RuntimeException {
    private String message;

    public NoConnectionTypeException(String message) {
        super(message);
        this.message = message;
    }

}
