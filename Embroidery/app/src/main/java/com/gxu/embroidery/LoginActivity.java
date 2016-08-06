package com.gxu.embroidery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gxu.embroidery.db.domain.Admin;
import com.gxu.embroidery.db.domain.User;
import com.gxu.embroidery.receiver.EmbroideryReceiver;
import com.gxu.embroidery.utils.CommonUrl;
import com.gxu.embroidery.utils.CommonUtils;
import com.gxu.embroidery.utils.HttpUtils;
import com.gxu.embroidery.utils.JsonService;
import com.gxu.embroidery.utils.JsonTools;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private final String SHARED_PREFERENCES_NAME = "sharedPreferences";
    private EditText user_value, phone_value, code_value;
    private EditText password_value;
    private Button login, get_code;
    private Button register;
    private RadioGroup group;
    private String userType;
    private static Object nowUser = null;
    private static Map<String, Object> map = null;
    private EmbroideryReceiver receiver;
    private String username;
    private String password;
    private String codeValue, phoneValue;
    private String phoneCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        receiver = new EmbroideryReceiver();

        // 当程序进入主页面的时候，他之后启动肯定就不是第一次启动了。所以我们可以在界面，或者是调用主页面的步骤中将他的状态设为false.
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFirstIn", true);
        // 提交修改
        editor.commit();
        //那么这样就可以实现了，当程序第二次进入的时候，在启动也就进行了判断。。if() else() 就执行了你想让他执行的操作。

        user_value = (EditText) findViewById(R.id.user_value);
        password_value = (EditText) findViewById(R.id.password_value);
        phone_value = (EditText) findViewById(R.id.phone_value);
        code_value = (EditText) findViewById(R.id.code_value);
        group = (RadioGroup) this.findViewById(R.id.person);
        RadioButton learn_man = (RadioButton) group.findViewById(R.id.learn_man);
        learn_man.setChecked(true);
        login = (Button) this.findViewById(R.id.login);
        register = (Button) this.findViewById(R.id.register);
        get_code = (Button) this.findViewById(R.id.get_code);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        get_code.setOnClickListener(this);
    }

    // 登录按钮监听
    public void login() {
        username = user_value.getText().toString();
        password = password_value.getText().toString();
        codeValue = code_value.getText().toString();
        phoneValue = phone_value.getText().toString();
        // 判断用户名是否为空
        if (username.equals("")) {
            msg(this, "提示", "用户名不能为空！");
        }
        // 判断密码是否为空
        else if (password.equals("")) {
            msg(this, "提示", "密码不能为空！");
        } else if (phoneValue.equals("")) {
            msg(this, "提示", "手机号码不能为空！");
        } else if (phoneValue.length() != 11) {
            msg(this, "提示", "您输入的不是正确的手机号码！");
        }
        // 判断验证码是否为空
        else if (codeValue.equals("")) {
            msg(this, "提示", "验证码不能为空！");
        }
        // 判断验证码是否正确
        else if (!(codeValue.equals(phoneCode))) {
            msg(this, "提示", "验证码错误，请重新输入！");
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int len = group.getChildCount();// 获得单选按钮组的选项个数
            for (int i = 0; i < len; i++) {
                RadioButton radioButton = (RadioButton) group.getChildAt(i);
                if (radioButton.isChecked()) {
                    userType = radioButton.getText().toString();
                    break;
                }
            }
            if (userType == null) {
                msg(this, "提示", "请选择用户类型");
            } else if (userType.equals("学者")) {
                Map<String, String> map = new HashMap<>();
                map.put("u_num", username);
                User user = null;
                try {
                    String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.USER_LOGIN_URL,
                            JsonService.createJsonString(map));
                    user = JsonTools.getUser("user", jsonString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!(phoneValue.equals(user.getU_phone()))) {
                    msg(this, "提示", "您输入的不是注册时绑定的手机号码，请输入绑定的手机号码！");
                } else if (user.getU_pwd() != null && user.getU_pwd().equals(password)) {
                    nowUser = user;
                    setNowUser(nowUser);
                    setNowUserMap(userType);

                    Bundle bundle = new Bundle();
                    bundle.putString("user_type", userType);
                    bundle.putSerializable("now_user", (Serializable) user);
                    launch(BaseActivity.class, bundle);
                    finish();
                } else {
                    msg(this, "失败信息", "用户名或密码错误，请重新输入！");
                    user_value.setText("");
                    password_value.setText("");
                }

            } else if (userType.equals("管理员")) {
                Map<String, String> map = new HashMap<>();
                map.put("m_num", username);
                Admin admin = null;
                try {
                    String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.ADMIN_LOGIN_URL,
                            JsonService.createJsonString(map));
                    admin = JsonTools.getAdmin("admin", jsonString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!(phoneValue.equals(admin.getM_phone()))) {
                    msg(this, "提示", "您输入的不是注册时绑定的手机号码，请输入绑定的手机号码！");
                } else if (admin.getM_pwd() != null && admin.getM_pwd().equals(password)) {
                    nowUser = admin;
                    setNowUserMap(userType);

                    Bundle bundle = new Bundle();
                    bundle.putString("user_type", userType);
                    bundle.putSerializable("now_user", (Serializable) admin);
                    launch(BaseActivity.class, bundle);
                    finish();
                } else {
                    msg(this, "失败信息", "用户名或密码错误，请重新输入！");
                    user_value.setText("");
                    password_value.setText("");
                }
            }
        }
    }


    public static void setNowUserMap(String key) {
        map = new HashMap<>();
        if (nowUser != null) {
            map.put(key, nowUser);
        } else {
            map.put(key, null);
        }
    }

    public static Map<String, Object> getNowUserMap() {
        return map;
    }

    public static Object getNowUser() {
        if (nowUser != null) {
            return nowUser;
        }
        return "";
    }

    public static void setNowUser(Object nowUser) {
        LoginActivity.nowUser = nowUser;
    }

    private void register() {
        launch(RegisterActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                login();
                break;
            case R.id.register:
                register();
                break;
            case R.id.get_code:
                getCode();

                break;
            default:
                break;
        }
    }

    private void getCode() {

        String number = phone_value.getText().toString();
        phoneCode = CommonUtils.createRandom(true, 4);
        if (number.equals("")) {
            msg(this, "提示", "手机号码不能为空！");
        } else if (number.length() != 11) {
            msg(this, "提示", "您输入的不是正确的手机号码！");
        } else {
            timer.start();
            String content = "您此次登录系统的验证码为：" + phoneCode + ",请妥善保管！";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, content, null, null);
        }

    }

    // 消息对话框
    public void msg(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(R.drawable.ic_dialog_alert_holo_light);
        builder.setPositiveButton("确定", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void launch(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        launch_slideright2left(intent);
    }

    public void launch(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
        overridePendingTransition(R.anim.push_right_in, android.R.anim.fade_out);
    }

    public void launch_slideright2left(Intent it) {
        startActivity(it);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    private CountDownTimer timer = new CountDownTimer(120000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            get_code.setClickable(false);
            get_code.setBackgroundColor(Color.WHITE);
            get_code.setText(millisUntilFinished / 1000 + "秒。。。");
        }

        @Override
        public void onFinish() {
            get_code.setText("获取短信验证码");
            get_code.setClickable(true);
        }
    };

    //注册广播
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    // 卸载广播
    @Override
    protected void onPause() {
        super.onPause();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

}
