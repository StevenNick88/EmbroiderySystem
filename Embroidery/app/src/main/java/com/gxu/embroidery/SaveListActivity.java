package com.gxu.embroidery;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.gxu.embroidery.db.domain.Admin;
import com.gxu.embroidery.db.domain.Save;
import com.gxu.embroidery.db.domain.User;
import com.gxu.embroidery.utils.CommonUrl;
import com.gxu.embroidery.utils.HttpUtils;
import com.gxu.embroidery.utils.ImgUtils;
import com.gxu.embroidery.utils.JsonService;
import com.gxu.embroidery.utils.JsonTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SaveListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView save_list_lv;
    private SaveBaseAdapter adapter;
    private List<Save> saveList;
    private User now_user;
    AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_list);
        init();
    }

    private void init() {
        aq=new AQuery(this);
        now_user = (User) getIntent().getSerializableExtra("now_user");
        save_list_lv = (ListView) this.findViewById(R.id.save_list_lv);
        adapter = new SaveBaseAdapter(this);
        adapter.setData(getSaveData());
        save_list_lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        save_list_lv.setOnItemClickListener(this);
    }


    private List<Map<String, Object>> getSaveData() {
        List<Map<String, Object>> data = new ArrayList();
        try {
            Map<String, String> map = new HashMap<>();
            map.put("u_num", now_user.getU_num());
            String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.SAVE_WITH_U_NUM_URL,
                    JsonService.createJsonString(map));
            saveList = JsonTools.getSaves("save_with_u_num", jsonString);
            if (saveList != null) {
                for (int i = 0; i < saveList.size(); i++) {
                    Map<String, Object> map2 = new HashMap();
                    map2.put("img", saveList.get(i).getS_img());
                    map2.put("name", saveList.get(i).getS_title());

                    data.add(map2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString("table", saveList.get(position).getS_table());
        bundle.putString("num", saveList.get(position).getS_num());
        bundle.putString("title", saveList.get(position).getS_title());
        bundle.putString("img", saveList.get(position).getS_img());
        launch(UserMainInfoActivity.class, bundle);
    }

    private class SaveBaseAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Map<String, Object>> list = null;

        public SaveBaseAdapter(Context context) {
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
                view = layoutInflater.inflate(R.layout.save_list_item, null);
            } else {
                view = convertView;
            }
            ImageView saveImg = (ImageView) view.findViewById(R.id.save_img);
            TextView save_title = (TextView) view.findViewById(R.id.save_title);
//            aq.id(R.id.save_img).progress(R.id.progressBar).image(CommonUrl.LOAD_IMG_TITLE+list.get(position).get("img").toString(), false, false);
//            aq.id(saveImg).image(CommonUrl.LOAD_IMG_TITLE + list.get(position).get("img").toString());

            getImage(saveImg,CommonUrl.LOAD_IMG_TITLE + list.get(position).get("img").toString());
            save_title.setText((String) list.get(position).get("name"));
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
