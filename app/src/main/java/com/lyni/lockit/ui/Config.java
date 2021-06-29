package com.lyni.lockit.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;

/**
 * @author Liangyong Ni
 * description 配置类
 * @date 2021/6/22
 */
public class Config {
    /**
     * 是否开启加密
     */
    public static boolean encrypted;
    /**
     * 是否支持指纹验证
     */
    public static boolean supportFingerprint;
    /**
     * 是否开启PIN加密
     */
    public static boolean usePasswordEncryption;
    /**
     * PIN
     */
    public static String password;
    /**
     * 是否开启指纹加密
     */
    public static boolean useFingerprintEncryption;
    /**
     * 配置信息的SharedPreferences
     */
    private static SharedPreferences sharedPreferences;

    /**
     * 设置SharedPreferences并初始化配置
     *
     * @param sharedPreferences 配置信息的SharedPreferences
     */
    public static void initConfig(SharedPreferences sharedPreferences, Context context) {
        Config.sharedPreferences = sharedPreferences;
        if (sharedPreferences != null) {
            encrypted = sharedPreferences.getBoolean("encrypt", false);
            usePasswordEncryption = sharedPreferences.getBoolean("usePasswordEncryption", false);
            password = sharedPreferences.getString("password", "000000");
            useFingerprintEncryption = sharedPreferences.getBoolean("useFingerprintEncryption", false);
        }
        checkFingerprint(context);
    }


    /**
     * 检查指纹
     *
     * @param context 上下文
     */
    private static void checkFingerprint(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
            //确定是否指纹硬件存在和功能
            supportFingerprint = fingerprintManager.isHardwareDetected() && fingerprintManager.hasEnrolledFingerprints();
        } else {
            supportFingerprint = false;
        }
    }

    /**
     * 保存配置到文件
     */
    public static void saveConfig() {
        sharedPreferences.edit().putBoolean("encrypt", encrypted)
                .putBoolean("usePasswordEncryption", usePasswordEncryption)
                .putString("password", password)
                .putBoolean("useFingerprintEncryption", useFingerprintEncryption).apply();
    }

}
