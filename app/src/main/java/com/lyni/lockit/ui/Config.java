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
    public static boolean encrypted;
    public static boolean supportFingerprint;
    public static boolean usePasswordEncryption;
    public static String password;
    public static boolean useFingerprintEncryption;

    public static void setConfigBySharedPreferences(SharedPreferences sharedPreferences) {
        encrypted = sharedPreferences.getBoolean("encrypt", false);
        usePasswordEncryption = sharedPreferences.getBoolean("usePasswordEncryption", false);
        password = sharedPreferences.getString("password", "");
        useFingerprintEncryption = sharedPreferences.getBoolean("useFingerprintEncryption", false);
    }

    /**
     * 检查指纹
     *
     * @param context 上下文
     */
    public static void checkFingerprint(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
            //确定是否指纹硬件存在和功能
            supportFingerprint = fingerprintManager.isHardwareDetected() && fingerprintManager.hasEnrolledFingerprints();
        }
    }
}
