package com.fuheryu.controller;

import com.fuheryu.http.HTTPContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by fuheyu on 2017/8/6.
 */
    public class ControllerMethod {

    private Method method;

    private Class caller;

    private String routerString;

    private String httpMethod;

    private ControllerMethod (){}

    public static ControllerMethod init (Method method, Class caller, String routerString, String httpMethod){

        ControllerMethod cm = new ControllerMethod();
        cm.setMethod(method);
        cm.setCaller(caller);
        cm.setRouterString(routerString);
        cm.setHttpMethod(httpMethod);

        return cm;
    }

    public String callee(HTTPContext httpContext) {
        String result = "";
        try {
            result = (String) this.method.invoke(this.caller.newInstance(), httpContext);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setCaller(Class caller) {
        this.caller = caller;
    }

    public void setRouterString(String routerString) {
        this.routerString = routerString;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Method getMethod() {
        return method;
    }

    public Class getCaller() {
        return caller;
    }

    public String getRouterString() {
        return routerString;
    }

    public String getHttpMethod() {
        return httpMethod;
    }
}
