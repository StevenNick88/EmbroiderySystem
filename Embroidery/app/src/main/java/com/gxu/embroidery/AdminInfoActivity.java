package com.gxu.embroidery;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gxu.embroidery.db.domain.Admin;
import com.gxu.embroidery.utils.CommonUrl;
import com.gxu.embroidery.utils.HttpUtils;


public class AdminInfoActivity extends BaseActivity implements View.OnClickListener{

    private TextView m_num_value,m_name_value,m_pwd_value,m_phone_value;
    private Button update_admin,delete_admin;
    private Admin admin_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_info);
        init();

    }

    private void init() {
        admin_query = (Admin)getIntent().getSerializableExtra("admin_query");

        m_num_value=(TextView)this.findViewById(R.id.m_num_value);
        m_name_value=(TextView)this.findViewById(R.id.m_name_value);
        m_pwd_value=(TextView)this.findViewById(R.id.m_pwd_value);
        m_phone_value=(TextView)this.findViewById(R.id.m_phone_value);

        m_num_value.setText(admin_query.getM_num());
        m_name_value.setText(admin_query.getM_name());
        m_pwd_value.setText(admin_query.getM_pwd());
        m_phone_value.setText(admin_query.getM_phone());

        update_admin=(Button)this.findViewById(R.id.update_admin);
        delete_admin=(Button)this.findViewById(R.id.delete_admin);
        delete_admin.setOnClickListener(this);
        update_admin.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.update_admin:
                Bundle bundle = new Bundle();
                bundle.putSerializable("update_admin", admin_query);
                launch(UpdateAdminActivity.class,bundle);
                break;
            case R.id.delete_admin:
                deleteAdmin();
                break;
        }
    }

    private void deleteAdmin() {
        Boolean flag = HttpUtils.sendJavaBeanToServer(CommonUrl.DELETE_ADMIN_URL,
                m_num_value.getText().toString());
        if (flag == true) {
            msg(this, "成功信息", "删除管理员成功！");
            launch(AdminMangeActivity.class);
        } else {
            msg(this, "失败信息", "删除管理员失败！");
        }
    }
}
