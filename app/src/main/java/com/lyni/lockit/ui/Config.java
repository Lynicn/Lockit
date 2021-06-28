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
    private static SharedPreferences sharedPreferences;

    public static void setSharedPreferences(SharedPreferences sharedPreferences) {
        Config.sharedPreferences = sharedPreferences;
    }


    public static void setConfig() {
        if (sharedPreferences != null) {
            encrypted = sharedPreferences.getBoolean("encrypt", false);
            usePasswordEncryption = sharedPreferences.getBoolean("usePasswordEncryption", false);
            password = sharedPreferences.getString("password", "000000");
            useFingerprintEncryption = sharedPreferences.getBoolean("useFingerprintEncryption", false);
        }
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
        } else {
            supportFingerprint = false;
        }
    }

    public static void saveConfig() {
        sharedPreferences.edit().putBoolean("encrypt", encrypted)
                .putBoolean("usePasswordEncryption", usePasswordEncryption)
                .putString("password", password)
                .putBoolean("useFingerprintEncryption", useFingerprintEncryption).apply();
    }

}
