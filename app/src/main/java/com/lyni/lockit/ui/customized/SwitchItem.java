package com.lyni.lockit.ui.customized;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.card.MaterialCardView;
import com.lyni.lockit.R;

import static android.widget.RelativeLayout.CENTER_VERTICAL;

/**
 * @author Liangyong Ni
 * description 带选择开关的条目
 * @date 2021/6/28
 */
public class SwitchItem extends MaterialCardView {
    private final SwitchButton switchButton;

    public SwitchItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        MaterialCardView itemView = (MaterialCardView) View.inflate(context, R.layout.switch_item, null);
        ImageView icon = itemView.findViewById(R.id.si_icon);
        TextView title = itemView.findViewById(R.id.si_title);
        TextView subTitle = itemView.findViewById(R.id.si_sub_title);
        switchButton = itemView.findViewById(R.id.si_switch);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchItem);
        if (typedArray != null) {
            icon.setImageResource(typedArray.getResourceId(R.styleable.SwitchItem_icon, R.drawable.ic_app_icon_24));
            title.setText(typedArray.getString(R.styleable.SwitchItem_titleText));
            subTitle.setText(typedArray.getString(R.styleable.SwitchItem_subTitleText));
            switchButton.setEnabled(typedArray.getBoolean(R.styleable.SwitchItem_enabled, true));
            if (!typedArray.getBoolean(R.styleable.SwitchItem_hasSubTitle, false)) {
                subTitle.setVisibility(GONE);
                ((RelativeLayout.LayoutParams) title.getLayoutParams()).addRule(CENTER_VERTICAL);
            }
            typedArray.recycle();
        }
        addView(itemView);
    }

    public SwitchItem(Context context) {
        this(context, null);
    }


    @Override
    public void setOnClickListener(@NonNull OnClickListener onClickListener) {
        switchButton.setOnClickListener(onClickListener);
    }


    @Override
    public void setEnabled(boolean enabled) {
        switchButton.setEnabled(enabled);
    }

    @Override
    public void setChecked(boolean checked) {
        switchButton.setChecked(checked);
    }
}
