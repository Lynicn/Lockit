package com.lyni.lockit.ui.detail;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lyni.lockit.R;
import com.lyni.lockit.databinding.FragmentDetailsBinding;
import com.lyni.lockit.model.entity.record.Account;
import com.lyni.lockit.model.entity.record.Record;
import com.lyni.lockit.repository.Repository;
import com.lyni.lockit.ui.MainActivity;
import com.lyni.lockit.ui.dialog.SimpleInputDialog;
import com.lyni.lockit.utils.ToastUtil.ToastUtil;
import com.lyni.lockit.utils.clipboard.ClipboardUtil;

/**
 * @author Liangyong Ni
 * description 详情界面
 * @date 2021/6/13
 */
public class DetailsFragment extends Fragment {
    FragmentDetailsBinding binding;
    private Record record;
    private int loginWay = 0;
    private Account account;
    private String recordId;
    private SimpleInputDialog inputDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String keyString = "record";
        Record currentRecord;
        if (getArguments() != null && getArguments().containsKey(keyString)) {
            currentRecord = getArguments().getParcelable(keyString);
            record = currentRecord.getCopy();
            account = record.getAccount();
            recordId = record.getId();
            inputDialog = new SimpleInputDialog(requireContext());
            loginWay = account.getMinLoginWay();
        } else {
            ToastUtil.show("程序内部错误 (●ˇ∀ˇ●)");
            Navigation.findNavController(requireView()).popBackStack(R.id.summaryFragment, false);
            return;
        }

        initView();
        setCopyImageButtonClickListener();
        setLoginWaysClickListener();
        setTextViewClickListener();

        binding.detailsDelete.setOnClickListener(v -> {
            if (Repository.deleteRecordsByIds(recordId)) {
                ToastUtil.show("删除成功(*ꈍ꒙ꈍ*)");
                Navigation.findNavController(requireView()).popBackStack(R.id.summaryFragment, false);
            } else {
                ToastUtil.show("删除失败，请重试꒲⌯ ू(ꆧ⚇̭ꆧ ूˆ)");
            }
        });
        ((MainActivity) requireActivity()).setOnPressBackListener(() -> {
            if (record.equals(currentRecord)) {
                Navigation.findNavController(requireView()).popBackStack();
                return;
            }
            new AlertDialog.Builder(requireContext())
                    .setMessage("是否保存修改？")
                    .setPositiveButton("是", (dialog, which) -> {
                        Repository.update(record);
                        Navigation.findNavController(requireView()).popBackStack();
                    })
                    .setNegativeButton("否", null)
                    .create().show();
        });
    }


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

    private void setTextViewClickListener() {
        binding.detailsAppName.setOnClickListener(v -> inputDialog.getInstance("应用名称", R.drawable.ic_android_round_28, input -> {
            record.setName(input);
            ((TextView) v).setText(input);
        }, null).show());
        binding.detailsAppUrl.setOnClickListener(v -> inputDialog.getInstance("网址", R.drawable.ic_url_28, input -> {
            record.setUrl(input);
            ((TextView) v).setText(input);
        }, null).show());
        binding.detailsAccountUsername.setOnClickListener(v -> inputDialog.getInstance("用户名", R.drawable.ic_user_26, input -> {
            account.setUsername(input);
            ((TextView) v).setText(input);
        }, null).show());
        binding.detailsAccountUid.setOnClickListener(v -> inputDialog.getInstance("账号ID", R.drawable.ic_id_26, input -> {
            account.setUid(input);
            ((TextView) v).setText(input);
        }, null).show());
        binding.detailsAccountPassword.setOnClickListener(v -> inputDialog.getInstance("密码", R.drawable.ic_password_24, input -> {
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

    private void setCopyImageButtonClickListener() {
        binding.uidCopy.setOnClickListener(v -> ClipboardUtil.copy(requireContext(), account.getUid()));
        binding.usernameCopy.setOnClickListener(v -> ClipboardUtil.copy(requireContext(), account.getUsername()));
        binding.passwordCopy.setOnClickListener(v -> ClipboardUtil.copy(requireContext(), account.getPassword()));
        binding.loginWayInfoCopy.setOnClickListener(v -> ClipboardUtil.copy(requireContext(), binding.loginWayInfoText.getText().toString()));
    }

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
}