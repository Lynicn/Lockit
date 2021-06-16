package com.lyni.lockit.model.entity.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Liangyong Ni
 * description 基本对象转换类
 * @date 2021/6/16
 */
public class BaseObjectConverter<T> {
    @TypeConverter
    public T jsonToObject(String jsonString) {
        try {
            return new Gson().fromJson(jsonString, new TypeToken<T>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public String objectToJson(T t) {
        return new Gson().toJson(t);
    }
}
