package com.lyni.lockit.ui.settings;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;

import androidx.navigation.Navigation;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.lyni.lockit.R;
import com.lyni.lockit.ui.Config;
import com.lyni.lockit.ui.LockitApplication;
import com.lyni.lockit.ui.MainActivity;

/**
 * @author Liangyong Ni
 * description 设置界面
 * @date 2021/6/22
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String TAG = "SettingsFragment";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        ((MainActivity) requireActivity()).setOnPressBackListener(mActivity -> Navigation.findNavController(requireView()).popBackStack());
        SwitchPreferenceCompat encrypt = findPreference("encrypt");
        SwitchPreferenceCompat useFingerprintEncryption = findPreference("useFingerprintEncryption");
        SwitchPreferenceCompat usePasswordEncryption = findPreference("usePasswordEncryption");
        EditTextPreference password = findPreference("password");

        assert encrypt != null;
        assert useFingerprintEncryption != null;
        assert usePasswordEncryption != null;
        assert password != null;
        password.setVisible(Config.usePasswordEncryption && Config.encrypted);
        if (!encrypt.isChecked()) {
            useFingerprintEncryption.setEnabled(false);
            usePasswordEncryption.setEnabled(false);
        }
        encrypt.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean checked = (boolean) newValue;
            if (Config.supportFingerprint) {
                useFingerprintEncryption.setEnabled(checked);
            }
            usePasswordEncryption.setEnabled(checked);
            Config.encrypted = checked;
            return true;
        });
        useFingerprintEncryption.setOnPreferenceChangeListener((preference, newValue) -> {
            Config.useFingerprintEncryption = (boolean) newValue;
            return true;
        });
        usePasswordEncryption.setOnPreferenceChangeListener((preference, newValue) -> {
            Config.usePasswordEncryption = (boolean) newValue;
            password.setVisible(Config.usePasswordEncryption);
            return true;
        });
        password.setOnBindEditTextListener(editText -> {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        });
        LockitApplication.setAuthenticated(true);
    }
}