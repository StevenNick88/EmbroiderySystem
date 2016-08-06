package com.gxu.embroidery.pulltorefresh.task;

import android.content.Context;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by Administrator on 2015/2/23.
 */
public class CustomLastItemVisibleListener implements PullToRefreshBase.OnLastItemVisibleListener{

    private Context context;

    public CustomLastItemVisibleListener(Context context) {
        this.context = context;
    }

    @Override
    public void onLastItemVisible() {
        Toast.makeText(context, "End of List!", Toast.LENGTH_SHORT).show();
    }
}
