package com.lyni.lockit.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lyni.lockit.R;
import com.lyni.lockit.ui.LockitApplication;

/**
 * @author Liangyong Ni
 * description Fragment基类
 * @date 2021/6/21
 */
public class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";


    @Override
    public void onStart() {
        super.onStart();
        if (!LockitApplication.isAuthenticated()) {
            Navigation.findNavController(requireView()).navigate(R.id.authenticateFragment);
        }
    }

}
