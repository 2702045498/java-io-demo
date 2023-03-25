package com.bange.bio;

import java.util.concurrent.*;

//线程池类
public class HandlerSocketThreadPool {
    private ExecutorService executorService;

    /**
     *
     * @param maxThreadNum 最大线程数
     * @param queueSize 任务队列数
     */
    public HandlerSocketThreadPool(int maxThreadNum,int queueSize) {
        this.executorService = new ThreadPoolExecutor(3,maxThreadNum,120, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize));
    }
    //提交任务给线程池
    public void excute(Runnable target){
        executorService.execute(target);
    }
}
