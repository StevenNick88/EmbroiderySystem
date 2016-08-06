package com.gxu.embroidery;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gxu.embroidery.db.domain.Admin;
import com.gxu.embroidery.db.domain.User;
import com.gxu.embroidery.utils.CommonUrl;
import com.gxu.embroidery.utils.HttpUtils;
import com.gxu.embroidery.utils.JsonService;
import com.gxu.embroidery.utils.JsonTools;

import java.util.HashMap;
import java.util.Map;


public class AdminAddActivity extends BaseActivity implements View.OnClickListener {
    private EditText m_num_value, m_name_value, m_pwd_value, m_phone_value;
    private Button add_admin;
    private String adminNum;

    public String getAdminNum() {
        return adminNum;
    }

    public void setAdminNum(String adminNum) {
        this.adminNum = adminNum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add);
        init();
    }

    private void init() {
        m_num_value = (EditText) this.findViewById(R.id.m_num_value);
        m_name_value = (EditText) this.findViewById(R.id.m_name_value);
        m_pwd_value = (EditText) this.findViewById(R.id.m_pwd_value);
        m_phone_value = (EditText) this.findViewById(R.id.m_phone_value);

        add_admin = (Button) this.findViewById(R.id.add_admin);
        add_admin.setOnClickListener(this);

        String jsonString = HttpUtils.getJsonContent(CommonUrl.ADMIN_LAST_URL);
        Admin admin = JsonTools.getAdmin("admin_last", jsonString);
        if ((admin != null) && (admin.getM_num() != null)) {
            int ms_num = Integer.parseInt(admin.getM_num());
            int rel_ms_num=++ms_num;
            setAdminNum(String.valueOf(rel_ms_num));
            m_num_value.setText(String.valueOf(rel_ms_num));
            m_num_value.setEnabled(false);
        } else {
            setAdminNum(String.valueOf(100));
            m_num_value.setText(String.valueOf(100));
        }
    }


    @Override
    public void onClick(View v) {
        addAdmin();
    }

    private void addAdmin() {
        if (m_name_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员编号不能为空");
        }
        else if ( m_pwd_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员密码不能为空");
        }
        else if ( m_phone_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员手机不能为空");
        }else{
            Map<String, String> map = new HashMap<>();
            map.put("m_num",getAdminNum());
            map.put("m_name", m_name_value.getText().toString());
            map.put("m_pwd", m_pwd_value.getText().toString());
            map.put("m_phone", m_phone_value.getText().toString());
            Boolean flag = HttpUtils.sendJavaBeanToServer(CommonUrl.ADD_ADMIN_URL,
                    JsonService.createJsonString(map));
            if (flag == true) {
                msg(this, "提示", "添加管理员成功！");
                launch(AdminMangeActivity.class);
            } else {
                msg(this, "提示", "添加管理员失败！");
            }
        }

    }
}
