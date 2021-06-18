package com.lyni.lockit.model.entity.record;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.lyni.lockit.model.entity.converters.BitmapConverter;

import org.jetbrains.annotations.NotNull;

/**
 * @author Liangyong Ni
 * description 储存应用信息的实体类
 * @date 2021/6/16
 */
@Entity(tableName = "icon_table")
@TypeConverters(BitmapConverter.class)
public class AppInfo implements Parcelable {
    public static final Creator<AppInfo> CREATOR = new Creator<AppInfo>() {
        @Override
        public AppInfo createFromParcel(Parcel in) {
            return new AppInfo(in);
        }

        @Override
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "unique_name")
    private String packageName;
    @Ignore
    private String name;
    @ColumnInfo(name = "icon")
    private Drawable icon;

    public AppInfo() {
    }

    protected AppInfo(Parcel in) {
        packageName = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(packageName);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @NotNull
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(@NotNull String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    @NotNull
    @Override
    public String toString() {
        return "AppInfo{" +
                "packageName='" + packageName + '\'' +
                ", name='" + name + '\'' +
                ", icon=" + icon +
                '}';
    }
}
