package com.lyni.lockit.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lyni.lockit.model.dao.RecordDao;
import com.lyni.lockit.model.entity.record.Account;
import com.lyni.lockit.model.entity.record.Record;

/**
 * @author Liangyong Ni
 * description 数据库
 * @date 2021/6/13
 */
@Database(entities = {Record.class, Account.class}, version = 1, exportSchema = false)
public abstract class RecordDatabase extends RoomDatabase {
    /**
     * 得到操作数据库的Dao对象
     *
     * @return 数据库的Dao对象
     */
    public abstract RecordDao recordDao();
}
