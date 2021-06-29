package com.lyni.lockit.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lyni.lockit.model.dao.IconDao;
import com.lyni.lockit.model.dao.RecordDao;
import com.lyni.lockit.model.entity.record.AppInfo;
import com.lyni.lockit.model.entity.record.Record;

/**
 * @author Liangyong Ni
 * description 数据库
 * @date 2021/6/13
 */
@Database(entities = {Record.class, AppInfo.class}, version = 1, exportSchema = false)
public abstract class AbstractRecordDatabase extends RoomDatabase {
    /**
     * 得到record_table的Dao对象
     *
     * @return Dao对象
     */
    public abstract RecordDao recordDao();

    /**
     * 得到icon_table的Dao对象
     *
     * @return Dao对象
     */
    public abstract IconDao iconDao();
}
