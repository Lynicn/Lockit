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
    private static final String[] EMOTICONS = {
            "w(ﾟДﾟ)w", "┗|｀O′|┛", "（⊙ｏ⊙）", "(￣┰￣*)", "(ﾉ*･ω･)ﾉ"
    };

    /**
     * 显示Toast
     *
     * @param message 需要显示的信息
     */
    public static void show(Object message) {
        Toast toast = Toast.makeText(LockitApplication.getContext(), null, Toast.LENGTH_SHORT);
        toast.setText(message.toString());
        toast.show();
    }

    /**
     * 较长时间显示Toast
     *
     * @param message 需要显示的信息
     */
    public static void showLong(Object message) {
        Toast toast = Toast.makeText(LockitApplication.getContext(), null, Toast.LENGTH_LONG);
        toast.setText(message.toString());
        toast.show();
    }

    /**
     * 在主线程中显示Toast
     *
     * @param activity activity
     * @param message  信息
     */
    public static void showAnywhere(Activity activity, Object message) {
        activity.runOnUiThread(() -> Toast.makeText(LockitApplication.getContext(), message.toString(), Toast.LENGTH_SHORT).show());
    }
}
