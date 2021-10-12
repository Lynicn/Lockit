package com.lyni.lockit.ui.summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.lyni.lockit.R;
import com.lyni.lockit.model.entity.record.Account;
import com.lyni.lockit.model.entity.record.Record;
import com.lyni.lockit.repository.Repository;
import com.lyni.lockit.utils.ToastUtil.ToastUtil;
import com.lyni.lockit.utils.clipboard.ClipboardUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Liangyong Ni
 * description Summary界面的适配器
 * @date 2021/6/13
 */
public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.MyViewHolder> implements RecycleItemTouchHelper.ItemTouchHelperCallback {


    private final Fragment fragment;
    private List<Record> records;

    public SummaryAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_summary, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Record record = records.get(position);
        holder.appName.setText(record.getName() == null ? record.getUrl() : record.getName());
        holder.icon.setImageDrawable(Repository.getIconByPackageName(record.getPackageName()));
        final Account recordAccount = record.getAccount();
        holder.accountName.setText(recordAccount.getUid() == null ? recordAccount.getUsername() : recordAccount.getUid());
        if (recordAccount.getTele() != null) {
            holder.tele.setVisibility(View.VISIBLE);
        }
        if (recordAccount.getEmail() != null) {
            holder.email.setVisibility(View.VISIBLE);
        }
        if (recordAccount.getQq() != null) {
            holder.qq.setVisibility(View.VISIBLE);
        }
        if (recordAccount.getWechat() != null) {
            holder.wechat.setVisibility(View.VISIBLE);
        }
        if (recordAccount.getWeibo() != null) {
            holder.weibo.setVisibility(View.VISIBLE);
        }
        if (recordAccount.getAlipay() != null) {
            holder.alipay.setVisibility(View.VISIBLE);
        }

        // 点击跳转到详情
        holder.item.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("record", record);
            Navigation.findNavController(fragment.requireView()).navigate(R.id.action_summaryFragment_to_detailsFragment, bundle);
        });

        // 长按复制密码或者id
        holder.item.setOnLongClickListener(v -> {
            String message = recordAccount.getPassword() == null ? recordAccount.getUid() : recordAccount.getPassword();
            ClipboardUtil.copy(fragment.requireContext(), message);
            ToastUtil.show(message);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return records == null ? 0 : records.size();
    }

    @Override
    public void onItemDelete(int position) {
        records.remove(position);
        notifyItemRemoved(position);
        // TODO: 2021/10/12 数据库删除
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        // TODO: 2021/10/12
    }

    static final class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView icon;

        private final TextView appName;
        private final TextView accountName;
        private final View item;
        private final ImageView tele, email, qq, wechat, weibo, alipay;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            appName = itemView.findViewById(R.id.item_rv_summary_app_name);
            accountName = itemView.findViewById(R.id.item_rv_summary_account_name);
            icon = itemView.findViewById(R.id.item_rv_summary_app_icon);
            item = itemView.findViewById(R.id.item_rv_summary_item);
            tele = itemView.findViewById(R.id.item_rv_summary_tele);
            email = itemView.findViewById(R.id.item_rv_summary_email);
            qq = itemView.findViewById(R.id.item_rv_summary_qq);
            wechat = itemView.findViewById(R.id.item_rv_summary_wechat);
            weibo = itemView.findViewById(R.id.item_rv_summary_weibo);
            alipay = itemView.findViewById(R.id.item_rv_summary_alipay);
        }
    }
}
