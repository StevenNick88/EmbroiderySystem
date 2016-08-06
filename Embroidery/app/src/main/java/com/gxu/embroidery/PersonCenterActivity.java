package com.gxu.embroidery;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gxu.embroidery.db.domain.Admin;
import com.gxu.embroidery.db.domain.User;
import com.gxu.embroidery.utils.CommonUrl;
import com.gxu.embroidery.utils.ImgUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PersonCenterActivity extends BaseActivity implements OnItemClickListener{
    private ImageView user_img;
    private ListView user_center_lv;
    private PersonCenterBaseAdapter adapter;
    private String[] personCenterItem;
    private User now_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);
        init();
    }

    private void init() {
        now_user = (User)getIntent().getSerializableExtra("now_user");
        user_img= (ImageView)this.findViewById(R.id.user_img);
        getImage(user_img, CommonUrl.LOAD_IMG_HEAD+now_user.getU_img());

        user_center_lv= (ListView)this.findViewById(R.id.user_center_lv);
        personCenterItem =  this.getResources().getStringArray(R.array.person_center_item);
        adapter=new PersonCenterBaseAdapter(this);

        adapter.setData(getPersonData());
        user_center_lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        user_center_lv.setOnItemClickListener(this);
    }


    private List<Map<String, Object>> getPersonData() {
        Resources res = this.getResources();
        List<Map<String, Object>> data = new ArrayList();
        for (int i = 0; i < personCenterItem.length; i++) {
            Map<String, Object> map = new HashMap();
            map.put("img", res.getIdentifier("person_center_item", "drawable", this.getPackageName()));
            map.put("name", personCenterItem[i]);

            data.add(map);
        }
        return data;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                Bundle bundle = new Bundle();
                bundle.putSerializable("now_user", now_user);
                launch(SaveListActivity.class, bundle);
                break;
            default:
                break;
        }


    }

    private class PersonCenterBaseAdapter extends BaseAdapter {
            private Context context;
            private LayoutInflater layoutInflater;
            private List<Map<String, Object>> list = null;

            public PersonCenterBaseAdapter(Context context) {
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


    private void getImage(final ImageView imageView, String url) {
        ImgUtils.loadImage(new ImgUtils.ImageCallBack() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);

            }
        }, url);

    }
}
