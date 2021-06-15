package com.lyni.lockit.ui.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lyni.lockit.databinding.FragmentAddBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @author Liangyong Ni
 * description 添加账户界面
 * @date 2021/6/15
 */
public class AddFragment extends Fragment {

//    private boolean isAdd = true;

//    private Record record;

    FragmentAddBinding binding;
    private AddAccountDialog addAccountDialog;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.addAccount.setOnClickListener(v -> {
            addAccountDialog = new AddAccountDialog(requireContext(), (uid, username, password, email, tele, notes, isMainAccount) -> {

            });
            addAccountDialog.getDialog().show();
        });
    }
}