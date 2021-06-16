package com.lyni.lockit.ui.summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lyni.lockit.R;
import com.lyni.lockit.databinding.FragmentSummaryBinding;

/**
 * @author Liangyong Ni
 * description 首页
 * @date 2021/6/13
 */
public class SummaryFragment extends Fragment {

    private SummaryViewModel mViewModel;
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
        mViewModel = new ViewModelProvider(this).get(SummaryViewModel.class);
        SummaryAdapter adapter = new SummaryAdapter(this);
        binding.summaryRecords.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.summaryRecords.setAdapter(adapter);
        mViewModel.getRecords().observe(getViewLifecycleOwner(), records -> {
            adapter.setRecords(records);
            adapter.notifyDataSetChanged();
        });

        binding.summaryAddRecord.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(R.id.action_summaryFragment_to_addRecordFragment));

    }
}