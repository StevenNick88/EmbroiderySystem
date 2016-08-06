package com.gxu.embroidery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gxu.embroidery.db.domain.User;
import com.gxu.embroidery.utils.CommonUrl;
import com.gxu.embroidery.utils.HttpUtils;
import com.gxu.embroidery.utils.ImgUtils;
import com.gxu.embroidery.utils.JsonService;
import com.gxu.embroidery.utils.UploadFileTask;

import java.util.HashMap;
import java.util.Map;


public class UpdateUserActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "uploadImage";
    private static final String FLAG = "updatedImage";
    private EditText u_num_value, u_name_value,u_pwd_value,u_phone_value;
    private Button select_img,update_user;
    private String picPath = null;
    private User up_user;
    private ImageView user_img;
    private ProgressDialog dialog;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        init();
    }

    private void init() {
        up_user = (User)getIntent().getSerializableExtra("update_user");
        dialog= createProgressDialog(UpdateUserActivity.this,"提示","正在处理数据。。。请稍后！");


        u_num_value=(EditText)this.findViewById(R.id.u_num_value);
        u_name_value=(EditText)this.findViewById(R.id.u_name_value);
        u_pwd_value=(EditText)this.findViewById(R.id.u_pwd_value);
        u_phone_value=(EditText)this.findViewById(R.id.u_phone_value);
        user_img=(ImageView)this.findViewById(R.id.user_img);
        getImage(user_img,CommonUrl.LOAD_IMG_HEAD+up_user.getU_img());


        u_num_value.setText(up_user.getU_num());
        u_num_value.setEnabled(false);
        u_name_value.setText(up_user.getU_name());
        u_pwd_value.setText(up_user.getU_pwd());
        u_phone_value.setText(up_user.getU_phone());

        select_img =(Button)this.findViewById(R.id.select_img);
        update_user =(Button)this.findViewById(R.id.update_user);
        select_img.setOnClickListener(this);
        update_user.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_img:
                chooseImg();
                break;
            case R.id.update_user:
                updateUser();
                break;
            default:
                break;
        }

    }

    private void updateUser() {
        // 判断输入框是否为空
        if (user_img.getDrawable() == null) {
            msg(this, "提示", "学者头像不能为空！");
        }
        else if (u_num_value.getText().toString().equals("")) {
            msg(this, "提示", "学者编号不能为空！");
        } else if (u_name_value.getText().toString().equals("")) {
            msg(this, "提示", "学者姓名不能为空！");
        } else if (u_pwd_value.getText().toString().equals("")) {
            msg(this, "提示", "学者密码不能为空！");
        } else if (u_phone_value.getText().toString().equals("")) {
            msg(this, "提示", "学者手机不能为空！");
        }
        else {
            dialog.show();
            Map<String, String> map = new HashMap<>();
            Drawable drawable = new BitmapDrawable(bitmap);
            //如果修改了图书图片:将一个修改了图书图片的标识传到服务端
            if (!(user_img.getDrawable().equals(drawable))){
                map.put(FLAG,"true");
                //未修改图片
            }else{
                map.put(FLAG,"false");
            }
            //如果修改了图书图片才进行图片上传操作：异步任务开启另一个线程
            if (!(user_img.getDrawable().equals(drawable))&&picPath != null && picPath.length() > 0) {
                UploadFileTask uploadFileTask = new UploadFileTask(this);
                uploadFileTask.execute(picPath);
            }
            map.put("u_num", u_num_value.getText().toString());
            map.put("u_name", u_name_value.getText().toString());
            map.put("u_pwd", u_pwd_value.getText().toString());
            map.put("u_phone", u_phone_value.getText().toString());
            map.put("u_img", up_user.getU_img());
            Boolean flag = HttpUtils.sendJavaBeanToServer(CommonUrl.UPDATE_USER_URL,
                    JsonService.createJsonString(map));
            if (flag == true) {
                dialog.dismiss();
                msg(this, "成功信息", "修改学者成功！");
                launch(AdminMainActivity.class);
            } else {
                dialog.dismiss();
                msg(this, "失败信息", "修改学者失败！");
            }

        }


    }

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

    private void getImage(final ImageView imageView, String url) {
        ImgUtils.loadImage(new ImgUtils.ImageCallBack() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);

            }
        }, url);

    }

}
