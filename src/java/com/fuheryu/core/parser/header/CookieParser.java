package com.fuheryu.core.parser.header;

import com.fuheryu.core.http.Context;

import java.util.HashMap;


/**
 * Created by fuheyu on 2017/8/12.
 */
public class CookieParser extends ParserBase{

    public CookieParser(ParserBase next) {
        super(next);
    }

    @Override
    public void parse(Context context, HeaderMap headerMap) {
        HashMap<String, String> cookieMap = new HashMap<>();
        String cookie = headerMap.get("cookie");
        String[] cookies = cookie.split("; ");

        for(String s : cookies) {
            String[] slices = s.split("=");
            cookieMap.put(slices[0], slices[1]);
        }

        context.getReq().setCookies(cookieMap);

        super.next(context, headerMap);
    }
}
