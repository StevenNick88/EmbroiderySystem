package com.gxu.embroidery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gxu.embroidery.db.domain.User;
import com.gxu.embroidery.utils.CommonUrl;
import com.gxu.embroidery.utils.CommonUtils;
import com.gxu.embroidery.utils.HttpUtils;
import com.gxu.embroidery.utils.ImgUtils;
import com.gxu.embroidery.utils.JsonService;
import com.gxu.embroidery.utils.JsonTools;
import com.gxu.embroidery.utils.UploadFileTask;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends ActionBarActivity implements View.OnClickListener {
    private EditText  u_name_value, u_pwd_value, u_phone_value,  u_pwd_confirm_value,code_value;
    private Button register_user,get_code,select_img;
    private String learnMan;
    private String phoneCode="";
    private String picPath = null;
    private ImageView user_img;
    private Bitmap bitmap;
    private static final String TAG = "uploadImage";
    private static final String FLAG = "updatedImage";

    public String getLearnMan() {
        return learnMan;
    }

    public void setLearnMan(String learnMan) {
        this.learnMan = learnMan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        register_user = (Button) this.findViewById(R.id.register_user);
        get_code = (Button) this.findViewById(R.id.get_code);
        select_img =(Button)this.findViewById(R.id.select_img);
        user_img=(ImageView)this.findViewById(R.id.user_img);
        register_user.setOnClickListener(this);
        get_code.setOnClickListener(this);
        select_img.setOnClickListener(this);

        u_name_value = (EditText) this.findViewById(R.id.u_name_value);
        u_pwd_value = (EditText) this.findViewById(R.id.u_pwd_value);
        u_phone_value = (EditText) this.findViewById(R.id.u_phone_value);
        u_pwd_confirm_value = (EditText) this.findViewById(R.id.u_pwd_confirm_value);
        code_value = (EditText) this.findViewById(R.id.code_value);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.register_user:
                registerUser();
                break;
            case R.id.get_code:
                getCode();
                break;
            case R.id.select_img:
                chooseImg();
                break;
            default:
                break;

        }
    }

    private void getCode() {
        String number=u_phone_value.getText().toString();
        phoneCode= CommonUtils.createRandom(true, 4);
        if (number.equals("")) {
            msg(this, "提示", "手机号码不能为空！");
        }else if(number.length()!=11){
            msg(this, "提示", "您输入的不是正确的手机号码！");
        }else{
            timer.start();
            String content="您此次注册系统的验证码为："+phoneCode+",请妥善保管！";
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(number,null,content,null,null);
        }

    }

    private void registerUser() {
        // 判断输入框是否为空
        String u_pwd = u_pwd_value.getText().toString();
        String u_pwd_confirm = u_pwd_confirm_value.getText().toString();
        String codeValue = code_value.getText().toString();
        if (u_name_value.getText().toString().equals("")) {
            msg(this, "提示", "学者姓名不能为空！");
        } else if (u_pwd_value.getText().toString().equals("")) {
            msg(this, "提示", "学者密码不能为空！");
        } else if (u_pwd_confirm_value.getText().toString().equals("")) {
            msg(this, "提示", "确认密码不能为空！");
        } else if (!(u_pwd_confirm.equals(u_pwd))) {
            msg(this, "提示", "学者密码和确认密码不一致！请重新输入。");
            u_pwd_confirm_value.setText("");
        } else if (u_phone_value.getText().toString().equals("")) {
            msg(this, "提示", "手机号码不能为空！");
        }
        else if (u_phone_value.getText().length()!=11) {
            msg(this, "提示", "您输入的不是正确的手机号码！");
        }
        // 判断验证码是否为空
        else if (codeValue.equals("")) {
            msg(this, "提示", "验证码不能为空！");
        }
        // 判断验证码是否正确
        else if (!(codeValue.equals(phoneCode))) {
            msg(this, "提示", "验证码错误，请重新输入！");
        }
        else {
            if (picPath != null && picPath.length() > 0) {
                UploadFileTask uploadFileTask = new UploadFileTask(this);
                uploadFileTask.execute(picPath);
            }
            //首先查询现在数据库中user编号的最大值
            String jsonString = HttpUtils.getJsonContent(CommonUrl.USER_LAST_URL);
            User user = JsonTools.getUser("user_last", jsonString);
            Map<String, String> map = new HashMap<>();
            if ((user != null) && (user.getU_num() != null)) {
                //将编号加1
                int ms_num = Integer.parseInt(user.getU_num());
                int rel_ms_num=++ms_num;
                map.put("u_num", String.valueOf(rel_ms_num));
                setLearnMan(String.valueOf(rel_ms_num));
            } else {
                //从编号100开始添加买家
                map.put("u_num", String.valueOf(100));
                setLearnMan(String.valueOf(100));
            }
            Boolean flag = false;
            try {
                map.put("u_name", u_name_value.getText().toString());
                map.put("u_pwd", u_pwd_value.getText().toString());
                map.put("u_phone", u_phone_value.getText().toString());
                flag = HttpUtils.sendJavaBeanToServer(CommonUrl.ADD_USER_URL,
                        JsonService.createJsonString(map));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (flag == true) {
                msgWithPositiveButton(RegisterActivity.this, "提示", "确定", "注册学者成功！您的用户名是：" + getLearnMan() +
                        ",\t密码是：" + u_pwd_value.getText().toString() +
                        ",\t绑定的手机是：" + u_phone_value.getText().toString() + "，请牢记！");
            } else {
                msg(this, "失败信息", "注册学者失败！");
            }
        }
    }

    public void onPositiveEvent() {
        launch(LoginActivity.class);
    }

    public void launch(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
        overridePendingTransition(R.anim.push_right_in, android.R.anim.fade_out);
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

    private void chooseImg() {
        /***
         * 这个是调用android内置的intent，来过滤图片文件   ，同时也可以过滤其他的
         */
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //回调图片类使用的
        startActivityForResult(intent, RESULT_CANCELED);
    }

    /**
     * 回调执行的方法
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            /**
             * 当选择的图片不为空的话，在获取到图片的途径
             */
            Uri uri = data.getData();
            Log.e(TAG, "uri = " + uri);
            try {
                String[] pojo = {MediaStore.Images.Media.DATA};

                Cursor cursor = managedQuery(uri, pojo, null, null, null);
                if (cursor != null) {
                    ContentResolver cr = this.getContentResolver();
                    int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(colunm_index);
                    /***
                     * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，这样的话，我们判断文件的后缀名
                     * 如果是图片格式的话，那么才可以
                     */
                    if (path.endsWith("jpg") || path.endsWith("png")) {
                        picPath = path;
//                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        bitmap = ImgUtils.getThumbnail(uri, cr,
                                MediaStore.Images.Thumbnails.MICRO_KIND, new BitmapFactory.Options());

                        user_img.setImageBitmap(bitmap);
                        user_img.setVisibility(View.VISIBLE);
                    } else {
                        alert();
                    }
                } else {
                    alert();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 回调使用
         */
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void alert() {
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("您选择的不是有效的图片")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                picPath = null;
                            }
                        })
                .create();
        dialog.show();
    }

}
