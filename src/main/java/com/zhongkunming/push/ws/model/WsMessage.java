package com.zhongkunming.push.ws.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zhongkunming
 */
@Data
public class WsMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 7201044364302830692L;

    // 全局唯一id
    private String traceId;

    // 消息类型
    private String type;

    // 平台 macos android windows linux ios server
    private String platform;

    // 数据
    private BaseNotification data;

}
