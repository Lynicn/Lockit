package com.lyni.lockit.utils.ToastUtil;

import android.app.Activity;
import android.widget.Toast;

import com.lyni.lockit.ui.LockitApplication;

/**
 * @author Liangyong Ni
 * description Toast工具类
 * @date 2021/6/13
 */
public class ToastUtil {

    public static void show(Object message) {
        Toast.makeText(LockitApplication.getContext(), message.toString(), Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Object message) {
        Toast.makeText(LockitApplication.getContext(), message.toString(), Toast.LENGTH_LONG).show();
    }

    public static void showAnywhere(Activity activity, Object message) {
        activity.runOnUiThread(() -> Toast.makeText(LockitApplication.getContext(), message.toString(), Toast.LENGTH_SHORT).show());
    }
}
