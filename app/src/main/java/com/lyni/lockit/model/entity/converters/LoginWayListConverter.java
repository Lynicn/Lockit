package com.lyni.lockit.model.entity.converters;

import androidx.room.TypeConverter;

import com.lyni.lockit.model.entity.record.LoginWay;

import java.util.List;

/**
 * @author Liangyong Ni
 * description 登录方式转换类
 * @date 2021/6/16
 */
public class LoginWayListConverter extends BaseListConverter<LoginWay> {
    @TypeConverter
    public List<LoginWay> jsonToList(String jsonString) {
        return super.jsonToList(jsonString, LoginWay.class);
    }

    @TypeConverter
    @Override
    public String listToJson(List<LoginWay> list) {
        return super.listToJson(list);
    }
}
