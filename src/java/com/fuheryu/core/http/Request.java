package com.fuheryu.core.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fuheyu on 2017/8/12.
 */
public class Request {

    private String method;

    private String url;

    private String httpVersion;

    private Map<String, String> cookies;

    private Map<String, String> params;

    private List<String> accept;

    private List<String> acceptEncoding;

    private List<String> acceptLanguage;

    // 存储简单的key value的 attribute
    private final Map<String, String> attributes = new HashMap<String, String>();

    public static Request init(){

        return new Request();
    }

    // 简单设置request的属性值，如host, connection
    public void setAttribute(String name, String value) {
        attributes.put(name, value);
    }
    // 获取简单的request属性，如上
    public String getAttribue(String name) {
        return attributes.get(name);
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getHttpVersion() {return httpVersion;}

    public Map<String, String> getCookies() {
        return cookies;
    }

    public String getCookie(String name) {

        return cookies.get(name);
    }

    public void setHttpVersion(String version) {
        this.httpVersion = version;
    }

    public void setCookies(HashMap cookieMap) {
        this.cookies = cookieMap;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public List<String> getAccept() {
        return accept;
    }

    public List<String> getAcceptEncoding() {
        return acceptEncoding;
    }

    public List<String> getAcceptLanguage() {
        return acceptLanguage;
    }
}
