package com.gxu.embroidery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gxu.embroidery.utils.CommonUrl;
import com.gxu.embroidery.utils.CommonUtils;
import com.gxu.embroidery.utils.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminMainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView admin_main_lv;
    private AdminMainBaseAdapter adapter;
    private String[] adminMainItem;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        init();
    }

    private void init() {

        admin_main_lv= (ListView)this.findViewById(R.id.admin_main_lv);
        adminMainItem =  this.getResources().getStringArray(R.array.admin_main_item);
        adapter=new AdminMainBaseAdapter(this);

        adapter.setData(getPersonData());
        admin_main_lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        admin_main_lv.setOnItemClickListener(this);
    }


    private List<Map<String, Object>> getPersonData() {
        Resources res = this.getResources();
        //每个数据项中的数据由Map来存储
        List<Map<String, Object>> data = new ArrayList();
        for (int i = 0; i < adminMainItem.length; i++) {
            Map<String, Object> map = new HashMap();
            map.put("img", res.getIdentifier("person_center_item", "drawable", this.getPackageName()));
            map.put("name", adminMainItem[i]);

            data.add(map);
        }
        return data;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                launch(UserMangeActivity.class);
                break;
            case 1:
                launch(AdminMangeActivity.class);
                break;
            //系统备份
            case 2:
                doSystemDataBackup();
                break;
            case 3:
                launch(ChangePwdActivity.class);
                break;
            //注销
            case 4:
                msgWithEvent(AdminMainActivity.this, "提示", "确定", "取消", "确定要注销吗？");
                break;
            //退出
            case 5:
                onBackPressed();
                break;
            default:
                break;
        }

    }


    long waitTime = 2000;
    long touchTime = 0;

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - touchTime) >= waitTime) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            touchTime = currentTime;
        } else {
            finish();
        }
    }

    @Override
    public void onPositiveEvent() {
        launch(LoginActivity.class);
    }

    private class AdminMainBaseAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Map<String, Object>> list = null;

        public AdminMainBaseAdapter(Context context) {
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


    private void doSystemDataBackup() {
        //执行异步任务获取网络数据
        new SystemDataTask().execute(CommonUrl.SYSTEM_DATA_BACKUP);
    }

    public class SystemDataTask extends AsyncTask<String, Void, List<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(AdminMainActivity.this, "正在加载...", "系统正在处理您的请求...");
        }

        @Override
        protected List<String> doInBackground(String... params) {
            List<String> list = new ArrayList<>();

            String jsonString0 = HttpUtils.getJsonContent(params[0]);
            String jsonString1 = HttpUtils.getJsonContent(params[1]);
            String jsonString2 = HttpUtils.getJsonContent(params[2]);
            String jsonString3 = HttpUtils.getJsonContent(params[3]);
            String jsonString4 = HttpUtils.getJsonContent(params[4]);
            String jsonString5 = HttpUtils.getJsonContent(params[5]);
            String jsonString6 = HttpUtils.getJsonContent(params[6]);
            String jsonString7 = HttpUtils.getJsonContent(params[7]);
            String jsonString8 = HttpUtils.getJsonContent(params[8]);
            String jsonString9 = HttpUtils.getJsonContent(params[9]);

            list.add(jsonString0);
            list.add(jsonString1);
            list.add(jsonString2);
            list.add(jsonString3);
            list.add(jsonString4);
            list.add(jsonString5);
            list.add(jsonString6);
            list.add(jsonString7);
            list.add(jsonString8);
            list.add(jsonString9);

            return list;
        }

        @Override
        protected void onPostExecute(List<String> lists) {
            super.onPostExecute(lists);
            dialog.dismiss();
            byte[] bytes= CommonUtils.ChangeListToByte(lists);
            String path=CommonUtils.saveFileToSdcard("EmbroideryData.txt",bytes);
            if (!path.equals("")){
                msg(AdminMainActivity.this, "提示", "备份成功,文件存储于"+path+"目录下！");
            }
        }

    }


}
