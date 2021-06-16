package com.lyni.lockit.ui.add.record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lyni.lockit.R;
import com.lyni.lockit.databinding.FragmentAddRecordBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @author Liangyong Ni
 * description 添加账户界面
 * @date 2021/6/15
 */
public class AddRecordFragment extends Fragment {
    FragmentAddRecordBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddRecordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.addAccount.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(R.id.action_addRecordFragment_to_addAccountFragment));
    }
}