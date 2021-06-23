package com.lyni.lockit.ui.add.apps;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.lyni.lockit.R;
import com.lyni.lockit.model.entity.message.Message;
import com.lyni.lockit.model.entity.message.MessageType;
import com.lyni.lockit.model.entity.record.AppInfo;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Liangyong Ni
 * description 显示App的适配器
 * @date 2021/6/18
 */
public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.MyViewHolder> {

    private final Fragment fragment;
    private final boolean from;
    private List<AppInfo> apps;

    public AppsAdapter(Fragment fragment, boolean from) {
        this.fragment = fragment;
        this.from = from;
    }

    public void setApps(List<AppInfo> apps) {
        this.apps = apps;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        AppInfo appInfo = apps.get(position);
        holder.app.setText(appInfo.getName());
        Drawable icon = appInfo.getIcon();
        icon.setBounds(0, 0, 80, 80);
        holder.app.setCompoundDrawablesRelative(icon, null, null, null);
        holder.item.setOnClickListener(v -> {
            EventBus.getDefault().post(new Message(from ? MessageType.SA_AR_APP_INFO : MessageType.SA_D_APP_INFO, appInfo));
            Navigation.findNavController(fragment.requireView()).popBackStack();
        });
    }

    @Override
    public int getItemCount() {
        return apps == null ? 0 : apps.size();
    }

    protected static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView app;
        private final MaterialCardView item;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            app = itemView.findViewById(R.id.item_rv_search_app);
            item = itemView.findViewById(R.id.item_rv_search_item);
        }
    }
}
