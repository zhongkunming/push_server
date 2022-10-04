package com.zhongkunming.push.ws.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zhongkunming
 */
@Data
public class BaseNotification implements Serializable {

    @Serial
    private static final long serialVersionUID = 1184644848312820336L;

    private String title;

    private String content;
}
