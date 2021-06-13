package com.lyni.lockit.model.entity.converters;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Liangyong Ni
 * description 转换类，提供字符串与集合的相互转换
 * @date 2021/6/13
 */
public class IntListConverter {
    private static final String NULL_STRING = "null";

    @TypeConverter
    public String objectToString(List<Long> list) {
        if (list == null) {
            return NULL_STRING;
        }
        StringBuilder result = new StringBuilder();
        for (Long aLong : list) {
            result.append(aLong).append("-");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    @TypeConverter
    public List<Long> stringToObject(String str) {
        List<Long> list = new ArrayList<>();
        if (NULL_STRING.equals(str)) {
            return list;
        }
        String[] results = str.split("-");

        for (String result : results) {
            list.add(Long.parseLong(result));
        }
        return list;
    }
}
