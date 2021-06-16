package com.lyni.lockit.ui.add.record;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lyni.lockit.databinding.FragmentAddRecordBinding;
import com.lyni.lockit.model.entity.record.Account;
import com.lyni.lockit.model.entity.record.Record;
import com.lyni.lockit.repository.Repository;
import com.lyni.lockit.utils.ToastUtil.ToastUtil;

import org.jetbrains.annotations.NotNull;

/**
 * @author Liangyong Ni
 * description 添加账户界面
 * @date 2021/6/15
 */
public class AddRecordFragment extends Fragment {
    FragmentAddRecordBinding binding;
    private Record newRecord;
    private Account mainAccount;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddRecordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newRecord = new Record();
        mainAccount = new Account();
        newRecord.setAccount(mainAccount);

        setEditTextFocusChangedListener();

        binding.done.setOnClickListener(v -> {
            if (!newRecord.isReady()) {
                ToastUtil.show("应用名、网址至少填写一项");
                return;
            }
            if (!mainAccount.isReady()) {
                ToastUtil.show("账号Id、用户名、登录方式至少填写一项");
                return;
            }
            Repository.insert(newRecord);
        });
    }

    private void setEditTextFocusChangedListener() {
        binding.appName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Editable text = binding.appName.getText();
                if (text != null && !TextUtils.isEmpty(text)) {
                    newRecord.setName(text.toString());
                    ToastUtil.show(text.toString());
                }
            }
        });

        binding.appUrl.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Editable text = binding.appUrl.getText();
                if (text != null && !TextUtils.isEmpty(text)) {
                    newRecord.setUrl(text.toString());
                    ToastUtil.show(text.toString());
                }
            }
        });

        binding.accountId.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Editable text = binding.accountId.getText();
                if (text != null && !TextUtils.isEmpty(text)) {
                    mainAccount.setUid(text.toString());
                    ToastUtil.show(text.toString());
                }
            }
        });

        binding.accountUsername.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Editable text = binding.accountUsername.getText();
                if (text != null && !TextUtils.isEmpty(text)) {
                    mainAccount.setUsername(text.toString());
                    ToastUtil.show(text.toString());
                }
            }
        });

        binding.accountPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Editable text = binding.accountPassword.getText();
                if (text != null && !TextUtils.isEmpty(text)) {
                    mainAccount.setPassword(text.toString());
                    ToastUtil.show(text.toString());
                }
            }
        });
    }
}