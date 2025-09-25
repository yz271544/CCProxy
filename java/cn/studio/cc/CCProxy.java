package cn.studio.cc;

import cn.studio.cc.net.http.HttpProxy;

public class CCProxy {
    public static void main(String[] args) {
        HttpProxy httpServer = new HttpProxy(8888, 16);

        httpServer.start();
    }
}
