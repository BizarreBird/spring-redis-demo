package com.xiaohao.lisenner;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * Created by xiaohao on 2014/7/14.
 */
public class MyMessageDelegateListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.out.println(new String(message.getBody()));
    }
}
