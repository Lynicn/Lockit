package com.lyni.lockit.model.entity.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyni.lockit.model.entity.record.Account;

/**
 * @author Liangyong Ni
 * description 账户转化类
 * @date 2021/6/16
 */
public class AccountConverter {
    /**
     * 将Json字符串转化为Account
     *
     * @param jsonString 输入的json
     * @return 转化后的Account对象
     */
    @TypeConverter
    public Account jsonToObject(String jsonString) {
        try {
            return new Gson().fromJson(jsonString, new TypeToken<Account>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Account对象转化为Json字符串
     *
     * @param account 需要转化的对象
     * @return 转化后的字符串
     */
    @TypeConverter
    public String objectToJson(Account account) {
        return new Gson().toJson(account);
    }
}
