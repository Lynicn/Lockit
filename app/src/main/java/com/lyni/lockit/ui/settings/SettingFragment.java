package com.lyni.lockit.ui.settings;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    private FragmentSettingBinding binding;

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
        // 重写返回事件，如果开启加密却没有使用加密方式，将被拦截
        ((MainActivity) requireActivity()).setOnPressBackListener(mActivity -> {
            if (Config.encrypted) {
                if (!(Config.usePasswordEncryption || Config.useFingerprintEncryption)) {
                    new AlertDialog.Builder(requireContext())
                            .setMessage("您开启了加密但没有使用任何加密方式，请关闭加密或至少开启一种加密方式")
                            .setPositiveButton("知道了", null)
                            .create().show();
                    return;
                }
            }
            // 保存设置
            Config.saveConfig();
            mActivity.getNavController().popBackStack();
        });
        // 设置界面
        setView();
        binding.encrypted.setOnClickListener(v -> {
            Config.encrypted = !Config.encrypted;
            setView();
        });
        binding.usePasswordEncryption.setOnClickListener(v -> {
            Config.usePasswordEncryption = !Config.usePasswordEncryption;
            binding.password.setVisibility(Config.usePasswordEncryption ? View.VISIBLE : View.GONE);
        });
        binding.password.setOnTextItemClickListener(v -> new PasswordKeyboardDialog(requireContext(), input -> Config.password = input));
        binding.useFingerprintEncryption.setOnClickListener(v -> {
            Config.useFingerprintEncryption = !Config.useFingerprintEncryption;
            binding.useFingerprintEncryption.setChecked(Config.useFingerprintEncryption);
        });
    }

    private void setView() {
        // TODO: 2021/6/28 DataBinding
        binding.usePasswordEncryption.setChecked(false);
        binding.usePasswordEncryption.setEnabled(false);
        binding.password.setVisibility(View.GONE);
        binding.useFingerprintEncryption.setChecked(false);
        binding.useFingerprintEncryption.setEnabled(false);
        if (Config.encrypted) {
            // 使用加密
            // 加密按钮置为已选中，PIN加密按钮置为可交互状态
            binding.encrypted.setChecked(true);
            binding.usePasswordEncryption.setEnabled(true);
            if (Config.usePasswordEncryption) {
                // 启用PIN加密
                // 按钮置为已选中，设置密码按钮置为可见
                binding.usePasswordEncryption.setChecked(true);
                binding.password.setVisibility(View.VISIBLE);
            }
            if (Config.supportFingerprint) {
                // 设备支持指纹，指纹可交互打开
                binding.useFingerprintEncryption.setEnabled(true);
                if (Config.useFingerprintEncryption) {
                    // 使用指纹加密，指纹加密按钮置为已选中
                    binding.useFingerprintEncryption.setChecked(true);
                }
            }
        }
    }
}