package com.fuheryu.http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.nio.charset.CharsetEncoder;

/**
 * Created by fuheyu on 2017/8/1.
 */
public class HTTPResponse {
    private String version = "HTTP/1.1";
    private int responseCode = 200;
    private String responseReason = "ok";
    private Map<String, String> headers = new LinkedHashMap<String, String>();
    private final static Charset charset = Charset.forName("UTF-8");
    private final static CharsetEncoder encoder = charset.newEncoder();
    private byte[] content;

    private SelectionKey sk;

    private SocketChannel sc;


    private HTTPResponse() {}

    public static HTTPResponse init (SelectionKey sk, SocketChannel sc) {
        HTTPResponse response = new HTTPResponse();
        response.setSc(sc);
        response.setSk(sk);

        return response;
    }


    public void send() {

        addDefaultHeaders();
        try {
            writeLine(version + " " + responseCode + " " + responseReason);

            for(Map.Entry<String, String> header : headers.entrySet()) {
                writeLine(header.getKey() + ": " + header.getValue());
            }
            writeLine("");

            sc.write(ByteBuffer.wrap(content));
            sc.close();
            sk.cancel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendJSON() {

        addJSONHeader();
        send();
    }

    private void addDefaultHeaders() {

        headers.put("Date", new Date().toString());
        headers.put("Server", "NIO");
        headers.put("connection", "close");
        headers.put("Content-Length", Integer.toString(content.length));
    }

    private void addJSONHeader() {
        headers.put("Content-Type", "application/json; charset=utf-8");
    }

    private void writeLine(String line) throws IOException {
        sc.write(encoder.encode(CharBuffer.wrap(line + "\r\n")));
    }

    public SelectionKey getSk() {
        return sk;
    }

    public SocketChannel getSc() {
        return sc;
    }

    public void setSk(SelectionKey sk) {
        this.sk = sk;
    }

    public void setSc(SocketChannel sc) {
        this.sc = sc;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {

        this.content = content;
    }

}
