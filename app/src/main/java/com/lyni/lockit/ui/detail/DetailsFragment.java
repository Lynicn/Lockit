package com.lyni.lockit.ui.detail;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.lyni.lockit.R;
import com.lyni.lockit.databinding.FragmentDetailsBinding;
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
import com.lyni.lockit.utils.clipboard.ClipboardUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author Liangyong Ni
 * description 详情界面
 * @date 2021/6/13
 */
public class DetailsFragment extends BaseFragment {
    FragmentDetailsBinding binding;
    /**
     * 首页传递过来的Record
     */
    private Record currentRecord;
    /**
     * 可能产生修改后的Record
     */
    private Record record;
    /**
     * 当前选中的登录方式
     */
    private int loginWay = 0;
    /**
     * 账户
     */
    private Account account;
    /**
     * 记录Id
     */
    private String recordId;
    /**
     * 输入框
     */
    private SimpleInputDialog inputDialog;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        inputDialog = new SimpleInputDialog(requireContext());
        String keyString = "record";
        if (getArguments() != null && getArguments().containsKey(keyString)) {
            // 得到需要显示信息的记录
            currentRecord = getArguments().getParcelable(keyString);
            record = currentRecord.getCopy();
            account = record.getAccount();
            recordId = record.getId();
            inputDialog = new SimpleInputDialog(requireContext());
            loginWay = account.getMinLoginWay();
        } else {
            ToastUtil.show("程序内部错误 (●ˇ∀ˇ●)");
            ((MainActivity) requireActivity()).getNavController().popBackStack(R.id.summaryFragment, false);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 扫描所有应用
        ThreadPool.executeTasks(Repository::scanApps);

        // 初始化界面
        initView();
        // 设置复制按钮监听器
        setCopyImageButtonClickListener();
        // 设置登陆方式点击监听器
        setLoginWaysClickListener();
        // 设置TextView监听器
        setTextViewClickListener();

        // 设置引用图片
        binding.detailsSelectApp.setImageDrawable(Repository.getIconByPackageName(record.getPackageName()));
        // 重新选择应用
        binding.detailsSelectApp.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("from", false);
            Navigation.findNavController(requireView()).navigate(R.id.action_detailsFragment_to_selectAppFragment, bundle);
        });

        // 删除
        binding.detailsDelete.setOnClickListener(v -> new AlertDialog.Builder(requireContext())
                .setMessage("是否删除记录？")
                .setPositiveButton("是", (dialog, which) -> {
                    Repository.deleteRecordsByIds(recordId);
                    ToastUtil.show("删除成功(*ꈍ꒙ꈍ*)");
                    Navigation.findNavController(requireView()).popBackStack(R.id.summaryFragment, false);
                })
                .setNegativeButton("否", null)
                .create().show());

        // 重新设置返回事件
        ((MainActivity) requireActivity()).setOnPressBackListener(mActivity -> {
            if (record.equals(currentRecord)) {
                mActivity.getNavController().popBackStack();
                return;
            }
            new AlertDialog.Builder(requireContext())
                    .setMessage("是否保存修改？")
                    .setPositiveButton("是", (dialog, which) -> {
                        Repository.update(record);
                        mActivity.getNavController().popBackStack();
                    })
                    .setNegativeButton("否", null)
                    .create().show();
        });
    }

    /**
     * 初始化界面
     */
    private void initView() {
        binding.detailsAppName.setText(record.getName() == null ? "--" : record.getName());
        binding.detailsAppUrl.setText(record.getUrl());
        binding.detailsAccountUid.setText(account.getUid() == null ? "--" : account.getUid());
        binding.detailsAccountUsername.setText(account.getUid() == null ? "--" : account.getUsername());
        binding.detailsAccountPassword.setText(account.getUid() == null ? "--" : account.getPassword());
        binding.detailsAccountNotes.setText(account.getNotes() == null ? "无备注" : account.getNotes());
        binding.detailsAccountNotes.setMovementMethod(ScrollingMovementMethod.getInstance());
        setLoginWayId();
    }

    /**
     * 设置TextView监听器
     */
    private void setTextViewClickListener() {
        binding.detailsAppName.setOnClickListener(v -> inputDialog.getInstance("应用名称", R.drawable.ic_android_round_24, input -> {
            record.setName(input);
            ((TextView) v).setText(input);
        }, null).show());
        binding.detailsAppUrl.setOnClickListener(v -> inputDialog.getInstance("网址", R.drawable.ic_url_24, input -> {
            record.setUrl(input);
            ((TextView) v).setText(input);
        }, null).show());
        binding.detailsAccountUsername.setOnClickListener(v -> inputDialog.getInstance("用户名", R.drawable.ic_user_22, input -> {
            account.setUsername(input);
            ((TextView) v).setText(input);
        }, null).show());
        binding.detailsAccountUid.setOnClickListener(v -> inputDialog.getInstance("账号ID", R.drawable.ic_id_22, input -> {
            account.setUid(input);
            ((TextView) v).setText(input);
        }, null).show());
        binding.detailsAccountPassword.setOnClickListener(v -> inputDialog.getInstance("密码", R.drawable.ic_password_22, input -> {
            account.setPassword(input);
            ((TextView) v).setText(input);
        }, null).show());
        binding.detailsAccountNotesModifier.setOnClickListener(v -> inputDialog.getInstance("备注", R.drawable.ic_notes_24, input -> {
            account.setNotes(input);
            binding.detailsAccountNotes.setText(input);
        }, null).show());

        binding.loginWayInfoText.setOnClickListener(v -> inputDialog.getInstance(input -> {
            account.setLoginWayText(loginWay, input);
            ((TextView) v).setText(input);
        }).show());
    }

    /**
     * 设置复制按钮监听器
     */
    private void setCopyImageButtonClickListener() {
        binding.uidCopy.setOnClickListener(v -> ClipboardUtil.copy(requireContext(), account.getUid()));
        binding.usernameCopy.setOnClickListener(v -> ClipboardUtil.copy(requireContext(), account.getUsername()));
        binding.passwordCopy.setOnClickListener(v -> ClipboardUtil.copy(requireContext(), account.getPassword()));
        binding.loginWayInfoCopy.setOnClickListener(v -> ClipboardUtil.copy(requireContext(), binding.loginWayInfoText.getText().toString()));
    }

    /**
     * 设置登陆方式点击监听器
     */
    private void setLoginWaysClickListener() {
        binding.teleBinding.setOnClickListener(v -> {
            loginWay = 0;
            setLoginWayId();
        });
        binding.emailBinding.setOnClickListener(v -> {
            loginWay = 1;
            setLoginWayId();
        });
        binding.qqBinding.setOnClickListener(v -> {
            loginWay = 2;
            setLoginWayId();
        });
        binding.wechatBinding.setOnClickListener(v -> {
            loginWay = 3;
            setLoginWayId();
        });
        binding.weiboBinding.setOnClickListener(v -> {
            loginWay = 4;
            setLoginWayId();
        });
        binding.alipayBinding.setOnClickListener(v -> {
            loginWay = 5;
            setLoginWayId();
        });
    }

    /**
     * 设置当前登陆方式
     */
    private void setLoginWayId() {
        switch (loginWay) {
            case 0:
                binding.loginWayInfoIcon.setImageResource(R.drawable.ic_phone_round_28);
                binding.loginWayInfoText.setText(account.getTele() == null ? "--" : account.getTele());
                break;
            case 1:
                binding.loginWayInfoIcon.setImageResource(R.drawable.ic_email_round_28);
                binding.loginWayInfoText.setText(account.getEmail() == null ? "--" : account.getEmail());
                break;
            case 2:
                binding.loginWayInfoIcon.setImageResource(R.drawable.ic_qq_28);
                binding.loginWayInfoText.setText(account.getQq() == null ? "--" : account.getQq());
                break;
            case 3:
                binding.loginWayInfoIcon.setImageResource(R.drawable.ic_wechat_28);
                binding.loginWayInfoText.setText(account.getWechat() == null ? "--" : account.getWechat());
                break;
            case 4:
                binding.loginWayInfoIcon.setImageResource(R.drawable.ic_weibo_28);
                binding.loginWayInfoText.setText(account.getWeibo() == null ? "--" : account.getWeibo());
                break;
            case 5:
                binding.loginWayInfoIcon.setImageResource(R.drawable.ic_alipay_28);
                binding.loginWayInfoText.setText(account.getAlipay() == null ? "--" : account.getAlipay());
                break;
            default:
                break;
        }
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
     * 事件处理
     *
     * @param message 事件
     */
    @Subscribe
    public void messageHandler(Message message) {
        if (message.getMessageType() == MessageType.SA_D_APP_INFO) {
            AppInfo appInfo = (AppInfo) message.getObject();
            record.setName(appInfo.getName());
            record.setPackageName(appInfo.getPackageName());
            binding.detailsAppName.setText(record.getName());
            binding.detailsSelectApp.setImageDrawable(appInfo.getIcon());
        }
    }
}