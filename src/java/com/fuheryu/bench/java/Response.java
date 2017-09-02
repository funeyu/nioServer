package com.fuheryu.bench.java; /**
 * Created by fuheyu on 2017/9/2.
 */

import java.io.*;
import java.util.Date;

/**
 * @Description: represents Http Response
 * @author: jitianyu
 * @time: Aug 24, 2016
 *        6:38:48 AM
 */
public class Response {
    private String startLine;
    private String header;
    private byte[] content;

    //start-line
    private String version;
    private String status;
    private String reasonPhase;

    //header
    private String date;
    private String server;
    private String contentType;
    private int contentLength;

    public Response( String p_version, String p_requestURL, String p_status, byte[] p_content ){
        version = p_version;
        status = p_status;
        //reasonPhase need to check
        switch(status){
            case "200" : reasonPhase ="OK";
                content = p_content;
                break;
            case "404" : reasonPhase = "File Not Found";
                content = "File Not Found".getBytes();
                break;
            case "501" : reasonPhase = "Not Implemented";
                content = "Not Implemented".getBytes();
                break;
        }
        reasonPhase = "OK";
        startLine = version + ' ' + status + ' ' + reasonPhase + "\r\n";

        date = new Date().toString();
        server = "Jitianyu's Server";
        contentType = guessContentType( p_requestURL );

        contentLength = content.length;

        //notice: there is \r\n\r\n indicating the end of  Http header
        header = "Date: " + date + "\r\n" +
                "Server: " + server + "\r\n" +
                "Content-Length: " + contentLength + "\r\n" +
                "Content-Type: " + contentType + "\r\n\r\n";
    }
    public String toString(){
        return startLine + header + content;
    }

    public void writeResponse( OutputStream _out ){
        BufferedOutputStream raw = null;
        OutputStreamWriter out = null;
        try {
            raw = new BufferedOutputStream(_out);
            out = new OutputStreamWriter(raw ,"UTF-8");
            //out = new OutputStreamWriter(raw ,"ASCII");
        } catch (UnsupportedEncodingException e) {
            System.out.println( "Response().writeResponse() throws a UnsupportedEncodingException" );
            System.exit(0);
        }
        try {
            out.write(startLine);
            out.write(header);
            System.out.println(startLine);
            System.out.println(header);
            out.flush();

            raw.write(content);
            raw.flush();

            //notice: closing the OutputStream will close the connection too
            //but if not call the close(), client will received nothing, why?
            //maybe because of the tcp buffer? no;
            //yeah i found reason: because the end of header is \r\n\r\n, i wrote it wrong;
            //so when not call close() client will not received the end of header, so there is nothing to show;
            //when call out.close(), the raw will also be closed too.
            out.close();
            raw.close();
        } catch (IOException e) {
            System.out.println( "Response().writeResponse() throws an IOException" );
            System.err.print(e);
            System.exit(0);
        }
    }

    public String guessContentType( String requestURL ){
        int pos = requestURL.lastIndexOf(".");
        if( pos == -1 )return "text/plain";

        String extendName = requestURL.substring(pos, requestURL.length());
        switch(extendName){
            case ".html"  :return "text/html";
            case ".htm"   :return "text/html";
            case ".txt"   :return "text/plain";
            case ".java"  :return "text/plain";
            case ".gif"   :return "image/gif";
            case ".class" :return "application/octet-stream";
            case ".jpeg"  :return "image/jpeg";
            case ".jpg"   :return "image/jpeg";
            default       :return "text/plain";
        }
    }
}
