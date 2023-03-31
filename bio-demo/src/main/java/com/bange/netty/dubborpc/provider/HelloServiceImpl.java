package com.bange.netty.dubborpc.provider;

import com.bange.netty.dubborpc.common.HelloService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 每次客户端调用都会创建一个实例对象
 */
public class HelloServiceImpl implements HelloService {
    Logger logger = LoggerFactory.getLogger(Object.class);

    /**
     * 消费者调用该方法时，返回字符串
     * @param msg
     * @return
     */
    @Override
    public String hello(String msg) {
        logger.info("收到客户端消息：" + msg);
        if (msg != null) {
            return "[客户端，消息：]" + msg;
        }
        return "[客户端，消息!!!]";
    }
}
