package com.fuheryu.fudao;

/**
 * Created by fuheyu on 2017/8/19.
 */
public class NotExtendedException extends RuntimeException{

    private String msg;

    public NotExtendedException(String msg) {

        super(msg);
        this.msg = msg;
    }

}
