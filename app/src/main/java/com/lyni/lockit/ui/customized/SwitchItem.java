package com.lyni.lockit.ui.customized;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lyni.lockit.R;

/**
 * @author Liangyong Ni
 * description 带选择开关的条目
 * @date 2021/6/28
 */
public class SwitchItem extends RelativeLayout {
    private final ImageView icon;
    private final TextView title, subTitle;
    private final SwitchButton switchButton;

    public SwitchItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View itemView = LayoutInflater.from(context).inflate(R.layout.switch_item, null, false);
        icon = itemView.findViewById(R.id.si_icon);
        title = itemView.findViewById(R.id.si_title);
        subTitle = itemView.findViewById(R.id.si_sub_title);
        switchButton = itemView.findViewById(R.id.si_switch);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchItem);
        if (typedArray != null) {
            icon.setImageResource(typedArray.getResourceId(R.styleable.SwitchItem_icon, R.drawable.ic_app_icon_24));
            title.setText(typedArray.getString(R.styleable.SwitchItem_titleText));
            subTitle.setText(typedArray.getString(R.styleable.SwitchItem_subTitleText));
            switchButton.setEnabled(typedArray.getBoolean(R.styleable.SwitchItem_enabled, true));
            if (!typedArray.getBoolean(R.styleable.SwitchItem_hasSubTitle, false)) {
                subTitle.setVisibility(GONE);
                ((LayoutParams) title.getLayoutParams()).addRule(CENTER_VERTICAL);
            }
        }
        addView(itemView);
    }

    public SwitchItem(Context context) {
        this(context, null);
    }

    public boolean isChecked() {
        return switchButton.isChecked();
    }

    public void setChecked(boolean checked) {
        switchButton.setChecked(checked);
    }

    public void setOnCheckedChangeListener(@NonNull CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        switchButton.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    public SwitchItem setItemEnabled(boolean enabled) {
        switchButton.setEnabled(enabled);
        return this;
    }
}
