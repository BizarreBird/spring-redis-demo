package com.xiaohao.eg;

import com.xiaohao.entity.User;
import com.xiaohao.util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
     * template 可以按照set注入 对象redis里面的Set类型
     */
    @Resource(name="redisTemplate")
    private SetOperations<String,String> setOperations;

    /**
     * template 可以按照set注入 对象redis里面的普通类型
     */
    @Resource(name="redisTemplate")
    private ValueOperations<String,String> valueOperations;

    /**
     * template 可以按照zset注入 对应redis里面的 sortedSet类型
     */
    @Resource(name="redisTemplate")
    private ZSetOperations<String,String> zSetOperations;

    /**
     * template hashOperations 对象redis里面的hash类型
     */
    @Resource(name="redisTemplate")
    private HashOperations<String,String,String> hashOperations;


    public void test(){
     //   setOperations.add("t","t");
        //集合类型操作
        listOperations.leftPush("test","好小胖");
        //利用模板 想制定频道发送消息
        redisTemplate.convertAndSend("news.*","testtestestetset");
        User user = new User();
        user.setId(1000);
        user.setPassword("test");
        user.setName("test");
        //存储一个序列化成json的字符串
        valueOperations.set("userObject", SerializeUtil.obj2Json(user));
        System.out.println();
        User user1 =SerializeUtil.json2Obj(valueOperations.get("userObject"),User.class);
        System.out.println(user1.getId());
        System.out.println(user1.getName());
        System.out.println(user1.getPassword());
        //hash类型
        hashOperations.put("hashtest","xiaohao","testtest");
        //set类型 resis 2.4以前只能接受一个值
        //setOperations.add("setTest","my name is","test"); 会报错
        setOperations.add("setTest","my name is");
        //sortSet
        zSetOperations.add("sortSetTest","xiaohao",100);
        zSetOperations.add("sortSetTest","xiaohaohahahaha",101);
        //notice!!! value重复的不会存储 所以只存进去了101   104 两个
        zSetOperations.add("sortSetTest","xiaohao",102);
        zSetOperations.add("sortSetTest","xiaohao",103);
        zSetOperations.add("sortSetTest","xiaohao",104);
        zSetOperations.zCard("sortSetTest");
        zSetOperations.incrementScore("sortSettest","xiaohao",457);
        // key value 时间单位
        redisTemplate.expire("sortSetTest",1000,TimeUnit.MILLISECONDS);
        //取得hash操作
        redisTemplate.opsForHash();
        //取得list操作
        redisTemplate.opsForList();
        //取得value操作
        redisTemplate.opsForValue();
        //取得 set操作
        redisTemplate.opsForSet();
        //取得sortSet操作
        redisTemplate.opsForZSet();


    }


    /**
     * 在连接池环境中，需要借助sessionCallback来绑定connection
     */
    public void txUsedPoolSample(){
        SessionCallback<User> sessionCallback = new SessionCallback<User>() {
            @Override
            public User execute(RedisOperations operations) throws DataAccessException {
               //开启事务
                operations.multi();
                User user = new User();
                user.setId(100);
                user.setName("xiaohao");
                user.setPassword("hahaha");
                String key = "user:" + user.getId();
                BoundValueOperations<String, User> oper = operations.boundValueOps(key);
                oper.set(user);
                oper.expire(60, TimeUnit.MINUTES);
                //执行
                operations.exec();
                return user;
            }
        };
        redisTemplate.execute(sessionCallback);
    }

    /**
     * pipeline : 1，正确使用方式 pipeline 方式可以一次执行很多 redis操作 不用一个执行一个返回
     */
    public void pipelineSample(){
        final byte[] rawKey = ((StringRedisSerializer)redisTemplate.getKeySerializer()).serialize("user_total");
        //pipeline
        RedisCallback<List<Object>> pipelineCallback = new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                connection.incr(rawKey);
                connection.incr(rawKey);
                return connection.closePipeline();
            }

        };

        List<Object> results = (List<Object>)redisTemplate.execute(pipelineCallback);
        for(Object item : results){
            System.out.println(item.toString());
        }
    }
    //pipeline:备用方式
    public void pipelineSampleX(){
        byte[] rawKey = ((StringRedisSerializer)redisTemplate.getKeySerializer()).serialize("T");
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection redisConnection = factory.getConnection();
        List<Object> results;
        try{
            redisConnection.openPipeline();
            redisConnection.incr(rawKey);
            results = redisConnection.closePipeline();
        }finally{
            RedisConnectionUtils.releaseConnection(redisConnection, factory);
        }
        if(results == null){
            return;
        }
        for(Object item : results){
            System.out.println(item.toString());
        }

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
