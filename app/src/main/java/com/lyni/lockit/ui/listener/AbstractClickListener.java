package com.lyni.lockit.ui.listener;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Liangyong Ni
 * description 双击事件监听器
 * @date 2021/6/18
 */
@Deprecated
public abstract class AbstractClickListener implements View.OnTouchListener {

    /**
     * 双击延迟
     */
    private static final int TIMEOUT = 160;
    private final Handler handler;
    private final MyClickCallBack myClickCallBack;
    /**
     * 连续点击次数
     */
    private int clickCount = 0;

    public AbstractClickListener(MyClickCallBack myClickCallBack, Handler handler) {
        this.myClickCallBack = myClickCallBack;
        this.handler = handler;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            clickCount++;
            //延时timeout后执行run方法中的代码
            handler.postDelayed(() -> {
                if (clickCount == 1) {
                    myClickCallBack.onSingleClick();
                } else if (clickCount == 2) {
                    myClickCallBack.onDoubleClick();
                }
                //清空handler延时，并防内存泄漏
                handler.removeCallbacksAndMessages(null);
                //计数清零
                clickCount = 0;
            }, TIMEOUT);
        }
        //让点击事件继续传播，方便再给View添加其他事件监听
        return false;
    }

    /**
     * 点击回调
     */
    public interface MyClickCallBack {
        /**
         * 双击接口
         */
        void onDoubleClick();

        /**
         * 单击接口
         */
        void onSingleClick();

    }


}
