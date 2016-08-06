package com.gxu.embroidery;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxu.embroidery.db.domain.Admin;
import com.gxu.embroidery.db.domain.Save;
import com.gxu.embroidery.db.domain.User;
import com.gxu.embroidery.utils.CommonUrl;
import com.gxu.embroidery.utils.HttpUtils;
import com.gxu.embroidery.utils.ImgUtils;
import com.gxu.embroidery.utils.JsonService;
import com.gxu.embroidery.utils.JsonTools;
import com.gxu.embroidery.utils.ShareUtils;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserMainInfoActivity extends BaseActivity implements View.OnClickListener {

    private TextView d_title, d_detail1, d_detail2, d_detail3, d_detail4, d_detail5, d_detail6, d_detail7;
    private ImageView d_img, d_img1, d_img2, d_img3, d_img4, d_img5, d_img6, d_img7;
    private String table, num, reTable, title, img;
    private ProgressDialog dialog;
    private Button share, save;
    private Map<String, Object> mapForSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_info);
        init();
    }

    private void init() {
        d_title = (TextView) this.findViewById(R.id.d_title);
        d_detail1 = (TextView) this.findViewById(R.id.d_detail1);
        d_detail2 = (TextView) this.findViewById(R.id.d_detail2);
        d_detail3 = (TextView) this.findViewById(R.id.d_detail3);
        d_detail4 = (TextView) this.findViewById(R.id.d_detail4);
        d_detail5 = (TextView) this.findViewById(R.id.d_detail5);
        d_detail6 = (TextView) this.findViewById(R.id.d_detail6);
        d_detail7 = (TextView) this.findViewById(R.id.d_detail7);
        d_img = (ImageView) this.findViewById(R.id.d_img);
        d_img1 = (ImageView) this.findViewById(R.id.d_img1);
        d_img2 = (ImageView) this.findViewById(R.id.d_img2);
        d_img3 = (ImageView) this.findViewById(R.id.d_img3);
        d_img4 = (ImageView) this.findViewById(R.id.d_img4);
        d_img5 = (ImageView) this.findViewById(R.id.d_img5);
        d_img6 = (ImageView) this.findViewById(R.id.d_img6);
        d_img7 = (ImageView) this.findViewById(R.id.d_img7);
        share = (Button) this.findViewById(R.id.share);
        save = (Button) this.findViewById(R.id.save);
        share.setOnClickListener(this);
        save.setOnClickListener(this);

        table = (String) getIntent().getSerializableExtra("table");  //表名
        num = (String) getIntent().getSerializableExtra("num");     //表编号字段
        title = (String) getIntent().getSerializableExtra("title"); //item标题
        img = (String) getIntent().getSerializableExtra("img");     //item标题图
        getRelTableName();

        new QDetailDataTask().execute(CommonUrl.GET_DETAIL_DATA);
    }

    private void showUI(Map<String, Object> map) {
        d_title.setText(title);
        d_detail1.setText((String) map.get("detail1"));
        d_detail2.setText((String) map.get("detail2"));
        d_detail3.setText((String) map.get("detail3"));
        d_detail4.setText((String) map.get("detail4"));
        d_detail5.setText((String) map.get("detail5"));
        d_detail6.setText((String) map.get("detail6"));
        d_detail7.setText((String) map.get("detail7"));
        getImage(d_img, CommonUrl.LOAD_IMG_TITLE + img);
        getImage(d_img1, CommonUrl.LOAD_IMG_DETAIL + ((String) map.get("img1")));
        getImage(d_img2, CommonUrl.LOAD_IMG_DETAIL + ((String) map.get("img2")));
        getImage(d_img3, CommonUrl.LOAD_IMG_DETAIL + ((String) map.get("img3")));
        getImage(d_img4, CommonUrl.LOAD_IMG_DETAIL + ((String) map.get("img4")));
        getImage(d_img5, CommonUrl.LOAD_IMG_DETAIL + ((String) map.get("img5")));
        getImage(d_img6, CommonUrl.LOAD_IMG_DETAIL + ((String) map.get("img6")));
        getImage(d_img7, CommonUrl.LOAD_IMG_DETAIL + ((String) map.get("img7")));
    }

    private void getImage(final ImageView imageView, String url) {
        ImgUtils.loadImage(new ImgUtils.ImageCallBack() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);

            }
        }, url);

    }

    private void getRelTableName() {
        if (table != null) {
            if (table.equals("首页")) {
                reTable = "home";
            } else if (table.equals("资讯")) {
                reTable = "information";
            } else if (table.equals("湘绣")) {
                reTable = "xiangxiu";
            } else if (table.equals("蜀绣")) {
                reTable = "shuxiu";
            } else if (table.equals("粤绣")) {
                reTable = "yuxiu";
            } else if (table.equals("苏绣")) {
                reTable = "suxiu";
            } else if (table.equals("问答")) {
                reTable = "qanda";
            } else if (table.equals("鉴赏")) {
                reTable = "appreciation";
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share:
                ShareUtils.showShare(UserMainInfoActivity.this, null, title, null);
                break;
            //收藏
            case R.id.save:
                saveItem();
                break;
            default:
                break;
        }

    }

    private void saveItem() {
        User now_user = (User) LoginActivity.getNowUser();
        String u_num = null;
        if (now_user != null) {
            u_num = now_user.getU_num();
        }
        Map<String, String> map = new HashMap<>();
        map.put("s_num", (String) mapForSave.get("num"));
        map.put("u_num", u_num);
        Save saveItem = null;
        try {
            String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.SAVE_URL,
                    JsonService.createJsonString(map));
            saveItem = JsonTools.getSave("save", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (saveItem != null && saveItem.getS_num() != null) {
            msg(this, "提示", "您已经收藏过该教程，请勿重复收藏！");
        } else {
            Map<String, String> map2 = new HashMap<>();
            map2.put("u_num", u_num);
            map2.put("s_table", table);
            map2.put("s_num", (String) mapForSave.get("num"));
            map2.put("s_title", title);
            map2.put("s_img", img);
            Boolean flag2 = null;
            try {
                flag2 = HttpUtils.sendJavaBeanToServer(CommonUrl.ADD_SAVE_URL,
                        JsonService.createJsonString(map2));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (flag2 == true) {
                msg(this, "提示", "收藏成功！");
                save.setEnabled(false);
            } else {
                msg(this, "提示", "收藏失败！");
            }
        }
    }

    public class QDetailDataTask extends AsyncTask<String, Void, Map<String, Object>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(UserMainInfoActivity.this, "正在加载...", "系统正在处理您的请求...");
        }

        @Override
        protected Map<String, Object> doInBackground(String... params) {
            Map<String, Object> map = new HashMap<>();

            Map<String, String> map2 = new HashMap<>();
            map2.put("re_table", reTable);
            map2.put("num", num);
            String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.GET_DETAIL_DATA,
                    JsonService.createJsonString(map2));
            map = JsonTools.getMaps("send_to_client", jsonString);

            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);
            dialog.dismiss();
            showUI(map);
            mapForSave = map;
        }
    }


}
