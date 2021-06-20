package com.lyni.lockit.ui.add.record;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.lyni.lockit.R;
import com.lyni.lockit.databinding.FragmentAddRecordBinding;
import com.lyni.lockit.model.entity.message.Message;
import com.lyni.lockit.model.entity.message.MessageType;
import com.lyni.lockit.model.entity.record.Account;
import com.lyni.lockit.model.entity.record.AppInfo;
import com.lyni.lockit.model.entity.record.Record;
import com.lyni.lockit.repository.Repository;
import com.lyni.lockit.ui.MainActivity;
import com.lyni.lockit.ui.base.BaseFragment;
import com.lyni.lockit.ui.dialog.SimpleInputDialog;
import com.lyni.lockit.utils.ToastUtil.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

/**
 * @author Liangyong Ni
 * description 添加账户界面
 * @date 2021/6/15
 */
public class AddRecordFragment extends BaseFragment {
    private static final String TAG = "AddRecordFragment";
    FragmentAddRecordBinding binding;
    private Record newRecord;
    private Account mainAccount;
    private AppInfo appInfo;
    private SimpleInputDialog simpleInputDialog;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddRecordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.appName.setSaveEnabled(false);
        ((MainActivity) requireActivity()).setOnPressBackListener(mActivity -> mActivity.getNavController().popBackStack(R.id.summaryFragment, false));

        simpleInputDialog = new SimpleInputDialog(requireContext());

        newRecord = new Record();
        mainAccount = new Account();
        newRecord.setAccount(mainAccount);

        setBasicByAppInfo();
        setEditTextFocusChangedListener();
        setImageButtonClickListener();
        binding.selectApp.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("from", true);
            Navigation.findNavController(requireView()).navigate(R.id.action_addRecordFragment_to_selectAppFragment, bundle);
        });
        binding.done.setOnClickListener(v -> {
            binding.appName.clearFocus();
            binding.appUrl.clearFocus();
            binding.accountUsername.clearFocus();
            binding.accountUid.clearFocus();
            binding.accountPassword.clearFocus();
            binding.accountNotes.clearFocus();
            if (!mainAccount.isReady()) {
                ToastUtil.show("账号Id、用户名、登录方式至少填写一项");
                return;
            }
            if (!newRecord.isReady()) {
                ToastUtil.show("应用名、网址至少填写一项");
                return;
            }
            Repository.insert(newRecord);
            if (appInfo != null) {
                Repository.insert(appInfo);
            }
            Navigation.findNavController(requireView()).popBackStack();
        });
    }

    private void setBasicByAppInfo() {
        if (appInfo != null) {
            newRecord.setName(appInfo.getName());
            newRecord.setPackageName(appInfo.getPackageName());
            binding.selectApp.setImageDrawable(appInfo.getIcon());
            binding.appName.setText(appInfo.getName());
            Log.e(TAG, "setBasicByAppInfo: " + appInfo.getName());
        }
    }

    private void setImageButtonClickListener() {
        binding.phone.setOnClickListener(v -> {
                    simpleInputDialog.getInstance("请输入手机号",
                            binding.phone.getDrawable(),
                            input -> mainAccount.setTele(input),
                            input -> input.setInputType(InputType.TYPE_CLASS_PHONE))
                            .show();
                    ToastUtil.show("ヾ(≧▽≦*)o");
                }
        );
        binding.email.setOnClickListener(v -> {
            simpleInputDialog.getInstance("请输入电子邮件账号",
                    binding.email.getDrawable(),
                    input -> mainAccount.setEmail(input),
                    input -> input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS))
                    .show();
            ToastUtil.show("φ(*￣0￣)");
        });
        binding.qq.setOnClickListener(v -> {
            simpleInputDialog.getInstance("请输入QQ账号或用户名",
                    binding.qq.getDrawable(),
                    input -> mainAccount.setQq(input),
                    null)
                    .show();
            ToastUtil.show("q(≧▽≦q)");
        });
        binding.wechat.setOnClickListener(v -> {
            simpleInputDialog.getInstance("请输入微信账号或用户名",
                    binding.wechat.getDrawable(),
                    input -> mainAccount.setWechat(input),
                    null)
                    .show();
            ToastUtil.show("*^____^*");
        });
        binding.alipay.setOnClickListener(v -> {
            simpleInputDialog.getInstance("请输入支付宝账号或用户名",
                    binding.alipay.getDrawable(),
                    input -> mainAccount.setAlipay(input),
                    null)
                    .show();
            ToastUtil.show("(*^▽^*)");
        });
        binding.weibo.setOnClickListener(v -> {
            simpleInputDialog.getInstance("请输入微博账号或用户名",
                    binding.weibo.getDrawable(),
                    input -> mainAccount.setWeibo(input),
                    null)
                    .show();
            ToastUtil.show("(≧∇≦)ﾉ");
        });
        binding.addLoginWay.setOnClickListener(v -> ToastUtil.show("该功能暂未实现 ( •̀ ω •́ )✧"));
    }

    private void setEditTextFocusChangedListener() {
        binding.appName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Editable text = binding.appName.getText();
                if (text != null && !TextUtils.isEmpty(text)) {
                    newRecord.setName(text.toString());
                }
            }
        });

        binding.appUrl.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Editable text = binding.appUrl.getText();
                if (text != null && !TextUtils.isEmpty(text)) {
                    newRecord.setUrl(text.toString());
                }
            }
        });

        binding.accountUid.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Editable text = binding.accountUid.getText();
                if (text != null && !TextUtils.isEmpty(text)) {
                    mainAccount.setUid(text.toString());
                }
            }
        });

        binding.accountUsername.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Editable text = binding.accountUsername.getText();
                if (text != null && !TextUtils.isEmpty(text)) {
                    mainAccount.setUsername(text.toString());
                }
            }
        });

        binding.accountPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Editable text = binding.accountPassword.getText();
                if (text != null && !TextUtils.isEmpty(text)) {
                    mainAccount.setPassword(text.toString());
                }
            }
        });
        binding.accountNotes.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Editable text = binding.accountNotes.getText();
                if (text != null && !TextUtils.isEmpty(text)) {
                    mainAccount.setNotes(text.toString());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void messageHandler(Message message) {
        if (message.getMessageType() == MessageType.SA_AR_APP_INFO) {
            appInfo = (AppInfo) message.getObject();
        }
    }
}