package com.lyni.lockit.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.lyni.lockit.model.entity.record.AppInfo;

import java.util.List;

/**
 * @author Liangyong Ni
 * description icon_table的dao
 * @date 2021/6/18
 */
@Dao
public interface IconDao {
    /**
     * 查询所有的包名对应的icon
     *
     * @return AppInfo对象集合
     */
    @Query("select * from icon_table")
    List<AppInfo> getAllIcons();

    /**
     * 查询包名对应的icon
     *
     * @param packageName 包名
     * @return 对应的AppInfo对象
     */
    @Query("select * from icon_table where unique_name = :packageName")
    AppInfo getIconByPackageName(String packageName);

    /**
     * 向数据库插入图标
     *
     * @param appInfo 需要插入的对象
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AppInfo appInfo);
}
