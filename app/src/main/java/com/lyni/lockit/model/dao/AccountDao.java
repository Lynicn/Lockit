package com.lyni.lockit.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lyni.lockit.model.entity.record.Account;

import java.util.List;

/**
 * @author Liangyong Ni
 * description 账户数据库的dao
 * @date 2021/6/13
 */
@Dao
public interface AccountDao {
    /**
     * 查询数据库中的所有数据
     *
     * @return 返回LiveData形式的结果
     */
    @Query("select * from account_table")
    LiveData<List<Account>> getAllAccountsLive();

    /**
     * 查询一组id对应的Account
     *
     * @param idList 需要查询的id集合
     * @return 查询结果
     */
    @Query("select * from account_table where id in (:idList)")
    LiveData<List<Account>> getSelectedAccounts(List<Long> idList);

    /**
     * 向数据库插入一条或多条数据
     *
     * @param accounts 需要插入的数据
     */
    @Insert
    void insertAccounts(Account... accounts);

    /**
     * 从数据库删除一条或多条数据
     *
     * @param accounts 需要删除的数据
     */
    @Delete
    void deleteRecords(Account... accounts);

    /**
     * 更新一条或多条数据
     *
     * @param accounts 需要更新的数据
     */
    @Update
    void updateRecords(Account... accounts);

    /**
     * 删除所有无归属应用的账号
     */
    @Query("delete from account_table where rc <= 0")
    void deleteUnusedAccounts();
}
