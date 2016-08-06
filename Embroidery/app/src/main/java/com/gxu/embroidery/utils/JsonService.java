package com.gxu.embroidery.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2015/2/8.
 */
public class JsonService {

    public JsonService() {

    }


    public static String createJsonString(Map<String,String> map) {
        JSONObject jsonObject = new JSONObject();
        try {
            for (String key : map.keySet()) {
                jsonObject.put(key, map.get(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    public static String createJsonString(String key, Object value) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
