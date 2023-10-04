package com.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        log.info("=========线程信息存储==={}",threadLocal);
        log.info("======当前线程id:{}",Thread.currentThread().getId());
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        log.info("=========线程信息获取==={}",threadLocal);
        log.info("=========当前线程id:{}",Thread.currentThread().getId());
        log.info("获取存储信息==={}",threadLocal.get());
        return threadLocal.get();
    }
}
