package com.xiaohao;

import com.xiaohao.config.AppConfig;
import com.xiaohao.eg.Example;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by xiaohao on 2014/7/14
 * app 启动器
 */
public class Starter {
    /**
     * @param args
     */
    public static void main(String[] args) {
        //拿到spring容器
        ApplicationContext ac = new FileSystemXmlApplicationContext("./src/main/resources/applicationContext.xml");
        //ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        Example e = (Example) ac.getBean("example");
        e.test();
        e.pipelineSample();
    }

}
