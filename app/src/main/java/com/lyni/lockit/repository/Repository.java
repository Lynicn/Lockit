package com.lyni.lockit.repository;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.lyni.lockit.R;
import com.lyni.lockit.model.database.RecordDatabase;
import com.lyni.lockit.model.entity.record.AppInfo;
import com.lyni.lockit.model.entity.record.Record;
import com.lyni.lockit.ui.LockitApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Liangyong Ni
 * description 仓库类，用于数据的获取与修改
 * @date 2021/6/13
 */
// TODO: 2021/6/18
@SuppressLint("UseCompatLoadingForDrawables")
public class Repository {
    private static final String TAG = "Repository";
    private static final RecordDatabase DATABASE = Room.databaseBuilder(LockitApplication.getContext(),
            RecordDatabase.class, "record.db")
            .allowMainThreadQueries()
            .build();
    private static final ArrayList<AppInfo> INSTALLED_APPS = new ArrayList<>();
    private static final ArrayList<AppInfo> ALL_APPS = new ArrayList<>();
    private static final HashMap<String, Drawable> ICON_CACHE = new HashMap<>();

    static {
        // TODO: 2021/6/18
        new Thread(() -> {
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
                // TODO: 2021/6/19 图片获取问题
                ICON_CACHE.put(appInfo.getPackageName(), appInfo.getIcon());
            }
        }).start();
        ICON_CACHE.put("null", LockitApplication.getContext().getDrawable(R.drawable.ic_android_round_28));
    }

    public static ArrayList<AppInfo> getInstalledApps() {
        return INSTALLED_APPS;
    }

    public static ArrayList<AppInfo> getAllApps() {
        return ALL_APPS;
    }

    public static LiveData<List<Record>> getAllRecordsLive() {
        return DATABASE.recordDao().getAllRecordsLive();
    }

    public static Drawable getIconByPackageName(String packageName) {
        Log.e(TAG, "getIconByPackageName: " + packageName);
        if (packageName == null) {
            return ICON_CACHE.get("null");
        }
        if (!ICON_CACHE.containsKey(packageName)) {
            AppInfo appInfo = DATABASE.iconDao().getIconByPackageName(packageName);
            if (appInfo != null) {
                ICON_CACHE.put(packageName, appInfo.getIcon());
                return ICON_CACHE.get(packageName);
            }
            return ICON_CACHE.get("null");
        }
        return ICON_CACHE.get(packageName);
    }

    public static void insert(AppInfo appInfo) {
        DATABASE.iconDao().insert(appInfo);
    }

    public static LiveData<Record> findRecordLiveById(String id) {
        return DATABASE.recordDao().getRecordLiveById(id);
    }

    public static boolean deleteRecordsByIds(String... ids) {
        DATABASE.recordDao().deleteRecordsByIds(ids);
        List<Record> records = DATABASE.recordDao().queryRecordByIds(ids);
        return records == null || records.isEmpty();
    }

    public static void insert(Record... records) {
        DATABASE.recordDao().insertRecords(records);
    }

    public static void delete(Record... records) {
        DATABASE.recordDao().deleteRecords(records);
    }

    public static void update(Record... records) {
        DATABASE.recordDao().updateRecords(records);
    }

    public static Record findRecordById(String id) {
        return DATABASE.recordDao().findById(id).get(0);
    }

}
