package com.lyni.lockit.model.entity.record;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.jetbrains.annotations.NotNull;

/**
 * @author Liangyong Ni
 * description 描述账号登录方式的实体类
 * @date 2021/6/16
 */
@Entity
public class LoginWay {
    /**
     * 登录方式的唯一标识，如应用的包名、网站的网址
     */
    @PrimaryKey
    @ColumnInfo(name = "unique_name")
    @NonNull
    private String uniqueName = "";
    /**
     * 登录方式名称
     */
    @ColumnInfo(name = "name")
    private String name;
    /**
     * 登录方式对应的图标，不存入数据库，根据手机应用列表获取
     */
    @Ignore
    private Drawable icon;
    /**
     * 该种登陆方式的用户Id
     */
    @ColumnInfo(name = "uid")
    private String uid;
    /**
     * 关联的账户Id
     */
    @ColumnInfo(name = "linked_id")
    private String linkedId;

    public LoginWay() {
    }

    @NotNull
    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(@NotNull String uniqueName) {
        this.uniqueName = uniqueName;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLinkedId() {
        return linkedId;
    }

    public void setLinkedId(String linkedId) {
        this.linkedId = linkedId;
    }
}
