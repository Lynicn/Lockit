package com.lyni.lockit.ui.listener;

import com.lyni.lockit.ui.MainActivity;

/**
 * @author Liangyong Ni
 * description 返回键事件监听接口
 * @date 2021/6/18
 */
public interface OnPressBackListener {
    /**
     * 按下返回键时调用
     *
     * @param mActivity MainActivity对象
     */
    void onPressBack(MainActivity mActivity);
}
