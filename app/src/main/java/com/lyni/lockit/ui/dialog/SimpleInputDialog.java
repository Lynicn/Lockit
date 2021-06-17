package com.lyni.lockit.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.lyni.lockit.R;
import com.lyni.lockit.utils.ToastUtil.ToastUtil;

/**
 * @author Liangyong Ni
 * description 简单输入框
 * @date 2021/6/17
 */
public class SimpleInputDialog {
    private final AlertDialog instance;
    private final EditText input;
    private OnEnsureListener onEnsureListener;

    public SimpleInputDialog(@NonNull Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_simple_input, null, false);
        input = view.findViewById(R.id.dialog_simple_input_et);
        instance = new AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton("确认", (dialog, which) -> {
                    if (TextUtils.isEmpty(input.getText())) {
                        ToastUtil.show("输入无效");
                    } else {
                        onEnsureListener.onEnsure(input.getText().toString().trim());
                        ToastUtil.show(input.getText().toString());
                    }
                })
                .setNegativeButton("取消", null)
                .create();
    }

    public AlertDialog getInstance(@NonNull String title, @NonNull Drawable icon, @NonNull OnEnsureListener onEnsureListener, InputTypeListener inputTypeListener) {
        instance.setTitle(title);
        instance.setIcon(icon);
        input.setText(null);
        input.clearFocus();
        if (inputTypeListener != null) {
            inputTypeListener.setInputType(input);
        }
        this.onEnsureListener = onEnsureListener;
        return instance;
    }

    public AlertDialog getInstance(@NonNull OnEnsureListener onEnsureListener) {
        instance.setTitle(null);
        instance.setIcon(null);
        input.setText(null);
        input.clearFocus();
        this.onEnsureListener = onEnsureListener;
        return instance;
    }

    public AlertDialog getInstance(@NonNull String title, int iconId, @NonNull OnEnsureListener onEnsureListener, InputTypeListener inputTypeListener) {
        instance.setTitle(title);
        instance.setIcon(iconId);
        input.setText(null);
        input.clearFocus();
        if (inputTypeListener != null) {
            inputTypeListener.setInputType(input);
        }
        this.onEnsureListener = onEnsureListener;
        return instance;
    }

    public interface OnEnsureListener {
        /**
         * 点击确认按钮后执行
         *
         * @param input 输入
         */
        void onEnsure(String input);
    }

    public interface InputTypeListener {
        /**
         * 该接口用来设置输入框的输入类型，默认为text
         *
         * @param input 输入框
         */
        void setInputType(EditText input);
    }
}
