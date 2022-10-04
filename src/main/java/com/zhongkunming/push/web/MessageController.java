package com.zhongkunming.push.web;

import com.zhongkunming.push.ws.model.FlowNotification;
import com.zhongkunming.push.ws.service.ServerHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhongkunming
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/message")
@RequiredArgsConstructor
public class MessageController {

    @GetMapping("/send")
    public String send(@RequestParam("msg") String msg) {
        FlowNotification data = new FlowNotification();
        data.setTitle("流量提醒");
        data.setContent("还有100GB流量");

        ServerHolder.pushMessage(data);
        return "发送消息成功";
    }



}
