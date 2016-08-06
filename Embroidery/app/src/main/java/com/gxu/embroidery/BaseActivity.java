package com.gxu.embroidery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.gxu.embroidery.db.domain.Admin;
import com.gxu.embroidery.db.domain.User;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;


public class BaseActivity extends ActionBarActivity {

    //由于用户分为student和admin，所以使用Object增加包容性
    private Object now_user = null;
    private String userType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        getOverflowMenu();

        userType = getIntent().getStringExtra("user_type");
        now_user = getIntent().getSerializableExtra("now_user");
        if (now_user != null && userType!=null) {
            setNow_user(now_user);
           if (userType.equals("学者")){
                launch(UserMainActivity.class);
                finish();
            }
            else if (userType.equals("管理员")){
                launch(AdminMainActivity.class);
                finish();
            }

        }
    }

    public Object getNow_user() {
        return now_user;
    }

    public void setNow_user(Object now_user) {
        this.now_user = now_user;
    }

    // 消息对话框
    public void msgWithPositiveButton(Context context, String title, String positiveButton, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(R.drawable.ic_dialog_alert_holo_light);
        builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onPositiveEvent();
            }
        });
        builder.create();
        builder.show();
    }

    // 消息对话框
    public void msgWithEvent(Context context, String title, String positiveButton, String negativeButton, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(R.drawable.ic_dialog_alert_holo_light);
        builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onPositiveEvent();
            }


        });
        builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onNegativeEvent();
            }
        });
        builder.create();
        builder.show();
    }




    public void onNegativeEvent() {
    }

    public void onPositiveEvent() {
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

    // 进度条对话框
    public ProgressDialog createProgressDialog(Context context, String title, String message) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        return dialog;
    }

    /**
     * ********************************************************************************
     * Activity之间跳转并传值
     */
    public void launch(Intent it) {
        startActivity(it);
        overridePendingTransition(R.anim.push_right_in, android.R.anim.fade_out);
    }

    public void launch_slideright2left(Intent it) {
        startActivity(it);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    public void launch(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
        overridePendingTransition(R.anim.push_right_in, android.R.anim.fade_out);
    }

    public void launch(Class<? extends Activity> clazz, String key, String value) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(key, value);
        startActivity(intent);
        overridePendingTransition(R.anim.push_right_in, android.R.anim.fade_out);
    }


    public void launch(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        launch_slideright2left(intent);
    }

    public void launch(Class<? extends Activity> clazz, Intent it, String key, Object object) {
        it.setClass(this, clazz);
        it.putExtra(key, (Serializable) object);
        launch_slideright2left(it);
    }


    // ********************************************************************************

    public void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem now_user_num = menu.findItem(R.id.now_user_num);
        //首先判断当前用户是学生还是管理员，以便发送数据给服务端
        Map<String, Object> userMap = LoginActivity.getNowUserMap();
        for (String key : userMap.keySet()) {
            if (key.equals("学者")) {
                User now_user = (User) userMap.get(key);
                now_user_num.setTitle(now_user.getU_num() + ",欢迎你！");
            }
            else {
                Admin now_user = (Admin) userMap.get(key);
                now_user_num.setTitle(now_user.getM_num() + ",欢迎你！");
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                //首先判断当前用户是学生还是管理员，以便发送数据的服务端
                Map<String, Object> userMap = LoginActivity.getNowUserMap();
                for (String key : userMap.keySet()) {
                    if (key.equals("学者")) {
                        launch(UserMainActivity.class);
                    } else {
                        launch(AdminMainActivity.class);
                    }
                }
                break;
            default:
                break;

        }
        return true;
    }





}
