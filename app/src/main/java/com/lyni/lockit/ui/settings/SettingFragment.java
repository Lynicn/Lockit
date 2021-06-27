package com.lyni.lockit.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lyni.lockit.R;
import com.lyni.lockit.databinding.FragmentSettingBinding;
import com.lyni.lockit.ui.Config;
import com.lyni.lockit.ui.MainActivity;
import com.lyni.lockit.ui.base.BaseFragment;
import com.lyni.lockit.ui.customized.dialog.PasswordKeyboardDialog;

/**
 * @author Liangyong Ni
 * description 设置界面
 * @date 2021/6/28
 */
public class SettingFragment extends BaseFragment {
    private static final String TAG = "SettingFragment";
    private FragmentSettingBinding binding;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) requireActivity()).setOnPressBackListener(mActivity -> mActivity.getNavController().popBackStack());
        editor = ((MainActivity) requireActivity()).getEditor();
        setView();
        binding.encrypted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("encrypt", isChecked);
            Config.encrypted = isChecked;
            setView();
        });
        binding.usePasswordEncryption.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("usePasswordEncryption", isChecked);
            Config.usePasswordEncryption = isChecked;
            binding.password.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            binding.useFingerprintEncryption.setItemEnabled(isChecked);
        });
        binding.password.setOnClickListener(v -> new PasswordKeyboardDialog(requireContext(), input -> {
            Config.password = input;
            editor.putString("password", input);
        }));
        binding.useFingerprintEncryption.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("useFingerprintEncryption", isChecked);
            Config.useFingerprintEncryption = isChecked;
            buttonView.setChecked(isChecked);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        editor.apply();
    }

    private void setView() {
        // TODO: 2021/6/28 DataBinding
        if (Config.encrypted) {
            // 使用加密
            // 加密按钮置为已选中，PIN加密按钮置为可交互状态
            binding.encrypted.setChecked(true);
            binding.usePasswordEncryption.setItemEnabled(true);
            if (Config.usePasswordEncryption) {
                // 启用PIN加密
                // 按钮置为已选中，指纹加密按钮置为可交互，设置密码按钮置为可见
                binding.usePasswordEncryption.setChecked(true);
                binding.usePasswordEncryption.setItemEnabled(true);
                binding.password.setVisibility(View.VISIBLE);
                if (Config.useFingerprintEncryption) {
                    // 使用指纹加密，指纹加密按钮置为已选中
                    binding.useFingerprintEncryption.setChecked(true);
                }
            } else {
                // 未启用PIN验证
                // 指纹验证不可用，设置密码按钮不可见
                binding.useFingerprintEncryption.setItemEnabled(false);
                binding.password.setVisibility(View.GONE);
            }
        } else {
            binding.usePasswordEncryption.setItemEnabled(false);
        }
    }
}