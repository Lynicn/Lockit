package com.lyni.lockit.utils.clipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.lyni.lockit.utils.ToastUtil.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Liangyong Ni
 * description 剪贴板工具类
 * @date 2021/6/17
 */
public class ClipboardUtil {
    /**
     * 向剪贴板添加数据
     *
     * @param context 上下文
     * @param content 写入的内容
     */
    public static void copy(Context context, String content) {
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
        ClipData clipData = ClipData.newPlainText(null, content);
        // 把数据集设置（复制）到剪贴板
        clipboard.setPrimaryClip(clipData);
        ToastUtil.showLong(content + " 已复制（￣︶￣）");
    }

    /**
     * 从系统剪贴板获取内容
     *
     * @param context 上下文
     * @return 返回数据
     */
    public static List<String> getCopy(Context context) {
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 返回数据
        ClipData clipData = clipboard.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            List<String> result = new ArrayList<>();
            for (int i = 0; i < clipData.getItemCount(); i++) {
                result.add(clipData.getItemAt(i).getText().toString());
            }
            return result;
        }
        return null;
    }
}
