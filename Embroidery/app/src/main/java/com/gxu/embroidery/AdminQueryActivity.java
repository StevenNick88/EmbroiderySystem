package com.gxu.embroidery;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gxu.embroidery.db.domain.Admin;
import com.gxu.embroidery.utils.CommonUrl;
import com.gxu.embroidery.utils.HttpUtils;
import com.gxu.embroidery.utils.JsonService;
import com.gxu.embroidery.utils.JsonTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminQueryActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView admin_list_lv;
    private EditText m_num_value;
    private Button admin_query;
    private List<String> listNames;
    private AdminQueryBaseAdapter adapter;
    private List<Admin> adminList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_query);
        init();
    }

    private void init() {
        m_num_value = (EditText) this.findViewById(R.id.m_num_value);
        admin_query = (Button) this.findViewById(R.id.admin_query);
        admin_list_lv = (ListView) this.findViewById(R.id.admin_list_lv);
        admin_query.setOnClickListener(this);
        listNames=getAdminNames();

        adapter=new AdminQueryBaseAdapter(this);
        adapter.setData(getAdminData());
        admin_list_lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        admin_list_lv.setOnItemClickListener(this);
    }

    private List<String> getAdminNames() {
        List<String> list = new ArrayList<>();
        try {
            String jsonString = HttpUtils.getJsonContent(CommonUrl.ADMINS_URL);
            adminList = JsonTools.getAdmins("admins", jsonString);
            if (adminList != null) {
                for (int i = 0; i < adminList.size(); i++) {
                    list.add((String) adminList.get(i).getM_name());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<Map<String, Object>> getAdminData() {
        Resources res = this.getResources();
        //每个数据项中的数据由Map来存储
        List<Map<String, Object>> data = new ArrayList();
        for (int i = 0; i < listNames.size(); i++) {
            Map<String, Object> map = new HashMap();
            map.put("img", res.getIdentifier("person_center_item", "drawable", this.getPackageName()));
            map.put("name", listNames.get(i));

            data.add(map);
        }
        return data;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.admin_query:
                queryAdmin();
                break;
            default:
                break;
        }
    }

    private void queryAdmin() {
        // 判断输入框是否为空
        if (m_num_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员编号不能为空！");
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("m_num", m_num_value.getText().toString());
            //从服务端获取json的数据并封装为javabean
            String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.ADMIN_URL,
                    JsonService.createJsonString(map));
            Admin admin = JsonTools.getAdmin("admin", jsonString);
            if (admin != null && admin.getM_num() != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("admin_query", admin);
                launch(AdminInfoActivity.class, bundle);
            } else {
                msg(this, "失败信息", "对不起，没有这个管理员！");
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("admin_query", adminList.get(position));
        launch(AdminInfoActivity.class, bundle);
    }

    private class AdminQueryBaseAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Map<String, Object>> list = null;

        public AdminQueryBaseAdapter(Context context) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
        }

        public void setData(List<Map<String, Object>> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.person_list_item, null);
            } else {
                view = convertView;
            }
            ImageView person_item_img = (ImageView) view.findViewById(R.id.person_item_img);
            TextView person_list_item_name = (TextView) view.findViewById(R.id.person_list_item_name);
            person_item_img.setImageResource((Integer)list.get(position).get("img"));
            person_list_item_name.setText((String) list.get(position).get("name"));
            return view;
        }
    }
}
