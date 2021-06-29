package com.lyni.lockit.model.entity.record;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.lyni.lockit.model.entity.converters.AccountConverter;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Liangyong Ni
 * description 账户实体类，可序列化
 * @date 2021/6/13
 */
@Entity(tableName = "record_table")
@TypeConverters({AccountConverter.class})
public class Record implements Parcelable {
    public static final Creator<Record> CREATOR = new Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };
    /**
     * 记录序号，由 {@link UUID} 生成唯一的字符串
     */
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    /**
     * 应用名
     */
    @ColumnInfo(name = "name")
    private String name;
    /**
     * 包名
     */
    @ColumnInfo(name = "package_name")
    private String packageName = "default";
    /**
     * 网址
     */
    @ColumnInfo(name = "url")
    private String url;
    /**
     * 账户
     */
    @ColumnInfo(name = "account")
    private Account account;

    public Record() {
        id = UUID.randomUUID().toString();
    }

    protected Record(Parcel in) {
        id = in.readString();
        name = in.readString();
        packageName = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(packageName);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    /**
     * 判断该记录是否完整
     *
     * @return 记录准备好返回true，否则false
     */
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

    @NotNull
    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", url='" + url + '\'' +
                ", account=" + account +
                '}';
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

    /**
     * 返回记录的深拷贝
     *
     * @return 深拷贝
     */
    public Record getCopy() {
        Record record = new Record();
        record.setId(id);
        record.setName(name);
        record.setUrl(url);
        record.setPackageName(packageName);
        record.setAccount(account.getCopy());
        return record;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Record record = (Record) o;
        return id.equals(record.id) &&
                Objects.equals(name, record.name) &&
                Objects.equals(packageName, record.packageName) &&
                Objects.equals(url, record.url) &&
                account.equals(record.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, packageName, url, account);
    }
}
