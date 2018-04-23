package com.fuheryu.futty;

import com.fuheryu.futty.event.MessageEvent;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {
        ChannelPipeLine channelPipeLine = new DefaultPipeLine();
        channelPipeLine.addHandler(new AbstractChannelHandler() {
            @Override
            public void received(SocketChannel socketChannel) {

            }

            @Override
            public void opened(SocketChannel socketChannel) {

            }

            @Override
            public void connected(SocketChannel socketChannel) {

            }

            @Override
            public void onHandle(MessageEvent messageEvent) {
                System.out.println("on Handle");
                ByteBuffer byteBuffer = ByteBuffer.allocate(16);
                byteBuffer.put("nnnn\n".getBytes());

                byteBuffer.flip();
                try {
                    ((SocketChannel)messageEvent.getChannel()).write(byteBuffer);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        NioServerBoss boss = new NioServerBoss(null, channelPipeLine);
        boss.bind(new InetSocketAddress(9999));
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(boss);
    }

}
