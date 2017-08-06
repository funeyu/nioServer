package com.fuheryu.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

/**
 * Created by fuheyu on 2017/8/1.
 */
public class Parse {

    public static String parse(String rawString) {
        BufferedReader reader = new BufferedReader(new StringReader(rawString));

        try {
            String methodInfo = reader.readLine();
            String[] methodUrl = methodAndUrl(methodInfo);
            System.out.println(methodUrl[0] + ":" + methodUrl[1]);

            String result = Router.use(methodUrl[1]);

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String[] methodAndUrl(String s) {

        String[] spliteds = s.split("\\s+");

        return new String[]{spliteds[0], spliteds[1]};
    }
}
