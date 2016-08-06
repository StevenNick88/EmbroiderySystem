package com.gxu.embroidery.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.gxu.embroidery.R;

public class EmbroideryReceiver extends BroadcastReceiver {
    public EmbroideryReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        // 提醒用户网络状况有异常
        // 分别获得2G和3G、wifi的网络状况
        //提醒用户的两种方式：吐司、通知
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (!mobileInfo.isConnected() && !wifiInfo.isConnected()) {
            msg(context, "提示", "网络未连接，请先连接网络！");
        } else {
            Toast.makeText(context, "提示,网络已连接！", Toast.LENGTH_SHORT).show();
//            msg(context, "提示", "网络已连接！");
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
}
