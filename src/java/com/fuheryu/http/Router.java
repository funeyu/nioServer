package com.fuheryu.http;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fuheyu on 2017/8/1.
 */
public class Router {

    private static final Map<String, Controller> routersMapping = new ConcurrentHashMap<String, Controller>();

}
