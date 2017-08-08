package com.fuheryu.handler;

import com.alibaba.fastjson.JSON;
import com.fuheryu.http.HTTPContext;
import com.fuheryu.http.Parse;
import com.fuheryu.http.Router;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
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

                    String byteString = new String(bos.toByteArray());
                    HTTPContext httpContext = HTTPContext.init(sc, selectionKey);

                    Parse.parse(byteString, httpContext);

                    byte[] results = Router.use(httpContext);


                    httpContext.getResponse().setContent(results);
                    httpContext.getResponse().send();

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

}
