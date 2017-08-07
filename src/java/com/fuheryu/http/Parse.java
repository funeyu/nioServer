package com.fuheryu.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

/**
 * Created by fuheyu on 2017/8/1.
 */
public class Parse {

    public static HTTPContext parse(String rawString, HTTPContext httpContext) {
        BufferedReader reader = new BufferedReader(new StringReader(rawString));

        try {
            String methodInfo = reader.readLine();
            String[] methodUrl = methodAndUrl(methodInfo);
            System.out.println(methodUrl[0] + ":" + methodUrl[1]);

            HTTPRequest req = HTTPRequest.init(methodUrl[0], null, methodUrl[1]);
            // 初始化一个res对象，并且将其content设置为null
            HTTPResponse res = HTTPResponse.init(null);

            httpContext.setResponse(res);
            httpContext.setRequest(req);


            return httpContext;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Cookie[] cookies(String s, HTTPContext httpContext) {


        return null;
    }

    private static String[] methodAndUrl(String s) {

        String[] spliteds = s.split("\\s+");


        return new String[]{spliteds[0], spliteds[1]};
    }


}
