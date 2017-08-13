package com.fuheryu.core.http;

import com.fuheryu.core.Utils;
import com.fuheryu.core.connection.Connection;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by fuheyu on 2017/8/12.
 */
public class Response {
    private String version = "HTTP/1.1";
    private int responseCode = 200;
    private String responseReason = "ok";
    private Map<String, String> headers = new LinkedHashMap<String, String>();
    private final static Charset charset = Charset.forName("UTF-8");
    private final static CharsetEncoder encoder = charset.newEncoder();
    private byte[] content;

    private Connection conn;


    private Response() {}

    public static Response init (Connection conn) {
        Response response = new Response();

        return response;
    }


    public void send() {

        addDefaultHeaders();

        ArrayList<ByteBuffer> buffersList = new ArrayList<>();
        try {
            buffersList.add(writeLine(version + " " + responseCode + " " + responseReason));

            for(Map.Entry<String, String> header : headers.entrySet()) {
                buffersList.add(writeLine(header.getKey() + ": " + header.getValue()));
            }
            buffersList.add(writeLine(""));
            buffersList.add(ByteBuffer.wrap(content));

            ByteBuffer result = Utils.concat(buffersList);

            conn.write(result);
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

    private ByteBuffer writeLine(String line) throws IOException {
        return encoder.encode(CharBuffer.wrap(line + "\r\n"));
    }

    public Connection getConneciton() {
        return this.conn;
    }

    public byte[] getContent() {
        return content;
    }

    public Response setContent(byte[] content) {

        this.content = content;
        return this;
    }
}