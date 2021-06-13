package com.lyni.lockit.ui.detail;

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
import com.lyni.lockit.databinding.FragmentDetailsBinding;

/**
 *
 */
public class DetailsFragment extends Fragment {

    FragmentDetailsBinding binding;
    private DetailsViewModel mViewModel;

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
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
        mViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        binding.returnBtn.setOnClickListener(v -> Navigation.findNavController(requireView()).popBackStack());
    }

}