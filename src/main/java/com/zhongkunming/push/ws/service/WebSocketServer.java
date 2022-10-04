package com.zhongkunming.push.ws.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.zhongkunming.push.ws.model.WsMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import static com.zhongkunming.push.ws.model.WsMessageConstants.*;

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
        System.out.println(mid + " 已连接");
    }

    @OnClose
    public void onClose() {
        ServerHolder.INSTANCE.removeServer(this);
        System.out.println(this.mid + " 已断开");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        boolean typeJSON = JSONUtil.isTypeJSON(message);
        if (!typeJSON) {
            System.out.println("收到非JSON数据，" + message);
            return;
        }
        JSONObject wsMessage = JSONUtil.parseObj(message);
        String type = wsMessage.get("type").toString();
        switch (type) {
            case SUBSCRIBE -> {
                System.out.println(this.getMid() + " 订阅");
                ServerHolder.INSTANCE.addServer(this);
            }

            case UNSUBSCRIBE -> {
                System.out.println(this.getMid() + "取消订阅");
                ServerHolder.INSTANCE.removeServer(this);
            }

            case KEEP_LIVE -> {
                System.out.println("keep-live");
            }
            default -> System.err.println("服务端只接收订阅和取消订阅类型消息 " + type);
        }

    }

    @OnError
    public void onError(Session session, Throwable error) {
    }
}
