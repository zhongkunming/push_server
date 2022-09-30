package com.zhongkunming.push.service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhongkunming
 */
public class ServerHolder {

    private final static ConcurrentHashMap<String, WebSocketServer> SERVER = new ConcurrentHashMap<>();

    private ServerHolder() {
    }

    private static class Holder {
        private final static ServerHolder HOLDER = new ServerHolder();
    }

    public final static ServerHolder INSTANCE = Holder.HOLDER;


    public void addServer(WebSocketServer server) {
        SERVER.put(server.getSid(), server);
    }

    public void removeServer(WebSocketServer server) {
        SERVER.put(server.getSid(), server);
    }
}
