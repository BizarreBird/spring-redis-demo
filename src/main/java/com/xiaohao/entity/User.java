package com.xiaohao.entity;

import java.io.Serializable;

/**
 * Created by xiaohao on 2014/7/15.
 * 创建一个对象 实现序列化接口防止会有序列化问题 用来尝试放入redis存储
 */
public class User implements Serializable {

    private int id;

    private String name;

    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
