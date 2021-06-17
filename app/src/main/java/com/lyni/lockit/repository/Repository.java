package com.lyni.lockit.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.lyni.lockit.model.database.RecordDatabase;
import com.lyni.lockit.model.entity.record.Record;
import com.lyni.lockit.ui.LockitApplication;

import java.util.List;

/**
 * @author Liangyong Ni
 * description 仓库类，用于数据的获取与修改
 * @date 2021/6/13
 */
public class Repository {
    private static final RecordDatabase DATABASE = Room.databaseBuilder(LockitApplication.getContext(),
            RecordDatabase.class, "record.db")
            .allowMainThreadQueries()
            .build();


    public static LiveData<List<Record>> getAllRecordsLive() {
        return DATABASE.recordDao().getAllRecordsLive();
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
