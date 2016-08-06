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

import com.gxu.embroidery.db.domain.User;
import com.gxu.embroidery.utils.CommonUrl;
import com.gxu.embroidery.utils.HttpUtils;
import com.gxu.embroidery.utils.JsonService;
import com.gxu.embroidery.utils.JsonTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserQueryActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView user_list_lv;
    private EditText u_num_value;
    private Button user_query;

    private List<String> listNames;
    private UserQueryBaseAdapter adapter;
    private List<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_query);
        init();
    }

    private void init() {
        u_num_value=(EditText)this.findViewById(R.id.u_num_value);
        user_query=(Button)this.findViewById(R.id.user_query);
        user_query.setOnClickListener(this);
        user_list_lv = (ListView) this.findViewById(R.id.user_list_lv);
        listNames=getAdminNames();

        adapter=new UserQueryBaseAdapter(this);
        adapter.setData(getAdminData());
        user_list_lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        user_list_lv.setOnItemClickListener(this);
    }

    private List<String> getAdminNames() {
        List<String> list = new ArrayList<>();
        try {
            String jsonString = HttpUtils.getJsonContent(CommonUrl.USERS_URL);
            userList = JsonTools.getListUser("users", jsonString);
            if (userList != null) {
                for (int i = 0; i < userList.size(); i++) {
                    list.add((String) userList.get(i).getU_name());
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
        switch (v.getId()){
            case R.id.user_query:
                queryUser();
                break;
            default:
                break;
        }
    }
    private void queryUser() {
        if (u_num_value.getText().toString().equals("")) {
            msg(this, "提示","学者编号不能为空！");
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("u_num", u_num_value.getText().toString());
            String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.USER_URL,
                    JsonService.createJsonString(map));
            User user = JsonTools.getUser("user", jsonString);
            if (user != null && user.getU_num()!=null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user_query", user);
                launch(UserInfoActivity.class, bundle);
            } else {
                msg(this, "提示", "对不起，没有这个学者！");
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("user_query", userList.get(position));
        launch(UserInfoActivity.class, bundle);
    }

    private class UserQueryBaseAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Map<String, Object>> list = null;

        public UserQueryBaseAdapter(Context context) {
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
