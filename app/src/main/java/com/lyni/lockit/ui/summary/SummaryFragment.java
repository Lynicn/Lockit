package com.lyni.lockit.ui.summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lyni.lockit.R;
import com.lyni.lockit.databinding.FragmentSummaryBinding;
import com.lyni.lockit.ui.base.BaseFragment;
import com.lyni.lockit.utils.ToastUtil.ToastUtil;

/**
 * @author Liangyong Ni
 * description 首页
 * @date 2021/6/13
 */
public class SummaryFragment extends BaseFragment {

    private FragmentSummaryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSummaryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SummaryViewModel mViewModel = new ViewModelProvider(this).get(SummaryViewModel.class);
        SummaryAdapter adapter = new SummaryAdapter(this);
        binding.summaryRecords.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.summaryRecords.setAdapter(adapter);
        mViewModel.getRecords().observe(getViewLifecycleOwner(), records -> {
            adapter.setRecords(records);
            adapter.notifyDataSetChanged();
        });
        binding.summarySearch.setOnClickListener(v -> ToastUtil.show("开发中( •̀ ω •́ )✧"));
        // 跳转到添加记录页面
        binding.summaryAddRecord.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(R.id.action_summaryFragment_to_addRecordFragment));
        // 跳转到设置页面
        binding.summarySetting.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(R.id.action_summaryFragment_to_settingFragment));
    }
}