package com.fuheryu.bench.java;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by fuheyu on 2017/9/2.
 */
public class HttpServer extends SimpleChannelInboundHandler<HttpObject> {

    public static class HttpServerInitializer extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();

            pipeline.addLast(new HttpServerCodec());
            pipeline.addLast(new HttpServer());
        }
    }


    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof LastHttpContent) {
            ByteBuf content = Unpooled.copiedBuffer("Hello World.", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, content.readableBytes());
            ctx.write(response);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    public static void main(String[] args) {
        EventLoopGroup eventLoop = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(eventLoop)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpServerInitializer())
                    .channel(NioSctpServerChannel.class);

            Channel ch = null;
            try {
                ch = bootstrap.bind(8999).sync().channel();
                ch.closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } finally {

        }

    }
}
