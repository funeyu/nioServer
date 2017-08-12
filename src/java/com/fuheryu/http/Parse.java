package com.fuheryu.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
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
            httpContext.setRequest(req);
            System.out.println("rawString::::" + rawString);

            for(String tmString = reader.readLine(); tmString != null; tmString = reader.readLine()) {
                if(tmString.indexOf("Cookie:") > -1) {
                    cookies(tmString, httpContext);
                }
                else if(tmString.indexOf("User-Agent:") > -1) {
                    userAgent(tmString, httpContext);
                }
            }
            return httpContext;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String[] methodAndUrl(String s) {

        String[] spliteds = s.split("\\s+");


        return new String[]{spliteds[0], spliteds[1]};
    }

    private static void cookies(String s, HTTPContext httpContext) {

        Map<String, String> cookieMap = new HashMap<String, String>();
        // cookie String 类似于：
        // Cookie: charger.mt.open=wechatInfo.openId;
        String[] cookiess = s.substring(s.indexOf(": ") + 2, s.length()).split("; ");

        for(String c : cookiess) {
            if(c.indexOf("=") > -1) {
                String[] slices = c.split("=");
                cookieMap.put(slices[0], slices[1].substring(0, slices[1].length() - 1));
            }
        }


        httpContext.getRequest().setCookies(cookieMap);
    }

    private static void userAgent(String s, HTTPContext httpContext) {

    }


}
