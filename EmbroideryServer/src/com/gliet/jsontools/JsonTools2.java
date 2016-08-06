package com.gliet.jsontools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;


public class JsonTools2 {

	public JsonTools2() {
		super();
		// TODO 自动生成的构造函数存根
	}

	//将json数据转换成map
	public static Map<String, Object> getMaps(String jsonString) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			Iterator<String> iterator = jsonObject.keys();
			while (iterator.hasNext()) {
				String json_key = iterator.next();
				Object json_value = jsonObject.get(json_key);
				if (json_value == null) {
					json_value = "";
				}
				map.put(json_key, json_value);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}


}
