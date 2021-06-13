package com.lyni.lockit.ui.summary;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyni.lockit.R;
import com.lyni.lockit.databinding.FragmentSummaryBinding;

public class SummaryFragment extends Fragment {

    private SummaryViewModel mViewModel;
    private FragmentSummaryBinding binding;

    public static SummaryFragment newInstance() {
        return new SummaryFragment();
    }

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
        binding.navigateBtn.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(R.id.action_summaryFragment_to_detailsFragment));
    }

}