package com.gxu.embroidery;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gxu.embroidery.db.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SettingActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private uk.co.senab.photoview.PhotoViewAttacher mAttacher;//图片放大缩小的包装器
    private final int BQ_PAGER = 5;
    private List<View> mqPagerViews;
    private ViewPager setting_pager;
    private ListView setting_lv;
    private String[] setting_item;
    private SettingBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    private void init() {
        mqPagerViews = getViews();
        setting_pager = (ViewPager) this.findViewById(R.id.setting_pager);
        setting_pager.setAdapter(new BQPagerAdapter());

        setting_lv = (ListView) this.findViewById(R.id.setting_lv);
        setting_item = this.getResources().getStringArray(R.array.setting_item);
        adapter = new SettingBaseAdapter(this);

        adapter.setData(getSettingData());
        setting_lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setting_lv.setOnItemClickListener(this);
    }


    private List<Map<String, Object>> getSettingData() {
        Resources res = this.getResources();
        List<Map<String, Object>> data = new ArrayList();
        for (int i = 0; i < setting_item.length; i++) {
            Map<String, Object> map = new HashMap();
            map.put("img", res.getIdentifier("person_center_item", "drawable", this.getPackageName()));
            map.put("name", setting_item[i]);

            data.add(map);
        }
        return data;

    }

    public List<View> getViews() {
        List<View> views = new ArrayList();
        Resources res = this.getResources();
        for (int i = 0; i < BQ_PAGER; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(res.getIdentifier("pager" + i, "drawable", this.getPackageName()));
            //实现图片放大缩小
            mAttacher = new uk.co.senab.photoview.PhotoViewAttacher(iv);
            LinearLayout ll = new LinearLayout(this);
            ll.setGravity(Gravity.CENTER);
            ll.addView(iv);
            views.add(ll);
        }
        return views;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            //修改密码
            case 0:
                launch(ChangePwdActivity.class);
                break;
            //在线学习
            case 1:
                launch(WebViewActivity.class);
                break;
            //帮助
            case 2:
                msg(SettingActivity.this, "帮助提示", getResources().getString(R.string.help_value));
                break;
            //关于
            case 3:
                msg(SettingActivity.this, "关于软件", getResources().getString(R.string.about_software_value));
                break;
            //注销
            case 4:
                msgWithEvent(SettingActivity.this, "提示", "确定", "取消", "确定要注销吗？");
                break;
            //退出
            case 5:
                onBackPressed();
                break;
            default:
                break;
        }


    }

    @Override
    public void onPositiveEvent() {
        launch(LoginActivity.class);
    }
    private class BQPagerAdapter extends PagerAdapter {

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public int getCount() {
            return mqPagerViews.size();
        }

        //显示界面方法
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            (container).removeView(mqPagerViews.get(position));
        }

        //销毁界面方法
        public Object instantiateItem(ViewGroup container, int position) {
            (container).addView(mqPagerViews.get(position));
            return mqPagerViews.get(position);
        }
    }


    private class SettingBaseAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Map<String, Object>> list = null;

        public SettingBaseAdapter(Context context) {
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
            person_item_img.setImageResource((Integer) list.get(position).get("img"));
            person_list_item_name.setText((String) list.get(position).get("name"));
            return view;
        }
    }


}
