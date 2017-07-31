package com.fuheryu.http;

import java.util.Map;

/**
 * Created by fuheyu on 2017/8/1.
 */
public class HTTPRequest {

    private String method;

    private String url;

    private Map<String, String>params;

    private HTTPRequest(){}

    public HTTPRequest init(String method, Map<String, String> params, String url) {

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

    public String getMethod() {
        return method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getUrl() {
        return url;
    }

    public static void main(String[] args) {

        String test = "GET / HTTP/1.1\n" +
                "cache-control: no-cache\n" +
                "Postman-Token: a8f61615-9ad0-4893-b844-2e295507df1a\n" +
                "User-Agent: PostmanRuntime/3.0.11-hotfix.2\n" +
                "Accept: */*\n" +
                "Host: localhost:8099\n" +
                "cookie: JSESSIONID=BD7E0D0385FA98D6BBF2BBC1F45F475C\n" +
                "accept-encoding: gzip, deflate\n" +
                "Connection: keep-alive";

        Parse.parse(test);
    }
}
