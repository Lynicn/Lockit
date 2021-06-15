package com.lyni.lockit.ui.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lyni.lockit.R;
import com.lyni.lockit.model.entity.converters.BitmapConverter;
import com.lyni.lockit.model.entity.record.Account;
import com.lyni.lockit.model.entity.record.Record;

import java.util.List;

/**
 * @author Liangyong Ni
 * description 登录方式ListView的适配器
 * @date 2021/6/15
 */
public class LoginWaysAdapter extends ArrayAdapter<Account> {
    private final int resourceId;
    private final Record currentRecord;
    private List<Account> accounts;


    public LoginWaysAdapter(@NonNull Context context, int resource, @NonNull List<Account> objects, Record currentRecord) {
        super(context, resource, objects);
        accounts = objects;
        resourceId = resource;
        this.currentRecord = currentRecord;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Account currentAccount = getItem(position);
        ViewHolder viewHolder;
        View view;
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.item_lv_details_tv);
            view.setTag(viewHolder);
        }
        assert currentAccount != null;
        viewHolder.textView.setText(currentAccount.getUid());
        viewHolder.textView.setCompoundDrawables(BitmapConverter.bitmapToDrawable(currentRecord.getIcon()), null, null, null);
        return view;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Nullable
    @Override
    public Account getItem(int position) {
        return accounts.get(position);
    }

    private static class ViewHolder {
        TextView textView;
    }
}
