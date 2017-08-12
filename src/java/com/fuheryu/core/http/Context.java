package com.fuheryu.core.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fuheyu on 2017/8/12.
 */
public class Context {

    private Request req;

    private Response res;

    private final Map<String, String> attributes = new HashMap<String, String>();

    private Context() {}

    public static Context init(Request req, Response res) {
        Context context = new Context();
        context.setReq(req);
        context.setRes(res);

        return context;
    }


    public void setReq(Request req) {
        this.req = req;
    }

    public void setRes(Response res) {
        this.res = res;
    }

    public Request getReq() {
        return req;
    }

    public Response getRes() {
        return res;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}
