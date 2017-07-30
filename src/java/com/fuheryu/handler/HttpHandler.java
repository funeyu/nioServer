package com.fuheryu.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpRetryException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fuheyu on 2017/7/29.
 */
public class HttpHandler implements Handler {

    private ExecutorService excutors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private int count = 0;
    private final static Charset charset = Charset.forName("UTF-8");
    private final static CharsetEncoder encoder = charset.newEncoder();

    private HttpHandler(){}


    public void onRead(final SelectionKey selectionKey) {

        excutors.execute(new Runnable() {

            public void run() {
                try {
                    SocketChannel sc = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int readed = 0;
                    byte[] bytes;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    while((readed = sc.read(buffer)) > 0) {

                        buffer.flip();
                        bytes = new byte[readed];
                        buffer.get(bytes, 0 , readed);
                        bos.write(bytes);
                        buffer.clear();
                    }

                    // 这里如果是client端直接关闭，bos.size()长度会小于1
                    if(bos.size() < 1) {

                        sc.close();
                        return;
                    }
                    System.out.println(new String(bos.toByteArray()));


                    HTTPResponse response = new HTTPResponse();
                    response.setContent("hello".getBytes());
                    response.addDefaultHeaders();

                    response.send(sc);
                    selectionKey.cancel();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static Handler createHander() {
        Handler h = new HttpHandler();
        return h;
    }

    private static class HTTPResponse {

        private String version = "HTTP/1.1";
        private int responseCode = 200;
        private String responseReason = "ok";
        private Map<String, String> headers = new LinkedHashMap<String, String>();
        private byte[] content;

        private HTTPResponse() {}

        private void addDefaultHeaders() {

            headers.put("Date", new Date().toString());
            headers.put("Server", "NIO");
            headers.put("connection", "close");
            headers.put("Content-Length", Integer.toString(content.length));
        }

        private void writeLine(String line, SocketChannel channel) throws IOException {
            channel.write(encoder.encode(CharBuffer.wrap(line + "\r\n")));
        }

        private void setContent(byte[] content) {

            this.content = content;
        }

        private void send(SocketChannel channel) {

            try {
                writeLine(version + " " + responseCode + " " + responseReason, channel);

                for(Map.Entry<String, String> header : headers.entrySet()) {
                    writeLine(header.getKey() + ": " + header.getValue(), channel);
                }
                writeLine("", channel);

                channel.write(ByteBuffer.wrap(content));
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class HTTPRequest {

    }
}
