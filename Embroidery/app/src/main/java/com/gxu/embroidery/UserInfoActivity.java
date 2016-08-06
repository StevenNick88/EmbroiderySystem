package com.gxu.embroidery;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxu.embroidery.db.domain.User;
import com.gxu.embroidery.utils.CommonUrl;
import com.gxu.embroidery.utils.HttpUtils;
import com.gxu.embroidery.utils.ImgUtils;


public class UserInfoActivity extends BaseActivity implements View.OnClickListener{

    private TextView u_num_value,u_name_value,u_pwd_value,u_phone_value;
    private Button update_user,delete_user;
    private User user_query;
    private ImageView user_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        init();

    }

    private void init() {
        user_query = (User)getIntent().getSerializableExtra("user_query");

        u_num_value=(TextView)this.findViewById(R.id.u_num_value);
        u_name_value=(TextView)this.findViewById(R.id.u_name_value);
        u_pwd_value=(TextView)this.findViewById(R.id.u_pwd_value);
        u_phone_value=(TextView)this.findViewById(R.id.u_phone_value);
        user_img=(ImageView)this.findViewById(R.id.user_img);
        getImage(user_img);

        u_num_value.setText(user_query.getU_num());
        u_name_value.setText(user_query.getU_name());
        u_pwd_value.setText(user_query.getU_pwd());
        u_phone_value.setText(user_query.getU_phone());

        update_user=(Button)this.findViewById(R.id.update_user);
        delete_user=(Button)this.findViewById(R.id.delete_user);
        update_user.setOnClickListener(this);
        delete_user.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.update_user:
                Bundle bundle = new Bundle();
                bundle.putSerializable("update_user", user_query);
                launch(UpdateUserActivity.class,bundle);
                break;
            case R.id.delete_user:
                deleteUser();
                break;
        }
    }

    private void deleteUser() {
        Boolean flag = HttpUtils.sendJavaBeanToServer(CommonUrl.DELETE_USER_URL,
                u_num_value.getText().toString());
        if (flag == true) {
            msg(this, "成功信息", "删除学者成功！");
            launch(AdminMainActivity.class);
        } else {
            msg(this, "失败信息", "删除学者失败！");
        }
    }

    private void getImage(final ImageView imageView) {
        String s=CommonUrl.LOAD_IMG_HEAD+user_query.getU_img();
        ImgUtils.loadImage(new ImgUtils.ImageCallBack() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);

            }
        },CommonUrl.LOAD_IMG_HEAD+user_query.getU_img());

    }

}
