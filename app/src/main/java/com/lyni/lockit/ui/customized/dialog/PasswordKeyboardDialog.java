package com.lyni.lockit.ui.customized.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.lyni.lockit.R;
import com.lyni.lockit.ui.customized.PasswordView;
import com.lyni.lockit.ui.listener.OnEnsureListener;

/**
 * @author Liangyong Ni
 * description 显示一个用于输入密码的对话框
 * @date 2021/6/27
 */
public class PasswordKeyboardDialog {

    /**
     * 弹框
     */
    private final AlertDialog dialog;


    /**
     * 默认样式
     *
     * @param context 上下文
     */
    public PasswordKeyboardDialog(Context context, OnEnsureListener onEnsureListener) {
        View mDialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog_password_input, null, false);
        PasswordView passwordView = mDialogLayout.findViewById(R.id.dialog_password_view);
        dialog = new AlertDialog.Builder(context).create();
        dialog.setCancelable(true);
        dialog.show();
        //设置透明度0.4
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置弹框布局
        window.setContentView(mDialogLayout);
        dialog.setCanceledOnTouchOutside(false);
        window.setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(true);
        passwordView.setOnInputFinishListener(input -> {
            onEnsureListener.onEnsure(input);
            dialog.dismiss();
        });
        dialog.show();
    }
}
