package com.xiaohao.eg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

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

    /**
     * template 可以按照set注入 对象redis里面的普通类型
     */
    @Resource(name="redisTemplate")
    private SetOperations<String,String> setOperations;

    public void test(){
     //   setOperations.add("t","t");
    //    setOperations.add("s","s");
      //  listOperations.leftPush("test","1");
        String tt =listOperations.leftPop("test");
        System.out.println(tt);
    }



    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setListOperations(ListOperations<String, String> listOperations) {
        this.listOperations = listOperations;
    }
}
