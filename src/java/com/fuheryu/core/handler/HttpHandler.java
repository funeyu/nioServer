package com.fuheryu.core.handler;

import com.fuheryu.core.buffer.ChannelBuffer;
import com.fuheryu.core.connection.Connection;
import com.fuheryu.core.connection.ConnectionFactory;
import com.fuheryu.core.http.Context;
import com.fuheryu.core.http.Request;
import com.fuheryu.core.http.Response;
import com.fuheryu.core.parser.header.CookieParser;
import com.fuheryu.core.parser.header.HeaderMap;
import com.fuheryu.core.parser.header.ParserBase;
import com.fuheryu.core.parser.header.UrlParser;
import com.fuheryu.core.http.Router;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fuheyu on 2017/7/29.
 */
public class HttpHandler implements Handler {

    private int count = 0;
    private final  Charset charset = Charset.forName("UTF-8");
    private final  CharsetEncoder encoder = charset.newEncoder();
    private final static int HEADER_BUFFFER_SIZE = 8192;
    private SelectionKey selectionKey;

    // header解析链
    private final static ParserBase headerParserChain = new CookieParser(new UrlParser(null));

    private HttpHandler(SelectionKey key){

        this.selectionKey = key;
    }

    /*
        从request中提取出 header的数组

     */
    private ArrayList<String> extractHeader(ChannelBuffer cb) {
        ArrayList<String> headers = new ArrayList<>();
        String line;
        while((line = cb.nextLine()) != null && !line.trim().isEmpty()) {

            headers.add(line);
        }

        return headers;
    }

    private void parseHeader(Context context, ArrayList<String> headers) {

        HeaderMap headerMap = HeaderMap.init();
        headerMap.fill(headers);

        headerParserChain.next(context, headerMap);
    }

    @Override
    public void onRead() {

        try {
            SocketChannel sc = (SocketChannel) selectionKey.channel();

            long connectionId = (long)selectionKey.attachment();
            Connection connection = ConnectionFactory.getById("Connection_" + connectionId);

            ChannelBuffer cb;
            if(connection != null) {
                cb = connection.getChannelBuffer();
            } else {
                connection = ConnectionFactory.init(HEADER_BUFFFER_SIZE, connectionId, selectionKey);
            }

            cb = connection.getChannelBuffer();

            if(cb.read() < 1) {  // 关闭channel
                connection.close();
                return;
            }

            cb.initReader();     //初始化reader,ready for parserHeader
            ArrayList<String> headers = extractHeader(cb);

            // 初始化request, response, context
            Request req = Request.init();
            Response res = Response.init(connection);
            Context context = Context.init(req, res);

            parseHeader(context, headers);

            byte[] results = Router.use(context);


            context.getRes().setContent(results);
            if(context.getReq().getAccept() != null) {
                context.getRes().sendJSON();
            } else {
                context.getRes().sendJSON();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Handler createHander(SelectionKey key) {
        Handler h = new HttpHandler(key);
        return h;
    }

}
