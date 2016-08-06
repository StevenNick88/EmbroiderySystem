package com.gxu.embroidery.utils;

public class CommonUrl {

    public static final String BASE_URL = "http://192.168.253.1:8080/";
//    public static final String BASE_URL = "http://192.168.43.24:8080/";
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    public static final String UPLOAD_FILE = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=upload_file";
    public static final String LOAD_IMG_TITLE = BASE_URL + "EmbroideryServer/title/";
    public static final String LOAD_IMG_DETAIL = BASE_URL + "EmbroideryServer/detail/";
    public static final String LOAD_IMG_HEAD = BASE_URL + "EmbroideryServer/head/";

    //注册
    public static final String USER_LAST_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=user_last_url";
    public static final String ADMIN_LAST_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=admin_last_url";

    //向服务器添加user表中单条记录的url
    public static final String ADD_USER_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=adduser";
    //获得栏目的所有数据组
    public static final String HOME_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=home_url";
    public static final String APPRECIATION_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=appreciation_url";
    public static final String INFORMATION_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=information_url";
    public static final String QANDA_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=qanda_url";
    public static final String SHUXIU_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=shuxiu_url";
    public static final String SUXIU_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=suxiu_url";
    public static final String XIANGXIU_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=xiangxiu_url";
    public static final String YUXIU_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=yuxiu_url";

    public static String[] GET_ALL_COLUMN_DATA_URL = new String[]{
            CommonUrl.HOME_URL,
            CommonUrl.INFORMATION_URL,
            CommonUrl.XIANGXIU_URL,
            CommonUrl.SHUXIU_URL,
            CommonUrl.YUXIU_URL,
            CommonUrl.SUXIU_URL,
            CommonUrl.QANDA_URL,
            CommonUrl.APPRECIATION_URL,
    };
    //获得对应栏目的详情数据
    public static final String GET_DETAIL_DATA = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=get_detail_data";

    //普通用户登录向服务端发送数据url
    public static final String USER_LOGIN_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=userlogin";
    //管理员登录向服务端发送数据url
    public static final String ADMIN_LOGIN_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=adminlogin";

    //查询admin表中单条记录的url
    public static final String USER_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=user";
    public static final String UPDATE_USER_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=updateuser";
    public static final String DELETE_USER_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=deleteuser";
    //查询admin表中单条记录的url
    public static final String ADMIN_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=admin";

    //向服务器修改admin表中单条记录的url
    public static final String UPDATE_ADMIN_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=updateadmin";
    //向服务器删除admin表中单条记录的url
    public static final String DELETE_ADMIN_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=deleteadmin";
    //向服务器添加admin表中单条记录的url
    public static final String ADD_ADMIN_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=addadmin";

    //备份数据组
    public static final String ADMINS_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=admins";
    public static final String USERS_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=users";

    public static String[] SYSTEM_DATA_BACKUP = new String[]{

            CommonUrl.ADMINS_URL,
            CommonUrl.USERS_URL,
            CommonUrl.HOME_URL,
            CommonUrl.INFORMATION_URL,
            CommonUrl.XIANGXIU_URL,
            CommonUrl.SHUXIU_URL,
            CommonUrl.YUXIU_URL,
            CommonUrl.SUXIU_URL,
            CommonUrl.QANDA_URL,
            CommonUrl.APPRECIATION_URL
    };

    //修改密码组
    public static final String UPDATE_USER_PWD = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=update_user_pwd";
    public static final String UPDATE_ADMIN_PWD = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=update_admin_pwd";

    //收藏
    public static final String ADD_SAVE_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=add_save";
    //我的收藏
    public static final String SAVE_WITH_U_NUM_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=save_with_u_num";
    public static final String SAVE_URL = BASE_URL + "EmbroideryServer/servlet/JsonAction?action_flag=save";

}
