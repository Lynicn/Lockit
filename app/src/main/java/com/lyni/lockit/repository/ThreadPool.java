package com.lyni.lockit.repository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Liangyong Ni
 * description 线程池类
 * @date 2021/6/23
 */
public class ThreadPool {
    /**
     * 单线程线程池
     */
    private static final ExecutorService SINGLE_THREAD_EXECUTOR = Executors.newSingleThreadExecutor();

    /**
     * 执行耗时操作
     *
     * @param runnable 需要在子线程执行的Runnable对象
     */
    public static void executeTasks(Runnable runnable) {
        SINGLE_THREAD_EXECUTOR.execute(runnable);
    }
}
