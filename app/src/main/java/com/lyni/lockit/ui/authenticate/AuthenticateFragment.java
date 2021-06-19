package com.lyni.lockit.ui.authenticate;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lyni.lockit.databinding.FragmentAuthenticateBinding;
import com.lyni.lockit.ui.LockitApplication;
import com.lyni.lockit.ui.MainActivity;
import com.lyni.lockit.ui.listener.OnPressBackListener;
import com.lyni.lockit.utils.ToastUtil.ToastUtil;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * @author Liangyong Ni
 * description 密码验证界面
 * @date 2021/6/13
 */
public class AuthenticateFragment extends Fragment {

    private static final String TAG = "AuthenticateFragment";
    FragmentAuthenticateBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthenticateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showBiometricPrompt(command -> new Handler(requireActivity().getMainLooper()).post(command));
        ((MainActivity) requireActivity()).setOnPressBackListener(Activity::finish);
    }

    /**
     * 生物认证的setting
     */
    private void showBiometricPrompt(Executor executor) {
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("请验证指纹")
                .setNegativeButtonText("取消")
                .build();

        //需要提供的参数callback
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                ToastUtil.show(errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Navigation.findNavController(requireView()).popBackStack();
                LockitApplication.setAuthenticated(true);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                ToastUtil.show("Authentication Failed");
            }
        });
        biometricPrompt.authenticate(promptInfo);
    }
}