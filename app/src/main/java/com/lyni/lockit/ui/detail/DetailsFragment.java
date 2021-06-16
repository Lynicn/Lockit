package com.lyni.lockit.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lyni.lockit.databinding.FragmentDetailsBinding;

/**
 * @author Liangyong Ni
 * description 详情界面
 * @date 2021/6/13
 */
public class DetailsFragment extends Fragment {

    FragmentDetailsBinding binding;
    private String recordId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DetailsViewModel mViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        String keyString = "recordId";

        if (getArguments() != null && getArguments().containsKey(keyString)) {
            recordId = getArguments().getString(keyString);
        }

        mViewModel.setId(recordId);
        mViewModel.setFragment(this);
        mViewModel.getRecord().observe(getViewLifecycleOwner(), record -> {
            binding.detailsAppName.setText(record.getName());
            binding.detailsAppUrl.setText(record.getUrl());
        });

//        AccountsAdapter accountsAdapter = new AccountsAdapter(mViewModel.getAccounts().getValue());
//        binding.accounts.setLayoutManager(new LinearLayoutManager(requireContext()));
//        binding.accounts.setAdapter(accountsAdapter);
//        mViewModel.getAccounts().observe(getViewLifecycleOwner(), account -> {
//            accountsAdapter.setAccounts(account);
//            accountsAdapter.notifyDataSetChanged();
//        });


    }
}