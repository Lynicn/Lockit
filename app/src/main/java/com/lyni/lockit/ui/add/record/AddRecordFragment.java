package com.lyni.lockit.ui.add.record;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
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
import com.lyni.lockit.repository.ThreadPool;
import com.lyni.lockit.ui.MainActivity;
import com.lyni.lockit.ui.base.BaseFragment;
import com.lyni.lockit.ui.customized.dialog.SimpleInputDialog;
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
    FragmentAddRecordBinding binding;
    /**
     * 记录
     */
    private Record newRecord;
    /**
     * 记录对应的account
     */
    private Account mainAccount;
    /**
     * 记录对应的应用信息
     */
    private AppInfo appInfo;
    /**
     * 对话框，{@link SimpleInputDialog}
     */
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

        // 提前扫描手机App，准备应用选择页面需要的应用信息
        ThreadPool.executeTasks(Repository::scanApps);

        // 取消自动保存
        binding.appName.setSaveEnabled(false);
        // 设置返回事件
        ((MainActivity) requireActivity()).setOnPressBackListener(mActivity -> mActivity.getNavController().popBackStack(R.id.summaryFragment, false));

        simpleInputDialog = new SimpleInputDialog(requireContext());

        // 初始化新纪录
        newRecord = new Record();
        mainAccount = new Account();
        newRecord.setAccount(mainAccount);

        // 设置页面初始显示
        setBasicByAppInfo();
        // 设置EditText焦点改变监听器
        setEditTextFocusChangedListener();
        // 设置图片按钮点击事件
        setImageButtonClickListener();
        // 设置选择App按钮监听器，跳转到选择App界面
        binding.selectApp.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("from", true);
            Navigation.findNavController(requireView()).navigate(R.id.action_addRecordFragment_to_selectAppFragment, bundle);
        });
        // 设置完成按钮监听器
        binding.done.setOnClickListener(v -> {
            // 清除焦点
            binding.appName.clearFocus();
            binding.appUrl.clearFocus();
            binding.accountUsername.clearFocus();
            binding.accountUid.clearFocus();
            binding.accountPassword.clearFocus();
            binding.accountNotes.clearFocus();

            // 检查是否记录是否准备好
            if (!mainAccount.isReady()) {
                ToastUtil.show("账号Id、用户名、登录方式至少填写一项");
                return;
            }
            if (!newRecord.isReady()) {
                ToastUtil.show("应用名、网址至少填写一项");
                return;
            }

            // 插入记录
            Repository.insert(newRecord);

            // 插入AppInfo
            if (appInfo != null) {
                Repository.insert(appInfo);
            }
            Navigation.findNavController(requireView()).popBackStack();
        });
    }

    /**
     * 设置页面初始显示
     */
    private void setBasicByAppInfo() {
        if (appInfo != null) {
            newRecord.setName(appInfo.getName());
            newRecord.setPackageName(appInfo.getPackageName());
            binding.selectApp.setImageDrawable(appInfo.getIcon());
            binding.appName.setText(appInfo.getName());
        }
    }

    /**
     * 设置图片按钮点击事件
     */
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
    }

    /**
     * 设置EditText焦点改变监听器，各个组件失去焦点时，保存文本
     */
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
        // 注销订阅者
        EventBus.getDefault().unregister(this);
        // 清除App信息缓存
        Repository.cleanAppCache();
    }

    /**
     * 订阅事件处理
     *
     * @param message 收到的消息
     */
    @Subscribe
    public void messageHandler(Message message) {
        if (message.getMessageType() == MessageType.SA_AR_APP_INFO) {
            appInfo = (AppInfo) message.getObject();
        }
    }
}