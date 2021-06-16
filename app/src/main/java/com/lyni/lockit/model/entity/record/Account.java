package com.lyni.lockit.model.entity.record;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.lyni.lockit.model.entity.converters.LoginWayListConverter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * @author Liangyong Ni
 * description 账号实体类
 * @date 2021/6/13
 */
@Entity
@TypeConverters(LoginWayListConverter.class)
public class Account {
    @PrimaryKey
    @NonNull
    private String id;
    private String uid;
    private String username;
    private String password;
    private String notes;
    private String tele;
    private String email;
    private String qq;
    private String wechat;
    private String alipay;
    private String weibo;
    private List<LoginWay> loginWays;

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public Account() {
        id = UUID.randomUUID().toString();
    }

    public boolean isReady() {
        boolean isReady = false;
        if (username != null || uid != null) {
            if (password != null) {
                isReady = true;
            }
        }
        if (tele != null || email != null || qq != null || wechat != null || alipay != null || weibo != null) {
            isReady = true;
        }
        return isReady;
    }

    public List<LoginWay> getLoginWays() {
        return loginWays;
    }

    public void setLoginWays(List<LoginWay> loginWays) {
        this.loginWays = loginWays;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
