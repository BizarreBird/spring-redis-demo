package com.xiaohao.util;

import com.alibaba.fastjson.JSON;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by xiaohao on 2014/7/15.
 */
public class SerializeUtil {

    /**
     *
     * @param obj
     * @return
     */
    public static byte[] obj2Byte(Object obj) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 利用fastJson 序列化对象 to json 字符串
     * @param obj
     * @return
     */
    public static String obj2Json(Object obj){
        if(obj!=null){
            return JSON.toJSONString(obj);
        }
        return null;
    }

    /**
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static  <T> T json2Obj(String json,java.lang.Class<T> clazz){
        return JSON.parseObject(json,clazz);
    }
}
