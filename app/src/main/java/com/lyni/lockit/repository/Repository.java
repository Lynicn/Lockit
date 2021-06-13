package com.lyni.lockit.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.lyni.lockit.model.database.RecordDatabase;
import com.lyni.lockit.model.entity.record.Account;
import com.lyni.lockit.model.entity.record.Record;
import com.lyni.lockit.ui.LockitApplication;

import java.util.List;
import java.util.Map;

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

    public static LiveData<List<Account>> getAllAccountsLive() {
        return DATABASE.accountDao().getAllAccountsLive();
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

    public static void insert(Account... accounts) {
        DATABASE.accountDao().insertAccounts(accounts);
    }

    public static void delete(Account... accounts) {
        DATABASE.accountDao().deleteRecords(accounts);
    }

    public static void update(Account... accounts) {
        DATABASE.accountDao().updateRecords(accounts);
    }
}
