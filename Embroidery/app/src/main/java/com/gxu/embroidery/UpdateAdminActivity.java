package com.gxu.embroidery;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gxu.embroidery.db.domain.Admin;
import com.gxu.embroidery.utils.CommonUrl;
import com.gxu.embroidery.utils.HttpUtils;
import com.gxu.embroidery.utils.JsonService;

import java.util.HashMap;
import java.util.Map;


public class UpdateAdminActivity extends BaseActivity implements View.OnClickListener {

    private EditText m_num_value, m_name_value, m_pwd_value, m_phone_value;
    private Button update_admin;
    private Admin up_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_admin);
        init();
    }

    private void init() {
        up_admin = (Admin) getIntent().getSerializableExtra("update_admin");

        m_num_value = (EditText) this.findViewById(R.id.m_num_value);
        m_name_value = (EditText) this.findViewById(R.id.m_name_value);
        m_pwd_value = (EditText) this.findViewById(R.id.m_pwd_value);
        m_phone_value = (EditText) this.findViewById(R.id.m_phone_value);

        m_num_value.setText(up_admin.getM_num());
        m_num_value.setEnabled(false);
        m_name_value.setText(up_admin.getM_name());
        m_pwd_value.setText(up_admin.getM_pwd());
        m_phone_value.setText(up_admin.getM_phone());

        update_admin = (Button) this.findViewById(R.id.update_admin);
        update_admin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        updateAdmin();
    }

    private void updateAdmin() {
        // 判断输入框是否为空
        if (m_num_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员编号不能为空！");
        } else if (m_name_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员姓名不能为空！");
        } else if (m_pwd_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员密码不能为空！");
        } else if (m_phone_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员手机不能为空！");
        } else {
            Map<String, String> map = new HashMap<>();
            Boolean flag = false;
            try {
                map.put("m_num", m_num_value.getText().toString());
                map.put("m_name", m_name_value.getText().toString());
                map.put("m_pwd",m_pwd_value.getText().toString());
                map.put("m_phone", m_phone_value.getText().toString());
                flag = HttpUtils.sendJavaBeanToServer(CommonUrl.UPDATE_ADMIN_URL,
                        JsonService.createJsonString(map));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (flag == true) {
                msg(this, "成功信息", "修改管理员成功！");
                launch(AdminMangeActivity.class);
            } else {
                msg(this, "失败信息", "修改管理员失败！");
            }
        }
    }

}
