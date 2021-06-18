package com.lyni.lockit.ui.listener;

import android.widget.EditText;

/**
 * @author Liangyong Ni
 * description 输入类型监听器
 * @date 2021/6/18
 */
public interface InputTypeListener {
    /**
     * 该接口用来设置输入框的输入类型，默认为text
     *
     * @param input 输入框
     */
    void setInputType(EditText input);
}
