package com.lyni.lockit.model.entity.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liangyong Ni
 * description 列表转换器基类
 * @date 2021/6/16
 */
public class BaseListConverter<T> {
    @TypeConverter
    public List<T> jsonToList(String jsonString) {
        try {
            Type type = new TypeToken<ArrayList<T>>() {
            }.getType();
            return new Gson().fromJson(jsonString, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public String listToJson(List<T> list) {
        return new Gson().toJson(list);
    }
}
