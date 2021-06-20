package com.lyni.lockit.ui.authenticate;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lyni.lockit.databinding.FragmentAuthenticateBinding;
import com.lyni.lockit.ui.LockitApplication;
import com.lyni.lockit.ui.MainActivity;
import com.lyni.lockit.utils.BiometricPromptUtil.BiometricPromptUtil;

import org.jetbrains.annotations.NotNull;

/**
 * @author Liangyong Ni
 * description 密码验证界面
 * @date 2021/6/13
 */
public class AuthenticateFragment extends Fragment {
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
        BiometricPromptUtil.setBiometricPrompt(this, () -> {
            Navigation.findNavController(requireView()).popBackStack();
            LockitApplication.setAuthenticated(true);
        });
        BiometricPromptUtil.show();
        ((MainActivity) requireActivity()).setOnPressBackListener(Activity::finish);
        binding.retry.setOnClickListener(v -> BiometricPromptUtil.show());
    }
}