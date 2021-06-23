package com.lyni.lockit.ui.add.apps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lyni.lockit.R;
import com.lyni.lockit.databinding.FragmentSelectAppBinding;
import com.lyni.lockit.repository.Repository;
import com.lyni.lockit.ui.MainActivity;
import com.lyni.lockit.ui.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

/**
 * @author Liangyong Ni
 * description 选择应用界面
 * @date 2021/6/17
 */
public class SelectAppFragment extends BaseFragment {

    private FragmentSelectAppBinding binding;
    private AppsAdapter appsAdapter;
    private boolean isAll;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectAppBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String keyString = "from";
        // true表示由添加页面跳转
        assert getArguments() != null;
        final boolean from = getArguments().getBoolean(keyString);
        appsAdapter = new AppsAdapter(this, from);
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
            isAll = isChecked;
        });
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                appsAdapter.setApps(Repository.findAppsByName(query, isAll));
                appsAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                appsAdapter.setApps(Repository.findAppsByName(newText, isAll));
                appsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        ((MainActivity) requireActivity()).setOnPressBackListener(mActivity -> mActivity.getNavController().popBackStack(from ? R.id.addRecordFragment : R.id.detailsFragment, false));
    }
}