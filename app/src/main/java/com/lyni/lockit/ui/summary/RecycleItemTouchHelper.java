package com.lyni.lockit.ui.summary;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.lyni.lockit.R;
import com.lyni.lockit.ui.LockitApplication;

/**
 * @author Liangyong Ni
 * description TODO
 * @date 2021/10/12
 */
public class RecycleItemTouchHelper extends ItemTouchHelper.Callback {
    private static final String TAG = "RecycleItemTouchHelper";
    private final ItemTouchHelperCallback helperCallback;

    public RecycleItemTouchHelper(ItemTouchHelperCallback helperCallback) {
        this.helperCallback = helperCallback;
    }

    /**
     * 设置滑动类型标记
     *
     * @param recyclerView recycler view
     * @param viewHolder   view holder
     * @return 返回一个整数类型的标识，用于判断Item那种移动行为是允许的
     */
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //START  右向左 END左向右 LEFT  向左 RIGHT向右  UP向上
        //如果某个值传0，表示不触发该操作，次数设置支持上下拖拽，支持向右滑动
        return makeMovementFlags(0, ItemTouchHelper.START);
    }

    /**
     * Item是否支持长按拖动
     *
     * @return true  支持长按操作
     * false 不支持长按操作
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }

    /**
     * Item是否支持滑动
     *
     * @return true  支持滑动操作
     * false 不支持滑动操作
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }

    /**
     * 拖拽切换Item的回调
     *
     * @param recyclerView recycler view
     * @param viewHolder   view holder
     * @param target       target
     * @return 如果Item切换了位置，返回true；反之，返回false
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        helperCallback.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    /**
     * 滑动Item
     *
     * @param viewHolder view holder
     * @param direction  Item滑动的方向
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        helperCallback.onItemDelete(viewHolder.getAdapterPosition());
    }

    /**
     * Item被选中时候回调
     *
     * @param viewHolder  view holder
     * @param actionState 当前Item的状态
     *                    ItemTouchHelper.ACTION_STATE_IDLE   闲置状态
     *                    ItemTouchHelper.ACTION_STATE_SWIPE  滑动中状态
     *                    ItemTouchHelper#ACTION_STATE_DRAG   拖拽中状态
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 移动过程中绘制Item
     *
     * @param c                 canvas
     * @param recyclerView      recycler view
     * @param viewHolder        view holder
     * @param dX                X轴移动的距离
     * @param dY                Y轴移动的距离
     * @param actionState       当前Item的状态
     * @param isCurrentlyActive 如果当前被用户操作为true，反之为false
     */
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //滑动时自己实现背景及图片
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

            //dX大于0时向右滑动，小于0向左滑动
            // 获取滑动的view
            View itemView = viewHolder.itemView;
            Resources resources = LockitApplication.getContext().getResources();
            Drawable vectorDrawable = LockitApplication.getContext().getDrawable(R.drawable.ic_delete_32);
            Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);

            // 图片绘制的padding
            final int padding = 10;
            // 最大的绘制宽度
            int maxDrawWidth = 2 * padding + bitmap.getWidth();
            Paint paint = new Paint();
            paint.setColor(resources.getColor(R.color.red_light));
            int distance = Math.round(Math.abs(dX));
            int itemTop = itemView.getBottom() - itemView.getHeight();
            int itemRight = itemView.getRight();
            //向左滑动
            if (dX < 0) {
                //根据滑动实时绘制一个背景
                c.drawRect(itemRight, itemTop, itemRight - distance, itemView.getBottom(), paint);
                if (distance > padding * 2) {
                    //滑动距离大于padding * 2时开始绘制图片
                    //指定图片绘制的位置
                    Rect rect = new Rect();
                    if (distance > maxDrawWidth * 2) {
                        rect.left = itemRight - distance / 2 - padding;
                        rect.right = rect.left + bitmap.getWidth();
                    } else if (distance <= maxDrawWidth) {
                        rect.right = itemRight - padding;
                        rect.left = itemRight - distance + padding;
                    } else {
                        rect.right = itemRight - padding;
                        rect.left = rect.right - bitmap.getWidth();
                    }
                    //图片居中
                    rect.top = itemTop + (itemView.getBottom() - itemTop - bitmap.getHeight()) / 2;
                    rect.bottom = rect.top + bitmap.getHeight();
                    c.drawBitmap(bitmap, null, rect, paint);
                }
                //绘制时需调用平移动画，否则滑动看不到反馈
                itemView.setTranslationX(dX);
                float alpha = 1.0f - Math.abs(dX) / (float) itemView.getWidth();
                itemView.setAlpha(alpha);
            } else {
                // 交由系统处理
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        } else {
            //拖动时有系统自己完成
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    public interface ItemTouchHelperCallback {
        /**
         * 删除时调用
         *
         * @param position 删除的位置
         */
        void onItemDelete(int position);

        /**
         * 移动时调用
         *
         * @param fromPosition 起始位置
         * @param toPosition   目标位置
         */
        void onMove(int fromPosition, int toPosition);
    }
}
