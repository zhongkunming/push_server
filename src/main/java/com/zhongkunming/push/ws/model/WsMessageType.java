package com.zhongkunming.push.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author zhongkunming
 */
@Getter
@AllArgsConstructor
public enum WsMessageType {

    // 订阅消息
    SUBSCRIBE("subscribe"),

    // 取消订阅消息
    UNSUBSCRIBE("unsubscribe")
    ;

    private final String typeName;



}
