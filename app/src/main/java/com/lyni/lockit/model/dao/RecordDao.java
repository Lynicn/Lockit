package com.lyni.lockit.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lyni.lockit.model.entity.record.Record;

import java.util.List;

/**
 * @author Liangyong Ni
 * description 记录数据库的dao
 * @date 2021/6/13
 */
@Dao
public interface RecordDao {


    /**
     * 查询数据库中的所有数据
     *
     * @return 返回LiveData形式的结果
     */
    @Query("select * from record_table")
    LiveData<List<Record>> getAllRecordsLive();

    /**
     * 查询数据库中的指定id的记录
     *
     * @param id 查询记录的id
     * @return 返回LiveData形式的结果
     */
    @Query("select * from record_table where id = (:id)")
    LiveData<Record> getRecordLiveById(String id);

    /**
     * 根据id删除记录
     *
     * @param ids 需要删除的记录id
     */
    @Query("delete from record_table where id in (:ids)")
    void deleteRecordsByIds(String... ids);

    /**
     * 根据id查询记录
     *
     * @param ids 需要查询的记录id
     * @return 查询结果集合
     */
    @Query("select * from record_table where id in (:ids)")
    List<Record> queryRecordByIds(String... ids);

    /**
     * 向数据库插入一条或多条数据
     *
     * @param records 需要插入的数据
     */
    @Insert
    void insertRecords(Record... records);

    /**
     * 从数据库删除一条或多条数据
     *
     * @param records 需要删除的数据
     */
    @Delete
    void deleteRecords(Record... records);

    /**
     * 更新一条或多条数据
     *
     * @param records 需要更新的数据
     */
    @Update
    void updateRecords(Record... records);

    /**
     * 根据id查找对应的记录
     *
     * @param ids 记录id
     * @return 记录列表
     */
    @Query("select * from record_table where id in (:ids)")
    List<Record> findById(String... ids);
}
