package cn.studio.cc;

import cn.studio.cc.config.ConfigManager;
import cn.studio.cc.net.http.HttpProxy;

public class CCProxy {
    public static void main(String[] args) {
        ConfigManager config = ConfigManager.getInstance();
        HttpProxy httpServer = new HttpProxy(config.getPort(), config.getAnswerThreadNum());

        httpServer.start();
    }
}