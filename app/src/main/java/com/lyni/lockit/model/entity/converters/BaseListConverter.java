package com.lyni.lockit.model.entity.converters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Liangyong Ni
 * description 列表转换器基类
 * @date 2021/6/16
 */
public class BaseListConverter<T> {
    public List<T> jsonToList(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<>();
        try {
            Gson gson = new Gson();
            JsonArray array = JsonParser.parseString(jsonString).getAsJsonArray();
            for (JsonElement jsonElement : array) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String listToJson(List<T> list) {
        return new Gson().toJson(list);
    }
}
