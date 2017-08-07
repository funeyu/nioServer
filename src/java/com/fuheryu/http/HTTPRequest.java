package com.fuheryu.http;

import java.util.Map;

/**
 * Created by fuheyu on 2017/8/1.
 */
public class HTTPRequest {

    private String method;

    private String url;

    private Map<String, String>params;

    private Map<String, String>cookies;

    private HTTPRequest(){}

    public static HTTPRequest init(String method, Map<String, String> params, String url) {

        HTTPRequest request = new HTTPRequest();
        request.setMethod(method);
        request.setParams(params);
        request.setUrl(url);

        return request;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCookies(Map<String, String> cookies) { this.cookies = cookies; }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getCookies() { return cookies; }

}
