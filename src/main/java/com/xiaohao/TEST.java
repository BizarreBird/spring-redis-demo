package com.xiaohao;

import com.xiaohao.eg.Example;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

/**
 * Created by xiaohao on 2014/7/10.
 * 启动 开始测试  starter
 */
public class TEST {

    public static void main(String[] args){

       //拿到spring容器
       ApplicationContext ac = new FileSystemXmlApplicationContext("./src/main/resources/applicationContext.xml");
   //    RedisTemplate<String,String> t =(RedisTemplate)ac.getBean("redisTemplate");
     //  Set<String> sets =t.keys("*");
   //    System.out.println(sets.size());
   //    t.boundListOps("test").leftPush("Test");

       Example e = (Example)ac.getBean("example");
        e.test();
    }

}
