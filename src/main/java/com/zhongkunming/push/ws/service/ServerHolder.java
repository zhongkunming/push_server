package com.zhongkunming.push.ws.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import com.alibaba.fastjson2.JSON;
import com.zhongkunming.push.ws.model.BaseNotification;
import com.zhongkunming.push.ws.model.WsMessage;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import static com.zhongkunming.push.ws.model.WsMessageConstants.KEEP_LIVE;
import static com.zhongkunming.push.ws.model.WsMessageConstants.SERVER_PUSH;

/**
 * @author zhongkunming
 */
public class ServerHolder {

    private final static ConcurrentHashMap<String, WebSocketServer> SERVER = new ConcurrentHashMap<>();

    static {
        CronUtil.schedule("*/10 * * * * *", (Task) ServerHolder::pingAllServer);
        // 支持秒级别定时任务
        CronUtil.setMatchSecond(true);
        CronUtil.start();
        System.out.println("任务启动");
    }

    private ServerHolder() {
    }

    private static class Holder {
        private final static ServerHolder HOLDER = new ServerHolder();
    }

    public final static ServerHolder INSTANCE = Holder.HOLDER;


    public void addServer(WebSocketServer server) {
        SERVER.put(server.getMid(), server);
    }

    public void removeServer(WebSocketServer server) {
        SERVER.put(server.getMid(), server);
    }

    public static void pushMessage(BaseNotification message) {
        WsMessage wsMessage = new WsMessage();
        wsMessage.setPlatform("server");
        wsMessage.setTraceId(UUID.fastUUID().toString());
        wsMessage.setType(SERVER_PUSH);
        wsMessage.setData(message);

        SERVER.forEach((k, v) -> {
            try {
                v.getSession().getBasicRemote().sendText(JSON.toJSONString(wsMessage));
            } catch (IOException e) {
                System.out.println("发送消息失败: " + k);
            }
        });
    }

    public static void pingAllServer() {
        WsMessage wsMessage = new WsMessage();
        wsMessage.setPlatform("server");
        wsMessage.setTraceId(UUID.fastUUID().toString());
        wsMessage.setType(KEEP_LIVE);

        SERVER.forEach((k, v) -> {
            try {
                v.getSession().getBasicRemote().sendText(JSON.toJSONString(wsMessage));
            } catch (IOException e) {

                System.out.println("发送消息失败: " + k);
            }
        });
    }
}
