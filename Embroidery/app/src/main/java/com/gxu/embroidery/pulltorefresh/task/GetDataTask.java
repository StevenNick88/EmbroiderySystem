package com.gxu.embroidery.pulltorefresh.task;

import android.os.AsyncTask;
import android.widget.BaseAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

/**
 * Created by Administrator on 2015/2/23.
 */
public class GetDataTask extends AsyncTask<Void, Void, List> {
    private List list;
    private BaseAdapter baseAdapter;
    private PullToRefreshListView pullToRefreshListView;

    //将参数改为List，BaseAdapter增加兼容性。
    public GetDataTask(List list,
                       BaseAdapter baseAdapter,
                       PullToRefreshListView pullToRefreshListView) {
        this.list = list;
        this.baseAdapter = baseAdapter;
        this.pullToRefreshListView = pullToRefreshListView;
    }

    @Override
    protected List doInBackground(Void... params) {
        // Simulates a background job.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        return list;
    }

    @Override
    protected void onPostExecute(List result) {
        baseAdapter.notifyDataSetChanged();

        // Call onRefreshComplete when the list has been refreshed.
        pullToRefreshListView.onRefreshComplete();

        super.onPostExecute(result);
    }
}
