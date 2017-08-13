package com.fuheryu.core.parser.header;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fuheyu on 2017/8/12.
 * 将header的内容：
 *   GET /hello HTTP/1.1
     cache-control: no-cache
     Postman-Token: 819dc102-19d7-4668-8678-86d8c638b988
     User-Agent: PostmanRuntime/6.2.5
     Accept:*
     Host: localhost:8099
     cookie: JSESSIONID=BD7E0D0385FA98D6BBF2BBC1F45F475C
     accept-encoding: gzip, deflate
     Connection: keep-alive

 * 存成map格式数据
 */
public class HeaderMap extends HashMap<String, String>{

    public static HeaderMap init() {
        return new HeaderMap();
    }

    /*
        存储header首行的内容
     */
    private void dealWithFirstLine(String firstLine) {
        if(firstLine.startsWith("GET")) {
            this.put("GET", firstLine);
        } else if (firstLine.startsWith("POST")) {
            this.put("POST", firstLine);
        } else if(firstLine.startsWith("PUT")) {
            this.put("PUT", firstLine);
        } else if(firstLine.startsWith("HEAD")) {
            this.put("HEAD", firstLine);
        }
    }

    public HeaderMap fill (ArrayList<String> lines) {

        dealWithFirstLine(lines.remove(0));

        for(String line : lines) {
            String[] splices = line.split(": ");
            this.put(splices[0].toLowerCase(), splices[1].toLowerCase());
        }

        return this;
    }

    public static void main(String[] args) {
        String line = "Connection: keep-alive";
        String[] ls = line.split(": ");
        for(String s : ls) {
            System.out.println(s.toLowerCase());
        }
    }

}
