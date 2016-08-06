package com.gxu.embroidery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.gxu.embroidery.db.domain.User;
import com.gxu.embroidery.pulltorefresh.task.CustomLastItemVisibleListener;
import com.gxu.embroidery.pulltorefresh.task.CustomRefreshListener;
import com.gxu.embroidery.pulltorefresh.task.CustomSoundPullEventListener;
import com.gxu.embroidery.utils.CommonUrl;
import com.gxu.embroidery.utils.HttpUtils;
import com.gxu.embroidery.utils.ImgUtils;
import com.gxu.embroidery.utils.JsonTools;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserMainActivity extends BaseActivity implements View.OnClickListener {


    private String webTitle[] = {"首页", "资讯", "湘绣", "蜀绣", "粤绣", "苏绣", "问答", "鉴赏"};
    private List<View> userMainViews;
    private List<View> userMainTopViews;
    private TabPageIndicator u_indicator;
    private ViewPager u_pager;
    private AQuery aq;
    private ProgressDialog dialog;
    private UserMainBaseAdapter adapter;
    private Map<String, Object> mapItem;
    private ImageButton person_center, right;
    private int topFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        init();
    }

    private void init() {
        new QDataTask().execute(CommonUrl.GET_ALL_COLUMN_DATA_URL);
        aq = new AQuery(this);

        u_pager = (ViewPager) this.findViewById(R.id.u_pager);
        u_indicator = (TabPageIndicator) this.findViewById(R.id.u_indicator);
        person_center = (ImageButton) this.findViewById(R.id.person_center);
        right = (ImageButton) this.findViewById(R.id.right);
        person_center.setOnClickListener(this);
        right.setOnClickListener(this);
    }


    public List<View> getList(List<List<Map<String, Object>>> listList) {
        List<View> list = new ArrayList<View>();
        for (int i = 0; i < webTitle.length; i++) {
            final int flag = i;
            topFlag = i;
            View view = (View) LayoutInflater.from(this).inflate(R.layout.user_main_item, null);
            userMainTopViews = getMainItemTopViews(flag);

            adapter = new UserMainBaseAdapter(listList.get(i), UserMainActivity.this, aq, new UserMainTopPageAdapter(userMainTopViews));
            final PullToRefreshListView ptView = (PullToRefreshListView) view.findViewById(R.id.main_item);

            ptView.setOnRefreshListener(new CustomRefreshListener(UserMainActivity.this, listList.get(i),
                    adapter, ptView));
            ptView.setOnLastItemVisibleListener(new CustomLastItemVisibleListener(
                    UserMainActivity.this));
            ptView.setOnPullEventListener(new CustomSoundPullEventListener(
                    UserMainActivity.this));

            ptView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            ptView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mapItem = (HashMap) ptView.getRefreshableView().getItemAtPosition(position);
                    Bundle bundle = new Bundle();
                    String table = webTitle[flag];
                    String num = (String) mapItem.get("num");
                    String title = (String) mapItem.get("title");

                    bundle.putString("table", webTitle[flag]);
                    bundle.putString("num", (String) mapItem.get("num"));
                    bundle.putString("title", (String) mapItem.get("title"));
                    bundle.putString("img", (String) mapItem.get("img"));
                    launch(UserMainInfoActivity.class, bundle);
                }
            });
            list.add(view);
        }
        return list;
    }

    private void getImage(final ImageView imageView, String url) {
        ImgUtils.loadImage(new ImgUtils.ImageCallBack() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);

            }
        }, url);

    }

    private List<View> getMainItemTopViews(int flag) {
        List<View> list[] = new List[webTitle.length];
        Resources res = this.getResources();
        for (int j = 0; j < list.length; j++) {
            list[j] = new ArrayList<View>();
        }
        for (int i = 0; i < 4; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.main_item_shang_detail, null);
            //加载滚动条
            ImageView wel_img = (ImageView) view.findViewById(R.id.images);
            wel_img.setImageResource(res.getIdentifier("daohanghome" + i, "drawable", this.getPackageName()));
            list[0].add(view);
        }
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.main_item_shang_detail, null);
            //加载滚动条
            ImageView wel_img = (ImageView) view.findViewById(R.id.images);
            wel_img.setImageResource(res.getIdentifier("daohangzixun" + i, "drawable", this.getPackageName()));
            list[1].add(view);
        }
        for (int i = 2; i < 8; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.main_item_shang_detail, null);
            //加载滚动条
            ImageView wel_img = (ImageView) view.findViewById(R.id.images);
            wel_img.setImageResource(res.getIdentifier("daohang" + i, "drawable", this.getPackageName()));
            list[i].add(view);
        }
        return list[flag];
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.person_center:
                User now_user = (User) LoginActivity.getNowUser();
                if (now_user != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("now_user", now_user);
                    launch(PersonCenterActivity.class, bundle);
                }
                break;
            case R.id.right:
                launch(SettingActivity.class);
                break;
            default:
                break;
        }

    }

    private class UserMainTopPageAdapter extends PagerAdapter {
        private List<View> views;

        public UserMainTopPageAdapter(List<View> views) {
            this.views = views;
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public int getCount() {
            return views.size();
        }

        // 显示界面方法
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));

        }

        // 销毁界面方法
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            views.get(position).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UserMainActivity.this, "您点击了第" + position + "张图", Toast.LENGTH_SHORT).show();
                }
            });
            ((ViewPager) container).addView(views.get(position));
            return views.get(position);
        }

    }

    private class UserMainPageAdapter extends PagerAdapter {

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public int getCount() {
            return webTitle.length;
        }

        public CharSequence getPageTitle(int position) {
            return webTitle[position];
        }


        // 显示界面方法
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(userMainViews.get(position));

        }

        // 销毁界面方法
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(userMainViews.get(position));
            return userMainViews.get(position);
        }


    }

    public class QDataTask extends AsyncTask<String, Void, List<List<Map<String, Object>>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(UserMainActivity.this, "正在加载...", "系统正在处理您的请求...请稍后！");
        }

        @Override
        protected List<List<Map<String, Object>>> doInBackground(String... params) {
            List<List<Map<String, Object>>> listList = new ArrayList<>();

            String jsonString0 = HttpUtils.getJsonContent(params[0]);
            List<Map<String, Object>> homeList = JsonTools.listKeyMaps("home_url", jsonString0);
            String jsonString1 = HttpUtils.getJsonContent(params[1]);
            List<Map<String, Object>> informationList = JsonTools.listKeyMaps("information_url", jsonString1);
            String jsonString2 = HttpUtils.getJsonContent(params[2]);
            List<Map<String, Object>> xiangxiuList = JsonTools.listKeyMaps("xiangxiu_url", jsonString2);
            String jsonString3 = HttpUtils.getJsonContent(params[3]);
            List<Map<String, Object>> shuxiuList = JsonTools.listKeyMaps("shuxiu_url", jsonString3);
            String jsonString4 = HttpUtils.getJsonContent(params[4]);
            List<Map<String, Object>> yuxiuList = JsonTools.listKeyMaps("yuxiu_url", jsonString4);
            String jsonString5 = HttpUtils.getJsonContent(params[5]);
            List<Map<String, Object>> suxiuList = JsonTools.listKeyMaps("suxiu_url", jsonString5);
            String jsonString6 = HttpUtils.getJsonContent(params[6]);
            List<Map<String, Object>> qandaList = JsonTools.listKeyMaps("qanda_url", jsonString6);
            String jsonString7 = HttpUtils.getJsonContent(params[7]);
            List<Map<String, Object>> appreciationList = JsonTools.listKeyMaps("appreciation_url", jsonString7);

            listList.add(homeList);
            listList.add(informationList);
            listList.add(xiangxiuList);
            listList.add(shuxiuList);
            listList.add(yuxiuList);
            listList.add(suxiuList);
            listList.add(qandaList);
            listList.add(appreciationList);

            return listList;
        }

        @Override
        protected void onPostExecute(List<List<Map<String, Object>>> listList) {
            super.onPostExecute(listList);
            dialog.dismiss();
            userMainViews = getList(listList);
            u_indicator.setVisibility(View.VISIBLE);
            u_pager.setAdapter(new UserMainPageAdapter());
            u_indicator.setViewPager(u_pager);
        }
    }

    public void launch(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        launch_slideright2left(intent);
    }


    public void launch_slideright2left(Intent it) {
        startActivity(it);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    public void launch(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
        overridePendingTransition(R.anim.push_right_in, android.R.anim.fade_out);
    }
}
