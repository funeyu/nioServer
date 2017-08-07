package com.fuheryu.http;

import java.util.Date;

/**
 * Created by fuheyu on 2017/8/7.
 */
public class Cookie {

    private String key;

    private String value;

    private Cookie (String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Cookie init (String key, String value) {

        return new Cookie(key, value);
    }
}
