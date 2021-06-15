package com.lyni.lockit.ui.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.lyni.lockit.R;
import com.lyni.lockit.model.entity.record.Account;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Liangyong Ni
 * description 账户列表RecyclerView的适配器
 * @date 2021/6/15
 */
public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.MyViewHolder> {

    private List<Account> accounts;

    public AccountsAdapter(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_details, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Account account = accounts.get(position);
        holder.id.setText(account.getUid());
        holder.username.setText(account.getUsername());
        holder.password.setText(account.getPassword());
        holder.idCopy.setOnClickListener(v -> copyToClipboard(account.getUid()));
        holder.passwordCopy.setOnClickListener(v -> copyToClipboard(account.getPassword()));
        holder.accountInfo.setOnClickListener(v -> {
            // TODO: 2021/6/15 账户点击逻辑
        });
    }

    private void copyToClipboard(String copyString) {
        // TODO: 2021/6/15 复制到剪贴板的逻辑
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    protected static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView id, username, password;
        private final ImageView idCopy, passwordCopy;
        private final MaterialCardView accountInfo;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.item_rv_details_id);
            username = itemView.findViewById(R.id.item_rv_details_username);
            password = itemView.findViewById(R.id.item_rv_details_password);
            idCopy = itemView.findViewById(R.id.item_rv_details_copy_id);
            passwordCopy = itemView.findViewById(R.id.item_rv_details_copy_pw);
            accountInfo = itemView.findViewById(R.id.accountInfo);
        }
    }
}
