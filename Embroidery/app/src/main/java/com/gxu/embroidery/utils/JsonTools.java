package com.gxu.embroidery.utils;

import com.gxu.embroidery.db.domain.Admin;
import com.gxu.embroidery.db.domain.Save;
import com.gxu.embroidery.db.domain.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 根据业务的需求数据类型
 * 完成对json数据的解析
 *
 * @author jack
 */
public class JsonTools {

    public JsonTools() {
    }

    public static User getUser(String key, String jsonString) {
        User user = new User();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject userObject = jsonObject.getJSONObject(key);
            user.setU_id(userObject.getInt("u_id"));
            user.setU_num(userObject.getString("u_num"));
            user.setU_name(userObject.getString("u_name"));
            user.setU_pwd(userObject.getString("u_pwd"));
            user.setU_phone(userObject.getString("u_phone"));
            user.setU_img(userObject.getString("u_img"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static Save getSave(String key, String jsonString) {
        Save save = new Save();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject userObject = jsonObject.getJSONObject(key);
            save.setId(userObject.getInt("id"));
            save.setU_num(userObject.getString("u_num"));
            save.setS_table(userObject.getString("s_table"));
            save.setS_num(userObject.getString("s_num"));
            save.setS_title(userObject.getString("s_title"));
            save.setS_img(userObject.getString("s_img"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return save;
    }

    public static List<User> getListUser(String key, String jsonString) {
        List<User> list = new ArrayList<User>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                User user = new User();
                user.setU_id(jsonObject2.getInt("u_id"));
                user.setU_num(jsonObject2.getString("u_num"));
                user.setU_name(jsonObject2.getString("u_name"));
                user.setU_pwd(jsonObject2.getString("u_pwd"));
                user.setU_phone(jsonObject2.getString("u_phone"));
                user.setU_img(jsonObject2.getString("u_img"));
                list.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Admin getAdmin(String key, String jsonString) {
        Admin admin = new Admin();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject adminObject = jsonObject.getJSONObject(key);
            admin.setM_num(adminObject.getString("m_num"));
            admin.setM_name(adminObject.getString("m_name"));
            admin.setM_pwd(adminObject.getString("m_pwd"));
            admin.setM_phone(adminObject.getString("m_phone"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
    }

    public static List<Admin> getAdmins(String key, String jsonString) {
        List<Admin> list = new ArrayList<Admin>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Admin admin = new Admin();
                admin.setM_id(jsonObject2.getInt("m_id"));
                admin.setM_num(jsonObject2.getString("m_num"));
                admin.setM_name(jsonObject2.getString("m_name"));
                admin.setM_pwd(jsonObject2.getString("m_pwd"));
                admin.setM_phone(jsonObject2.getString("m_phone"));
                list.add(admin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Save> getSaves(String key, String jsonString) {
        List<Save> list = new ArrayList<Save>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Save save = new Save();
                save.setId(jsonObject2.getInt("id"));
                save.setU_num(jsonObject2.getString("u_num"));
                save.setS_table(jsonObject2.getString("s_table"));
                save.setS_num(jsonObject2.getString("s_num"));
                save.setS_title(jsonObject2.getString("s_title"));
                save.setS_img(jsonObject2.getString("s_img"));
                list.add(save);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> getList(String key, String jsonString) {
        List<String> list = new ArrayList<String>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                String msg = jsonArray.getString(i);
                list.add(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    //将json数据转换成map
    public static Map<String, Object> getMaps(String key, String jsonString) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonObject2 = jsonObject.getJSONObject(key);
            Iterator<String> iterator = jsonObject2.keys();
            while (iterator.hasNext()) {
                String json_key = iterator.next();
                Object json_value = jsonObject2.get(json_key);
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


    public static List<Map<String, Object>> listKeyMaps(String key,
                                                        String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Map<String, Object> map = new HashMap<String, Object>();
                Iterator<String> iterator = jsonObject2.keys();
                while (iterator.hasNext()) {
                    String json_key = iterator.next();
                    Object json_value = jsonObject2.get(json_key);
                    if (json_value == null) {
                        json_value = "";
                    }
                    map.put(json_key, json_value);
                }
                list.add(map);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }
}
