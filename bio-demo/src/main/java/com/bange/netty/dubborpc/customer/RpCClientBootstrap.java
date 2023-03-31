package com.bange.netty.dubborpc.customer;

import com.bange.netty.dubborpc.common.HelloService;
import com.bange.netty.dubborpc.rpcclient.RPCNettyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RpCClientBootstrap {
  private static   Logger logger = LoggerFactory.getLogger(Object.class);
    private static String providerName="helloservice#hello#";
    public static void main(String[] args) {
        // 创建一个消费者
        RPCNettyClient customer = new RPCNettyClient();
        // 创建一个代理对象
        logger.info("==>准备代理对象");
        HelloService helloService =(HelloService) customer.getBean(HelloService.class, providerName);
        // 通过代理对象调用服务端的api
        String result = helloService.hello("huuuuu");
        System.out.println("调用结果："+result);
    }
}
