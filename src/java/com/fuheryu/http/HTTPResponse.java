package com.fuheryu.http;

/**
 * Created by fuheyu on 2017/8/1.
 */
public class HTTPResponse {

    private String content;
    private HTTPResponse() {}

    public static HTTPResponse init (String content) {
        HTTPResponse response = new HTTPResponse();
        response.setContent(content);

        return response;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
