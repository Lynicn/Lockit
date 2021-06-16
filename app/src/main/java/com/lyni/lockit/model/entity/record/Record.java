package com.lyni.lockit.model.entity.record;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.lyni.lockit.model.entity.converters.AccountConverter;
import com.lyni.lockit.model.entity.converters.BitmapConverter;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * @author Liangyong Ni
 * description 账户实体类
 * @date 2021/6/13
 */
@Entity(tableName = "record_table")
@TypeConverters({AccountConverter.class, BitmapConverter.class})
public class Record {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "package_name")
    private String packageName;
    @ColumnInfo(name = "icon")
    private Bitmap icon;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "account")
    private Account account;

    public Record() {
        id = UUID.randomUUID().toString();
    }

    public boolean isReady() {
        if (name != null || packageName != null || url != null) {
            return account.isReady();
        }
        return false;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
