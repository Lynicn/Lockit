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

    @TypeConverter
    public String objectToJson(Account account) {
        return new Gson().toJson(account);
    }
}
