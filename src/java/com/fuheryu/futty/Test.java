package com.fuheryu.futty;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {
        NioServerBoss boss = new NioServerBoss(null);
        boss.bind(new InetSocketAddress(9999));
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(boss);
    }
}
