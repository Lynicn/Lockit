package com.lyni.lockit.repository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Liangyong Ni
 * description 线程池类
 * @date 2021/6/23
 */
public class ThreadPool {
    private static final ExecutorService SINGLE_THREAD_EXECUTOR = Executors.newSingleThreadExecutor();

    public static void executeTasks(Runnable runnable) {
        SINGLE_THREAD_EXECUTOR.execute(runnable);
    }
}
