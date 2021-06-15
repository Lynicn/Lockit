package com.lyni.lockit.model.entity.record;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.lyni.lockit.model.entity.converters.BitmapConverter;
import com.lyni.lockit.model.entity.converters.IntListConverter;

import java.util.List;

/**
 * @author Liangyong Ni
 * description 账户实体类
 * @date 2021/6/13
 */
@Entity(tableName = "record_table", foreignKeys = @ForeignKey(entity = Account.class,
        parentColumns = "id",
        childColumns = "login_ways"))
@TypeConverters({IntListConverter.class, BitmapConverter.class})
public class Record {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "r_id")
    private long id;
    @ColumnInfo(name = "r_name")
    private String name;
    @ColumnInfo(name = "r_package_name")
    private String packageName;
    @ColumnInfo(name = "r_icon")
    private Bitmap icon;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "login_ways")
    private List<Long> loginWays;
    @ColumnInfo(name = "accounts")
    private List<Long> accounts;
    @ColumnInfo(name = "count")
    private Integer count = 0;

    public Record() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Long> getLoginWays() {
        return loginWays;
    }

    public void setLoginWays(List<Long> loginWays) {
        this.loginWays = loginWays;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public List<Long> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Long> accounts) {
        this.accounts = accounts;
    }

    public void add() {
        count++;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
