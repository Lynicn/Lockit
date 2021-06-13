package com.lyni.lockit.model.entity.record;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.lyni.lockit.model.entity.converters.IntListConverter;

import java.util.List;

/**
 * @author Liangyong Ni
 * description 账号实体类
 * @date 2021/6/13
 */
@Entity(tableName = "account_table",
        foreignKeys = @ForeignKey(entity = Account.class,
                parentColumns = "id",
                childColumns = "login_way"),
        indices = {@Index(value = "id")})
@TypeConverters(IntListConverter.class)
public class Account {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "uid")
    private String uid;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "notes")
    private String notes;
    @ColumnInfo(name = "tele")
    private String tele;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "icon")
    private long icon;
    @ColumnInfo(name = "login_way")
    private Long loginWay;
    @ColumnInfo(name = "linked_accounts")
    private List<Long> linkedAccounts;
    @ColumnInfo(name = "rc")
    private int rc = 1;

    public Account() {
    }

    @Ignore
    public Account(String uid, String username, String password) {
        this.uid = uid;
        this.username = username;
        this.password = password;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getLoginWay() {
        return loginWay;
    }

    public void setLoginWay(Long loginWay) {
        this.loginWay = loginWay;
    }

    public List<Long> getLinkedAccounts() {
        return linkedAccounts;
    }

    public void setLinkedAccounts(List<Long> linkedAccounts) {
        this.linkedAccounts = linkedAccounts;
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

    public long getIcon() {
        return icon;
    }

    public void setIcon(long icon) {
        this.icon = icon;
    }
}
