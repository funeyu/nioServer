package com.fuheryu.db.fupool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by fuheyu on 2017/8/17.
 */
public class Utils {
    private final static Properties prop = new Properties();

    static {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getProperty(String key) {

        return prop.getProperty(key);
    }
}
