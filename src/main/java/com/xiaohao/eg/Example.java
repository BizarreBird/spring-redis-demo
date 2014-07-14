package com.xiaohao.eg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.Resource;

/**
 * Created by xiaohao on 2014/7/14.
 */
@Component("example")
public class Example {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 可以按照list注入 对应redis中的集合类型
     */
    @Resource(name="redisTemplate")
    private ListOperations<String,String> listOperations;

    // can also inject as Value, Set, ZSet, and HashOperations

    /**
     * template 可以按照set注入 对象redis里面的普通类型
     */
    @Resource(name="redisTemplate")
    private SetOperations<String,String> setOperations;

    /**
     * template 可以按照set注入 对象redis里面的普通类型
     */
    @Resource(name="redisTemplate")
    private ValueOperations<String,String> valueOperations;

    /**
     * template 可以按照zset注入
     */
    @Resource(name="redisTemplate")
    private ZSetOperations<String,String> zSetOperations;

    /**
     * template hashOperations 对象redis里面的普通类型
     */
    @Resource(name="redisTemplate")
    private HashOperations<String,String,String> hashOperations;


    public void test(){
     //   setOperations.add("t","t");
        //集合类型操作
        listOperations.leftPush("test","好小胖");
        //利用模板 想制定频道发送消息
        redisTemplate.convertAndSend("news.*","testtestestetset");
    }



    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setListOperations(ListOperations<String, String> listOperations) {
        this.listOperations = listOperations;
    }

    public void setSetOperations(SetOperations<String, String> setOperations) {
        this.setOperations = setOperations;
    }

    public void setValueOperations(ValueOperations<String, String> valueOperations) {
        this.valueOperations = valueOperations;
    }

    public void setzSetOperations(ZSetOperations<String, String> zSetOperations) {
        this.zSetOperations = zSetOperations;
    }

    public void setHashOperations(HashOperations<String, String, String> hashOperations) {
        this.hashOperations = hashOperations;
    }
}
