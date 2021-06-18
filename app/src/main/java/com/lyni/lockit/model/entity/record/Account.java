package com.lyni.lockit.model.entity.record;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Liangyong Ni
 * description 账号实体类
 * @date 2021/6/13
 */
@Entity
public class Account implements Parcelable {
    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };
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
//    private List<LoginWay> loginWays;

    private String weibo;

    public Account() {
        id = UUID.randomUUID().toString();
    }

    protected Account(Parcel in) {
        id = in.readString();
        uid = in.readString();
        username = in.readString();
        password = in.readString();
        notes = in.readString();
        tele = in.readString();
        email = in.readString();
        qq = in.readString();
        wechat = in.readString();
        alipay = in.readString();
        weibo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(uid);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(notes);
        dest.writeString(tele);
        dest.writeString(email);
        dest.writeString(qq);
        dest.writeString(wechat);
        dest.writeString(alipay);
        dest.writeString(weibo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
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

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
        if (uid == null) {
            uid = qq;
        }
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
        if (uid == null) {
            uid = wechat;
        }
    }

    @NotNull
    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", notes='" + notes + '\'' +
                ", tele='" + tele + '\'' +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                ", wechat='" + wechat + '\'' +
                ", alipay='" + alipay + '\'' +
                ", weibo='" + weibo + '\'' +
                '}';
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
        if (uid == null) {
            uid = alipay;
        }
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
        if (uid == null) {
            uid = weibo;
        }
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
        if (uid == null) {
            uid = tele;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        if (uid == null) {
            uid = email;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return id.equals(account.id) &&
                Objects.equals(uid, account.uid) &&
                Objects.equals(username, account.username) &&
                Objects.equals(password, account.password) &&
                Objects.equals(notes, account.notes) &&
                Objects.equals(tele, account.tele) &&
                Objects.equals(email, account.email) &&
                Objects.equals(qq, account.qq) &&
                Objects.equals(wechat, account.wechat) &&
                Objects.equals(alipay, account.alipay) &&
                Objects.equals(weibo, account.weibo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uid, username, password, notes, tele, email, qq, wechat, alipay, weibo);
    }

    public void setLoginWayText(int loginWay, String text) {
        switch (loginWay) {
            case 0:
                this.tele = text;
                break;
            case 1:
                this.email = text;
                break;
            case 2:
                this.qq = text;
                break;
            case 3:
                this.wechat = text;
                break;
            case 4:
                this.weibo = text;
                break;
            case 5:
                this.alipay = text;
                break;
            default:
                break;
        }
    }

    public int getMinLoginWay() {
        if (tele != null) {
            return 0;
        }
        if (email != null) {
            return 1;
        }
        if (qq != null) {
            return 2;
        }
        if (wechat != null) {
            return 3;
        }
        if (weibo != null) {
            return 4;
        }
        if (alipay != null) {
            return 5;
        }
        return 0;
    }

    public Account getCopy() {
        Account account = new Account();
        account.setId(id);
        account.setUid(uid);
        account.setUsername(username);
        account.setPassword(password);
        account.setNotes(notes);
        account.setTele(tele);
        account.setEmail(email);
        account.setQq(qq);
        account.setWechat(wechat);
        account.setWeibo(weibo);
        account.setAlipay(alipay);
        return account;
    }
}
