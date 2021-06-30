package com.lyni.lockit.repository;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.lyni.lockit.R;
import com.lyni.lockit.model.database.AbstractRecordDatabase;
import com.lyni.lockit.model.entity.record.AppInfo;
import com.lyni.lockit.model.entity.record.Record;
import com.lyni.lockit.ui.LockitApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Liangyong Ni
 * description 仓库类，用于数据的获取与修改
 * @date 2021/6/13
 */
// TODO: 2021/6/18

@SuppressLint("UseCompatLoadingForDrawables")
public class Repository {
    /**
     * 数据库实例，单例 {@link AbstractRecordDatabase}
     */
    private static final AbstractRecordDatabase DATABASE = Room.databaseBuilder(LockitApplication.getContext(),
            AbstractRecordDatabase.class, "record.db")
            .allowMainThreadQueries()
            .build();
    /**
     * 已安装的应用信息（不含系统应用）
     */
    private static final ArrayList<AppInfo> INSTALLED_APPS = new ArrayList<>();
    /**
     * 所有应用信息
     */
    private static final ArrayList<AppInfo> ALL_APPS = new ArrayList<>();
    /**
     * 应用图标缓存
     */
    private static final HashMap<String, Drawable> ICON_CACHE = new HashMap<>();

    static {
        // 没有包名的使用默认图标
        ICON_CACHE.put("null", LockitApplication.getContext().getDrawable(R.drawable.ic_android_round_24));
    }

    /**
     * 扫描所有应用
     */
    public static void scanApps() {
        // 获取已经安装的所有应用, PackageInfo　系统类，包含应用信息
        PackageManager packageManager = LockitApplication.getContext().getPackageManager();
        @SuppressLint("QueryPermissionsNeeded") List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            AppInfo appInfo = new AppInfo();
            //获取应用名称
            appInfo.setName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
            //获取应用包名，可用于卸载和启动应用
            appInfo.setPackageName(packageInfo.packageName);
            //获取应用图标
            appInfo.setIcon(packageInfo.applicationInfo.loadIcon(packageManager));
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                //非系统应用
                INSTALLED_APPS.add(appInfo);
            }
            // 系统应用
            ALL_APPS.add(appInfo);
        }
    }

    /**
     * 清除应用信息
     */
    public static void cleanAppCache() {
        INSTALLED_APPS.clear();
        ALL_APPS.clear();
    }


    /**
     * 返回已安装应用列表
     *
     * @return 已安装应用列表
     */
    public static ArrayList<AppInfo> getInstalledApps() {
        return INSTALLED_APPS;
    }

    /**
     * 返回所有应用列表
     *
     * @return 所有应用列表
     */
    public static ArrayList<AppInfo> getAllApps() {
        return ALL_APPS;
    }

    /**
     * 根据名称查找应用（忽略大小写）
     *
     * @param name  应用名
     * @param isAll 是否包含系统应用
     * @return 符合名称的所有应用
     */
    public static ArrayList<AppInfo> findAppsByName(String name, boolean isAll) {
        ArrayList<AppInfo> result = new ArrayList<>();
        ArrayList<AppInfo> list = isAll ? ALL_APPS : INSTALLED_APPS;
        // 忽略大小写
        Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
        for (int i = 0; i < list.size(); i++) {
            Matcher matcher = pattern.matcher((list.get(i)).getName());
            if (matcher.find()) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    /**
     * 返回LiveData形式的所有记录
     *
     * @return LiveData形式的所有记录
     */
    public static LiveData<List<Record>> getAllRecordsLive() {
        return DATABASE.recordDao().getAllRecordsLive();
    }

    /**
     * 根据包名查询查找icon
     *
     * @param packageName 包名
     * @return icon
     */
    public static Drawable getIconByPackageName(String packageName) {
        if (packageName == null) {
            return ICON_CACHE.get("null");
        }
        if (!ICON_CACHE.containsKey(packageName)) {
            AppInfo appInfo = DATABASE.iconDao().getIconByPackageName(packageName);
            if (appInfo != null) {
                ICON_CACHE.put(packageName, appInfo.getIcon());
                return appInfo.getIcon();
            }
            return ICON_CACHE.get("null");
        }
        return ICON_CACHE.get(packageName);
    }

    /**
     * 插入应用信息
     *
     * @param appInfo 应用信息
     */
    public static void insert(AppInfo appInfo) {
        DATABASE.iconDao().insert(appInfo);
    }

    /**
     * 根据id查找记录
     *
     * @param id 需要查找的id
     * @return 查找到的记录的LiveData形式
     */
    public static LiveData<Record> findRecordLiveById(String id) {
        return DATABASE.recordDao().getRecordLiveById(id);
    }

    /**
     * 根据id删除记录
     *
     * @param ids 需要删除的id
     */
    public static void deleteRecordsByIds(String... ids) {
        DATABASE.recordDao().deleteRecordsByIds(ids);
    }

    /**
     * 插入记录
     *
     * @param records 需要插入的记录
     */
    public static void insert(Record... records) {
        DATABASE.recordDao().insertRecords(records);
    }

    /**
     * 更新记录
     *
     * @param records 需要更新的记录
     */
    public static void update(Record... records) {
        DATABASE.recordDao().updateRecords(records);
    }

    /**
     * 根据id查找记录
     *
     * @param id 需要查找的id
     * @return 查找到的记录
     */
    public static Record findRecordById(String id) {
        return DATABASE.recordDao().findById(id).get(0);
    }

}
