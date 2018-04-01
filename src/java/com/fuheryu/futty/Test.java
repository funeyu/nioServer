package com.fuheryu.futty;

import java.net.InetSocketAddress;

public class Test {

    public static void main(String[] args) {
        NioServerBoss boss = new NioServerBoss(null);
        boss.bind(new InetSocketAddress(9999));
    }
}
