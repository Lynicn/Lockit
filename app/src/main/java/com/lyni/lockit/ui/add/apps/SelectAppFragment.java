package com.lyni.lockit.ui.add.apps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lyni.lockit.databinding.FragmentSelectAppBinding;
import com.lyni.lockit.repository.Repository;

import org.jetbrains.annotations.NotNull;

/**
 * @author Liangyong Ni
 * description 选择应用界面
 * @date 2021/6/17
 */
public class SelectAppFragment extends Fragment {

    FragmentSelectAppBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectAppBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppsAdapter appsAdapter = new AppsAdapter(this);
        appsAdapter.setApps(Repository.getInstalledApps());
        binding.appsShow.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.appsShow.setAdapter(appsAdapter);
        binding.sysAppsShow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                appsAdapter.setApps(Repository.getAllApps());
            } else {
                appsAdapter.setApps(Repository.getInstalledApps());
            }
            appsAdapter.notifyDataSetChanged();
        });
    }
}