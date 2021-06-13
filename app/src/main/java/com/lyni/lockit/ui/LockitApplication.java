package com.lyni.lockit.ui;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.lyni.lockit.utils.LogUtil.LogUtil;

import java.io.File;

/**
 * @author Liangyong Ni
 * description 自定义application类
 * @date 2021/6/13
 */
public class LockitApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        initLog();
    }

    private void initLog() {
        File logDir = new File(getFilesDir().getAbsolutePath() + "/log");
        if (!(logDir.exists())) {
            if (logDir.mkdir()) {
                LogUtil.setLogDirAndInit(logDir);
            }
        } else {
            LogUtil.setLogDirAndInit(logDir);
        }
    }

    public static Context getContext() {
        return context;
    }
}
