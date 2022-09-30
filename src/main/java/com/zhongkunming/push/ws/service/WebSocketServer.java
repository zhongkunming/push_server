package com.zhongkunming.push.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author zhongkunming
 */
@Slf4j
@Getter
@Component
@ServerEndpoint("/api/ws/{mid}")
public class WebSocketServer {
    private Session session;

    // 机器ID
    private String mid = "";


    @OnOpen
    public void onOpen(Session session, @PathParam("mid") String mid) {
        this.session = session;
        this.mid = mid;
        ServerHolder.INSTANCE.addServer(this);
        System.out.println(mid + "  open");
    }

    @OnClose
    public void onClose() {
        ServerHolder.INSTANCE.removeServer(this);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
    }

    @OnError
    public void onError(Session session, Throwable error) {
    }
}
