package com.fuheryu.core;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fuheyu on 2017/8/10.
 */
public class ServiceFactory {

    private static final ConcurrentHashMap<Class, Object> containers = new ConcurrentHashMap<Class, Object>();

    static {
        String serviceConfig = Utils.getProperty("service");
        List<Class> controllers = Utils.getCalssesInDir(serviceConfig, ServiceFactory.class);

        for(Class c : controllers) {
            try {
                containers.put(c, c.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object one (Class clazz) {

        return containers.get(clazz);
    }
}
