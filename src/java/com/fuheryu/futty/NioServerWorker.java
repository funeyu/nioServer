package com.fuheryu.futty;

import com.fuheryu.futty.future.ChannelFuture;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;

public class NioServerWorker extends AbstractNioWorker{

    private ChannelPipeLine channelPipeLine;
    NioServerWorker(Executor executor) {

        super(executor);
    }

    public void setChannelPipeLine(ChannelPipeLine channelPipeLine) {
        this.channelPipeLine = channelPipeLine;
    }

    @Override
    protected boolean read(SelectionKey k) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        if(k.isReadable()) {
            SocketChannel sc = (SocketChannel) k.channel();
            int count;
            try {
                while((count = sc.read(buffer)) > 0) {
                    buffer.flip();
                    byte[] bytes = new byte[buffer.limit()];
                    buffer.get(bytes);
                    System.out.println("readdd:" + new String(bytes));
                }

                if(count < 0) { // 这里是客户端主动关闭channel
                    sc.close();
                    return false;
                }

                channelPipeLine.handle(sc);
            } catch (IOException e) {

            }
            return true;
        }

        System.out.println("reeeeddd false");
        return false;

    }

    @Override
    protected Runnable createRegisterTask(Channel channel, ChannelFuture channelFuture) {

        try {
            System.out.println("task");
            ((SocketChannel)channel).configureBlocking(false);
            ((SocketChannel)channel).register(selector, SelectionKey.OP_READ);
            System.out.println("socketChannel");
            new Thread(this).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("worker runnable！");
            }
        };
    }

    @Override
    protected void close(SelectionKey k) {

    }

    @Override
    public void shutDown() {

    }
}
