package com.lyni.lockit.ui.customized.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.lyni.lockit.R;
import com.lyni.lockit.ui.listener.InputTypeListener;
import com.lyni.lockit.ui.listener.OnEnsureListener;
import com.lyni.lockit.utils.ToastUtil.ToastUtil;

/**
 * @author Liangyong Ni
 * description 简单输入框
 * @date 2021/6/17
 */
public class SimpleInputDialog {
    /**
     * 对话框实例
     */
    private final AlertDialog instance;
    /**
     * 输入框
     */
    private final EditText input;
    /**
     * 确认回调
     */
    private OnEnsureListener onEnsureListener;

    public SimpleInputDialog(@NonNull Context context) {
        View view = View.inflate(context, R.layout.dialog_simple_input, null);
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

    /**
     * 得到特定类型的对话框
     *
     * @param title             标题
     * @param icon              图标
     * @param onEnsureListener  确认回调
     * @param inputTypeListener 输入类型
     * @return 对话框
     */
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

    /**
     * 得到特定类型的对话框
     *
     * @param onEnsureListener 确认回调
     * @return 对话框
     */
    public AlertDialog getInstance(@NonNull OnEnsureListener onEnsureListener) {
        instance.setTitle(null);
        instance.setIcon(null);
        input.setText(null);
        input.clearFocus();
        this.onEnsureListener = onEnsureListener;
        return instance;
    }

    /**
     * 得到特定类型的对话框
     *
     * @param title             标题
     * @param iconId            图标Id
     * @param onEnsureListener  确认回调
     * @param inputTypeListener 输入类型
     * @return 对话框
     */
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
}
