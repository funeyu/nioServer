package com.fuheryu.http;


/**
 * Created by fuheyu on 2017/8/1.
 */
public class HTTPContext {

    private HTTPRequest request;

    private HTTPResponse response;

    private HTTPContext() {}

    public HTTPContext init(HTTPRequest request, HTTPResponse response) {
        HTTPContext context = new HTTPContext();
        context.setRequest(request);
        context.setResponse(response);

        return context;
    }

    public void setRequest(HTTPRequest request) {
        this.request = request;
    }

    public void setResponse(HTTPResponse response) {
        this.response = response;
    }

    public HTTPRequest getRequest() {
        return request;
    }

    public HTTPResponse getResponse() {
        return response;
    }
}
