package com.zhuliyi.woju.utils;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * gson的工具类
 * Created by Leory on 2016/8/5.
 */
public class GsonUtil {

    /**
     * 获取接口数据
     *
     * @param response
     * @param <T>
     * @return
     */
    public static <T> T getParser(String response, Class<T> cls) {

        T parser = new Gson().fromJson(response, cls);
        return parser;
    }

    public static <T> List<T> getParserList(String json, Class<T> cls) {

        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, cls));
        }
        return arrayList;
    }

    public static String getResponseList(String response) {

        JsonElement jsonElement = new JsonParser().parse(response);
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = (JsonObject) jsonElement;
            String jsonArray = jsonObject.get("data").toString();
            return jsonArray;
        }
        return null;
    }
}
