package com.xiaohao.lisenner;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * Created by xiaohao on 2014/7/14.
 * 实现messageListenner接口 接收到消息后 处理   onMessage
 */
public class MyMessageDelegateListener implements MessageListener {

    /**
     * 接收到消息 触发
     * @param message
     * @param bytes
     */
    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.out.println(new String(message.getBody()));
    }
}
