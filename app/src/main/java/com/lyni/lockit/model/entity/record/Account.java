package com.lyni.lockit.model.entity.record;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.lyni.lockit.model.entity.converters.IntListConverter;

/**
 * @author Liangyong Ni
 * description 账号实体类
 * @date 2021/6/13
 */
@Entity(tableName = "account_table", indices = {@Index(value = "id")})
@TypeConverters(IntListConverter.class)
public class Account {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "uid")
    private String uid = "";
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
    @ColumnInfo(name = "linked_app")
    private Long linkedApp;

    public Account() {
    }

    @Ignore
    public Account(String uid, String username, String password) {
        this.uid = uid;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getLinkedApp() {
        return linkedApp;
    }

    public void setLinkedApp(Long linkedApp) {
        this.linkedApp = linkedApp;
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
