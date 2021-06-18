package com.lyni.lockit.ui.listener;

import android.view.View;

/**
 * @author Liangyong Ni
 * description 双击事件监听器
 * @date 2021/6/18
 */
public abstract class DoubleClickListener implements View.OnClickListener {

    private static final long DOUBLE_TIME = 1000;
    private static long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastClickTime < DOUBLE_TIME) {
            onDoubleClick(v);
        }
        lastClickTime = currentTimeMillis;
    }

    /**
     * 双击接口
     *
     * @param v 设置监听器的对象
     */
    public abstract void onDoubleClick(View v);

}
