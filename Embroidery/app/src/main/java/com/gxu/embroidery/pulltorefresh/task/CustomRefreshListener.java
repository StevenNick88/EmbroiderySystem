package com.gxu.embroidery.pulltorefresh.task;

import android.content.Context;
import android.text.format.DateUtils;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

/**
 * Created by Administrator on 2015/2/23.
 */
public class CustomRefreshListener implements PullToRefreshBase.OnRefreshListener<ListView>{

    private Context context;
    private List list;
    private BaseAdapter baseAdapter;
    private PullToRefreshListView pullToRefreshListView;

    public CustomRefreshListener(Context context, List list, BaseAdapter baseAdapter,
                                 PullToRefreshListView pullToRefreshListView) {
        this.context = context;
        this.list = list;
        this.baseAdapter = baseAdapter;
        this.pullToRefreshListView = pullToRefreshListView;
    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        String label = DateUtils.formatDateTime(context, System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

        // Update the LastUpdatedLabel
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

        // Do work to refresh the list here.
        new GetDataTask(list,baseAdapter,pullToRefreshListView).execute();

    }
}
