package com.gxu.embroidery;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.gxu.embroidery.utils.CommonUrl;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/26.
 */
public class UserMainBaseAdapter extends BaseAdapter {

    List<Map<String, Object>> datas;
    Activity act;
    AQuery aq;
    PagerAdapter pAdapter;

    public UserMainBaseAdapter(List<Map<String, Object>> datas, Activity act,
                                      AQuery aq,PagerAdapter pAdapter) {
        this.datas = datas;
        this.act = act;
        this.aq=aq;
        this.pAdapter=pAdapter;

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position > 0 ? 0 : 1;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position==0){
            View v = null;
            if (convertView == null) {
                v = act.getLayoutInflater().inflate(R.layout.main_item_shang, null);
            } else {
                v = convertView;
            }
            ViewPager lv_shang=(ViewPager)v.findViewById(R.id.lv_shang);
            lv_shang.setAdapter(pAdapter);
            pAdapter.notifyDataSetChanged();
            return v;
        }else{
            View v = null;
            if (convertView == null) {
                v = act.getLayoutInflater().inflate(R.layout.main_item_xia, null);
            } else {
                v = convertView;
            }
            ImageView img = (ImageView)v.findViewById(R.id.imglist);
            TextView name = (TextView)v.findViewById(R.id.tvnews);
            final Map<String, Object> data = datas.get(position);
            aq.id(R.id.imglist).progress(R.id.progressBar).image(CommonUrl.LOAD_IMG_TITLE+data.get("img").toString(), false, false);
            aq.id(img).image(CommonUrl.LOAD_IMG_TITLE+data.get("img").toString());
            name.setText(data.get("title").toString());
//            name.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    Toast.makeText(act, data.get("title")+"", Toast.LENGTH_SHORT).show();
//                }
//            });
            return v;
        }
    }
}
