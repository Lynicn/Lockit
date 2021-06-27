package com.lyni.lockit.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.lyni.lockit.R;
import com.lyni.lockit.databinding.ActivityMainBinding;
import com.lyni.lockit.ui.listener.OnPressBackListener;

import java.util.Objects;

/**
 * @author Liangyong Ni
 * description 主Activity
 * @date 2021/6/13
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private OnPressBackListener onPressBackListener;
    private NavController navController;
    private SharedPreferences sp;

    public NavController getNavController() {
        if (navController == null) {
            navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        }
        return navController;
    }

    public void setOnPressBackListener(OnPressBackListener onPressBackListener) {
        this.onPressBackListener = onPressBackListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navController = ((NavHostFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView))).getNavController();
        // 伪沉浸式状态栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        Config.checkFingerprint(this);
        Config.setConfigBySharedPreferences(sp);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (onPressBackListener != null) {
            onPressBackListener.onPressBack(this);
            // 确保每次设置的返回事件监听器只执行一次
            onPressBackListener = null;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        LockitApplication.setAuthenticated(false);
    }

    public SharedPreferences.Editor getEditor() {
        return sp.edit();
    }
}