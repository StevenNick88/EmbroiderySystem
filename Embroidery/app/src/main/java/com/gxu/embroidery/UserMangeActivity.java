package com.gxu.embroidery;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserMangeActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView user_mange_lv;
    private UserMangeBaseAdapter adapter;
    private String[] userMangeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mange);
        init();
    }

    private void init() {
        user_mange_lv= (ListView)this.findViewById(R.id.user_mange_lv);
        userMangeItem =  this.getResources().getStringArray(R.array.user_mange_item);
        adapter=new UserMangeBaseAdapter(this);

        adapter.setData(getPersonData());
        user_mange_lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        user_mange_lv.setOnItemClickListener(this);
    }


    private List<Map<String, Object>> getPersonData() {
        Resources res = this.getResources();
        List<Map<String, Object>> data = new ArrayList();
        for (int i = 0; i < userMangeItem.length; i++) {
            Map<String, Object> map = new HashMap();
            map.put("img", res.getIdentifier("person_center_item", "drawable", this.getPackageName()));
            map.put("name", userMangeItem[i]);

            data.add(map);
        }
        return data;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                launch(UserQueryActivity.class);
                break;
            case 1:
                launch(UserAddActivity.class);
                break;
            default:
                break;
        }

    }

    private class UserMangeBaseAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Map<String, Object>> list = null;

        public UserMangeBaseAdapter(Context context) {
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
