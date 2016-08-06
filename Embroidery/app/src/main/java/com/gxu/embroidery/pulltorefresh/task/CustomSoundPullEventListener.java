package com.gxu.embroidery.pulltorefresh.task;

import android.content.Context;

import com.gxu.embroidery.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

/**
 * Created by Administrator on 2015/2/23.
 */
public class CustomSoundPullEventListener extends SoundPullEventListener {
    /**
     * Constructor
     * Add Sound Event Listener
     * @param context - Context
     */
    public CustomSoundPullEventListener(Context context) {
        super(context);
        this.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        this.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        this.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
    }

}
