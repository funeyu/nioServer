package com.fuheryu.futty;

import java.net.InetSocketAddress;

public class ServerBootstrap {

    private NioServerBoss boss;


    public ServerBootstrap(NioServerBoss boss) {
        this.boss = boss;
    }

    public void bind(InetSocketAddress inetSocketAddress) {
        boss.bind(inetSocketAddress);
    }

}
