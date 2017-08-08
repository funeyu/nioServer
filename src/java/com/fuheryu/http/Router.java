package com.fuheryu.http;

import com.fuheryu.Utils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fuheryu.controller.ControllerMethod;

/**
 * Created by fuheyu on 2017/8/1.
 */
public class Router {

    private static final Map<String, ControllerMethod> routersMapping =
            new ConcurrentHashMap<String, ControllerMethod>();

    static {
        // 获取routerMapping的包配置
        String routerMappingConfig = Utils.getProperty("routerMapping");
        List<Class> controllers = Utils.getClassesPageName(routerMappingConfig);

        for(Class cla : controllers) {
            // 查找routeMapping的注解项
            List<ControllerMethod> methods = Utils.getAnnotedControllers(cla);

            for(ControllerMethod cm : methods) {
                routersMapping.put(cm.getRouterString(), cm);
            }
        }
    }

    public static byte[] use(HTTPContext httpContext) {
        String url = httpContext.getRequest().getUrl();
        ControllerMethod cm = routersMapping.get(url);

        if(cm == null) {
            return "error router".getBytes();
        }

        return cm.callee(httpContext).getBytes();
    }

}
