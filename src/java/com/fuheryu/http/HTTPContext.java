package com.fuheryu.http;


/**
 * Created by fuheyu on 2017/8/1.
 */
public class HTTPContext {

    private HTTPRequest req;

    private HTTPResponse res;

    private HTTPContext() {}

    public static HTTPContext init() {

        return new HTTPContext();
    }

    public static HTTPContext init(HTTPRequest request, HTTPResponse response) {
        HTTPContext context = new HTTPContext();
        context.setRequest(request);
        context.setResponse(response);

        return context;
    }

    public void setRequest(HTTPRequest request) {
        this.req = request;
    }

    public void setResponse(HTTPResponse response) {
        this.res = response;
    }

    public HTTPRequest getRequest() {
        return req;
    }

    public HTTPResponse getResponse() {
        return res;
    }
}
