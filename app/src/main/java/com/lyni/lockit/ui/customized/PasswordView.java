package com.lyni.lockit.ui.customized;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyni.lockit.R;
import com.lyni.lockit.ui.listener.OnEnsureListener;

import java.util.ArrayList;

/**
 * @author Liangyong Ni
 * description 自定义密码输入控件
 * @date 2021/6/23
 */
public class PasswordView extends RelativeLayout {
    private final int MAX_LENGTH = 12;
    private final int NUM_MAX = 9;
    private final int INPUT_NUMBER_MAX_LENGTH = 6;
    private final Context context;
    /**
     * 用数组保存6个TextView
     */
    private final TextView[] tvList;
    /**
     * 用GridView布局键盘，模拟键盘的功能
     */
    private final GridView gridView;
    private final ArrayList<String> valueList;
    /**
     * GridView的适配器
     */
    private final BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return valueList.size();
        }

        @Override
        public Object getItem(int position) {
            return valueList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_grid, null);
                viewHolder = new ViewHolder();
                viewHolder.btnKey = convertView.findViewById(R.id.keyboard_key);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.btnKey.setText(valueList.get(position));
            if (position == NUM_MAX) {
                viewHolder.btnKey.setClickable(false);
            }
            return convertView;
        }
    };
    /**
     * 输入的密码
     */
    private String strPassword;
    /**
     * 记录当前输入密码格位置
     */
    private int currentIndex = -1;

    public PasswordView(Context context) {
        this(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = View.inflate(context, R.layout.password_input, null);
        valueList = new ArrayList<>();
        tvList = new TextView[INPUT_NUMBER_MAX_LENGTH];

        tvList[0] = view.findViewById(R.id.tv_pass1);
        tvList[1] = view.findViewById(R.id.tv_pass2);
        tvList[2] = view.findViewById(R.id.tv_pass3);
        tvList[3] = view.findViewById(R.id.tv_pass4);
        tvList[4] = view.findViewById(R.id.tv_pass5);
        tvList[5] = view.findViewById(R.id.tv_pass6);

        gridView = view.findViewById(R.id.gv_keyboard);
        setView();
        addView(view);
    }

    /**
     * 准备数据并设置
     */
    private void setView() {
        for (int i = 0; i < MAX_LENGTH; i++) {
            if (i < 9) {
                valueList.add(String.valueOf(i + 1));
            } else if (i == NUM_MAX) {
                valueList.add("");
            } else if (i == NUM_MAX + 1) {
                valueList.add("0");
            } else {
                valueList.add("＜");
            }
        }

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            if (position < NUM_MAX || position == NUM_MAX + 1) {
                //点击0~9按钮
                //判断输入位置，注意数组越界
                if (currentIndex >= -1 && currentIndex < INPUT_NUMBER_MAX_LENGTH - 1) {
                    tvList[++currentIndex].setText(valueList.get(position));
                }
            } else {
                if (position == MAX_LENGTH - 1) {
                    //点击退格键
                    if (currentIndex - 1 >= -1) {
                        tvList[currentIndex--].setText("");
                    }
                }
            }
        });
    }

    /**
     * 输入完成回调
     *
     * @param onEnsureListener 回调
     */
    public void setOnInputFinishListener(OnEnsureListener onEnsureListener) {
        tvList[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
                    strPassword = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < INPUT_NUMBER_MAX_LENGTH; i++) {
                        stringBuilder.append(tvList[i].getText().toString().trim());
                    }
                    strPassword = stringBuilder.toString();
                    onEnsureListener.onEnsure(strPassword);
                }
            }
        });
    }

    /**
     * 清空输入
     */
    public void cleanPassword() {
        currentIndex = -1;
        for (TextView textView : tvList) {
            textView.setText(null);
        }
    }


    /**
     * 存放控件
     */
    public static final class ViewHolder {
        public TextView btnKey;
    }
}