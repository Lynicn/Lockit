package com.lyni.lockit.ui.base;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lyni.lockit.R;
import com.lyni.lockit.ui.Config;
import com.lyni.lockit.ui.LockitApplication;

/**
 * @author Liangyong Ni
 * description Fragment基类
 * @date 2021/6/21
 */
public class BaseFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
        if (Config.encrypted) {
            if (Config.useFingerprintEncryption || Config.usePasswordEncryption) {
                if (!LockitApplication.isAuthenticated()) {
                    Navigation.findNavController(requireView()).navigate(R.id.authenticateFragment);
                }
            }
        } else {
            LockitApplication.setAuthenticated(true);
        }
    }
}
