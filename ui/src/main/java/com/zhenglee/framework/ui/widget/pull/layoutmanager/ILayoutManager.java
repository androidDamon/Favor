package com.zhenglee.framework.ui.widget.pull.layoutmanager;

import android.support.v7.widget.RecyclerView;

import com.zhenglee.framework.ui.widget.pull.BaseListAdapter;


public interface ILayoutManager {
    RecyclerView.LayoutManager getLayoutManager();
    int findLastVisiblePosition();
    void setUpAdapter(BaseListAdapter adapter);
}
