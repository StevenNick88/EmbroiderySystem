package com.gxu.embroidery;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends Activity implements View.OnClickListener {

    private String titles[] = new String[5];
    private List<View> views;
    private uk.co.senab.photoview.PhotoViewAttacher mAttacher;// 图片放大缩小的包装器

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }

    public void init() {
        ViewPager pager = (ViewPager) this.findViewById(R.id.vpager);
        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) this
                .findViewById(R.id.cicletdtor);
        views = new ArrayList();
        Resources res = this.getResources();
        for (int i = 0; i < titles.length; i++) {

            View welView=LinearLayout.inflate(WelcomeActivity.this, R.layout.welcome_list, null);
            ImageView wel_img =(ImageView)welView.findViewById(R.id.wel_img);
            wel_img.setImageResource(res.getIdentifier("imgs" + i, "drawable",
                    this.getPackageName()));
            // 实现图片放大缩小
            mAttacher = new uk.co.senab.photoview.PhotoViewAttacher(wel_img);
            Button wel_btn =(Button)welView.findViewById(R.id.wel_btn);
            if(i==titles.length-1){
                wel_btn.setVisibility(View.VISIBLE);
                wel_btn.setOnClickListener(this);
            }
            views.add(welView);

        }
        pager.setAdapter(new ViewPagerAdapter());
        // 显示
        circlePageIndicator.setViewPager(pager);

    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;

        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        // 显示界面方法
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));

        }

        // 销毁界面方法
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(views.get(position));
            return views.get(position);
        }

    }

    @Override
    public void onClick(View v) {
        // 跳转
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }


}