package com.fuheryu.core.parser.header;

import com.fuheryu.core.http.Context;

/**
 * Created by fuheyu on 2017/8/12.
 */
public class UrlParser extends ParserBase {

    @Override
    public void parse(Context context, HeaderMap headerMap) {
        String url;

        if((url = headerMap.get("GET")) != null) {
            String[] slices = url.split(" ");
            context.getReq().setMethod(slices[0]);
            context.getReq().setUrl(slices[1]);
            context.getReq().setHttpVersion(slices[2]);
        }
    }
}
