package com.fuheryu.bench.java; /**
 * Created by fuheyu on 2017/9/2.
 */

import java.io.*;

/**
 * @Description: represents HttpRequest
 * @author: jitianyu
 * @time: Aug 24, 2016
 *        6:28:25 AM
 */
public class Request {

    private String startLine;
    private String header;

    //start-line
    private String method;
    private String requestURL;
    private String version;

    //header
    private String host;

    public Request( InputStream inn ){
        InputStreamReader in = null;
        try {
            in = new InputStreamReader( new BufferedInputStream(inn),"UTF-8" );
        } catch (UnsupportedEncodingException e1) {
            System.out.println(" Request() throws a UnsupportedEncodingException ");
            System.exit(0);
        }
        StringBuffer sb = new StringBuffer();
        try{
            while(true){
                //when TCP connection is established, read() method will block after reading the Http header
                // if there is no more data from client browser
                int res = in.read();
                //notice: break is used but not return
                //socket input will return -1 only when connection is closed
                //so it usually will block
                if( res == -1 ){
                    break;
                }
                System.out.write((char)res);
                sb.append((char)res);
            }
        }catch( IOException e ){
            //when read the end of input( namely read return -1 ), an IOEception will be thrown,
            //So, exit() should not be used in catch() clause
            //System.out.println( "read()(Request Class) throws an exception" );
        }
        String request = sb.toString();

        String[] requestStr = request.split("\r\n|\\s");
        this.method = requestStr[0];
        this.requestURL = requestStr[1];
        this.version = requestStr[2];

        for( int i = 0; i < requestStr.length; i++){
            if(requestStr[i].equals("Host:"))
                this.host = requestStr[i+1];
        }

        this.startLine = method + ' ' + requestURL + ' ' + version + "\r\n";
        this.header = "Host: " + host + "\r\n";
    }

    public String getStartLine() {
        return startLine;
    }

    public void setStartLine(String startLine) {
        this.startLine = startLine;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

}
