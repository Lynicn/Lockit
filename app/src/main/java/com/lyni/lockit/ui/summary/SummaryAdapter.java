package com.lyni.lockit.ui.summary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.lyni.lockit.R;
import com.lyni.lockit.model.entity.record.Account;
import com.lyni.lockit.model.entity.record.Record;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Liangyong Ni
 * description Summary界面的适配器
 * @date 2021/6/13
 */
public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.MyViewHolder> {
    private List<Record> records;
    private Map<Long, Account> accountMap;
    private Fragment fragment;

    public SummaryAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public void setAccountMap(Map<Long, Account> accountMap) {
        this.accountMap = accountMap;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_summary, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.icon.setImageBitmap(records.get(position).getIcon());
        holder.appName.setText(records.get(position).getName());
        holder.accountName.setText(Objects.requireNonNull(accountMap.get(records.get(position).getAccounts().get(0))).getUsername());
        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2021/6/13 跳转详情
            }
        });
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    static final class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView icon;
        private final TextView appName;
        private final TextView accountName;
        private final MaterialCardView materialCardView;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_rv_summary_app_icon);
            appName = itemView.findViewById(R.id.item_rv_summary_app_name);
            accountName = itemView.findViewById(R.id.item_rv_summary_account_name);
            materialCardView = itemView.findViewById(R.id.item_rv_summary_mcv);
        }
    }
}
