package com.gxu.embroidery;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;


/**
 * 实现应用程序只有在第一次启动时显示引导界面 ,以后就不在显示了
 *
 * 他的实现就是：
 第一次安装启动：启动页--->导航页-->主页面
 之后启动：启动页-->主页面
 实现的原理就是：

 在启动页面用做一个文件保存的状态,保存程序是不是第一次启动的状态。因为只是要保存一个状态，我们将这个程序是第一次打开就将他设为true,当他进入主页面之后将他的状态未为false，因为都进入到主页面了说明他之后肯定不是第一次启动程序,因为状态的字符也不多，所以大家都使用SharedPreferences来实现。
 判断这个状态是不是第一次启动如果是就走：启动页--->导航页-->主页面这个路线。不是就走：启动页-->主页面这个路线。
 下面是我在启动页面进行的操作，程序第一次启动，他的状态设为true.
 */

public class LogoActivity extends Activity {
    private final String SHARED_PREFERENCES_NAME="sharedPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //读取SharedPreferences中需要的数据,使用SharedPreferences来记录程序启动的使用次数
                SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
                //取得相应的值,如果没有该值,说明还未写入,用true作为默认值
                boolean  isFirstIn = preferences.getBoolean("isFirstIn", true);
                //判断程序第几次启动:程序不是第一次启动
                if (!isFirstIn) {
                    Intent intent = new Intent(LogoActivity.this, LoginActivity.class);
                    LogoActivity.this.startActivity(intent);
                    overridePendingTransition(R.anim.push_right_in,
                            android.R.anim.fade_out);
                    LogoActivity.this.finish();
                //程序是第一次启动
                } else {
                    Intent intent = new Intent(LogoActivity.this, WelcomeActivity.class);
                    LogoActivity.this.startActivity(intent);
                    overridePendingTransition(R.anim.push_right_in,
                            android.R.anim.fade_out);
                    LogoActivity.this.finish();
                }
            }
        }, 2000);
    }





}
