package com.lyni.lockit.model.entity.converters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

/**
 * @author Liangyong Ni
 * description Bitmap与String转换器
 * @date 2021/6/13
 */
public class BitmapConverter {
    /**
     * 将base64字符串转化成Bitmap
     *
     * @param icon base64字符串
     * @return 转化后的Bitmap
     */
    @TypeConverter
    public static synchronized Bitmap stringToBitmap(@NonNull String icon) {
        byte[] img = Base64.decode(icon.getBytes(), Base64.DEFAULT);
        if (img != null) {
            return BitmapFactory.decodeByteArray(img, 0, img.length);
        }
        return null;
    }


    /**
     * 将bitmap转为base64格式的字符串
     *
     * @param bitmap 需要转换的bitmap
     * @return 转化后的base64字符串
     */
    @TypeConverter
    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //参数100表示不压缩
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        return Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
    }

    /**
     * 将Bitmap转化为Drawable
     *
     * @param bitmap 需要转化的Bitmap
     * @return 转换后的Drawable格式图片
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /**
     * 将Drawable转化为Bitmap
     *
     * @param drawable 需要转化的Drawable
     * @return 转化后的Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        return ((BitmapDrawable) drawable).getBitmap();
    }
}
