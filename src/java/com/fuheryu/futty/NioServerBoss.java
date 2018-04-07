package com.fuheryu.futty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by fuheyu on 2018/3/28.
 */
public final class NioServerBoss extends AbstractNioSelector implements Boss{

    NioServerBoss(Executor bossExecutor) {super(bossExecutor);}

    private final AtomicBoolean isFirstStarted = new AtomicBoolean(true);

    @Override
    public void shutDown() {

    }

    @Override
    protected Runnable createRegisterTask(Channel channel, ChannelFuture channelFuture) {

        System.out.println("register task");

        return new Runnable() {
            @Override
            public void run() {
                try {
                    if(isFirstStarted.compareAndSet(true, false)) {
                        System.out.println("boss runnable");
                        ServerSocketChannel ssc = (ServerSocketChannel) channel;

                        for(;;) {
                            SocketChannel socketChannel = ssc.accept();
                            socketChannel.configureBlocking(false);
                            if(socketChannel != null) {
                                NioServerWorker worker = NioServerWorkerFactory.next();
                                worker.register(socketChannel, null);
                            }
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    protected void close(SelectionKey k) {

    }

    @Override
    protected void process(Selector selector) throws IOException {
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        if(selectedKeys.isEmpty()) {
            return ;
        }

        for (Iterator<SelectionKey> i = selectedKeys.iterator(); i.hasNext();) {
            SelectionKey k = i.next();
            i.remove();

            ServerSocketChannel channel = (ServerSocketChannel) k.channel();

            register(channel, null);
//            try {
//                SocketChannel acceptedSocket = channel.accept();
//                if(acceptedSocket == null) {
//                    break;
//                }
//            } catch (CancelledKeyException e) {
//                k.cancel();
//                channel.close();
//            }
        }
    }


    private static void registerAcceptedChannel() {

    }

    public void bind(InetSocketAddress inetSocketAddress) {
        if(inetSocketAddress == null) {
            return ;
        }

        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.socket().bind(inetSocketAddress);
            ssc.register(this.selector, SelectionKey.OP_ACCEPT);
            new Thread(this).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
