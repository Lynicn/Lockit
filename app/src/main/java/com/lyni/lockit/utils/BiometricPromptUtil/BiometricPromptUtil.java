package com.lyni.lockit.utils.BiometricPromptUtil;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.Fragment;

import com.lyni.lockit.ui.listener.OnSucceedListener;
import com.lyni.lockit.utils.ToastUtil.ToastUtil;

/**
 * @author Liangyong Ni
 * description 指纹验证工具类
 * @date 2021/6/21
 */
public class BiometricPromptUtil {
    private static final BiometricPrompt.PromptInfo PROMPT_INFO;
    private static BiometricPrompt biometricPrompt;

    static {
        PROMPT_INFO = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("请验证指纹")
                .setNegativeButtonText("取消")
                .build();
    }

    public static void setBiometricPrompt(Fragment fragment, OnSucceedListener onSucceedListener) {
        //需要提供的参数callback
        biometricPrompt = new BiometricPrompt(fragment, command -> new Handler(fragment.requireActivity().getMainLooper()).post(command), new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                onSucceedListener.onSucceed();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                ToastUtil.show("Authentication Failed ƪ(˘⌣˘)ʃ");
            }
        });
        biometricPrompt.authenticate(PROMPT_INFO);
    }

    public static void show() {
        biometricPrompt.authenticate(PROMPT_INFO);
    }
}
