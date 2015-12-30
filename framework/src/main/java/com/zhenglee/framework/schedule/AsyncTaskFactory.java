package com.zhenglee.framework.schedule;

import android.content.Context;

/**
 * Created by zhenglee on 15/11/12.
 */
public class AsyncTaskFactory {
    private AsyncTaskFactory() {
    }

    public static AsyncTaskQueue newTaskQueue(Context context) {
        return new AsyncTaskQueue(context);
    }

}
