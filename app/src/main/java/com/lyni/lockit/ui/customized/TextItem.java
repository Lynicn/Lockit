package com.lyni.lockit.ui.customized;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.lyni.lockit.R;

/**
 * @author Liangyong Ni
 * description 自定义控件
 * @date 2021/6/28
 */
public class TextItem extends MaterialCardView {
    /**
     * 整个MaterialCardView
     */
    private final MaterialCardView itemView;

    public TextItem(Context context) {
        this(context, null);
    }

    public TextItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        itemView = (MaterialCardView) View.inflate(context, R.layout.text_item, null);
        TextView title = itemView.findViewById(R.id.ti_title);
        TextView subTitle = itemView.findViewById(R.id.ti_sub_title);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextItem);
        if (typedArray != null) {
            if (typedArray.getBoolean(R.styleable.TextItem_hasSubTitle, false)) {
                subTitle.setVisibility(VISIBLE);
            }
            title.setText(typedArray.getString(R.styleable.TextItem_titleText));
            subTitle.setText(typedArray.getString(R.styleable.TextItem_subTitleText));
            ((RelativeLayout.LayoutParams) title.getLayoutParams()).leftMargin = typedArray.getDimensionPixelSize(R.styleable.TextItem_toStart, 0);
            typedArray.recycle();
        }
        addView(itemView);
    }

    /**
     * 设置点击监听
     * @param onClickListener 监听器
     */
    public void setOnTextItemClickListener(OnClickListener onClickListener) {
        itemView.setOnClickListener(onClickListener);
    }
}
