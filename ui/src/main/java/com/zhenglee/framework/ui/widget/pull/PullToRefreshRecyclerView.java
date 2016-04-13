package com.zhenglee.framework.ui.widget.pull;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.zhenglee.framework.ui.R;
import com.zhenglee.framework.ui.widget.pull.layoutmanager.ILayoutManager;


public class PullToRefreshRecyclerView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {

    public static final int ACTION_IDLE = 0;
    public static final int ACTION_PULL_TO_REFRESH = 1;
    public static final int ACTION_LOAD_MORE_REFRESH = 2;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private OnRecyclerRefreshListener listener;
    private int currentState = ACTION_IDLE;
    private boolean isLoadMoreEnabled = false;
    private boolean isPullToRefreshEnabled = true;
    private ILayoutManager layoutManager;
    private BaseListAdapter adapter;

    public PullToRefreshRecyclerView(Context context) {
        super(context);
        setUpView();
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpView();
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpView();
    }

    private void setUpView() {
        LayoutInflater.from(getContext()).inflate(R.layout.pull_to_refresh, this, true);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (currentState == ACTION_IDLE && isLoadMoreEnabled && checkIfNeedLoadMore()) {
                    currentState = ACTION_LOAD_MORE_REFRESH;
                    adapter.onLoadMoreStateChanged(true);
                    swipeRefreshLayout.setEnabled(false);
                    listener.onRefresh(ACTION_LOAD_MORE_REFRESH);
                }
            }
        });
    }

    private boolean checkIfNeedLoadMore() {
        int lastVisibleItemPosition = layoutManager.findLastVisiblePosition();
        int totalCount = layoutManager.getLayoutManager().getItemCount();
        return totalCount - lastVisibleItemPosition < 5;
    }

    public void enableLoadMore(boolean enable) {
        isLoadMoreEnabled = enable;
    }

    public void enablePullToRefresh(boolean enable) {
        isPullToRefreshEnabled = enable;
        swipeRefreshLayout.setEnabled(enable);
    }

    public void setLayoutManager(ILayoutManager manager) {
        this.layoutManager = manager;
        recyclerView.setLayoutManager(manager.getLayoutManager());
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decoration) {
        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
    }

    public void setAdapter(BaseListAdapter adapter) {
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);
        layoutManager.setUpAdapter(adapter);
    }

    public void setRefreshing() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    public void setOnRefreshListener(OnRecyclerRefreshListener listener) {
        this.listener = listener;
    }

    @Override
    public void onRefresh() {
        currentState = ACTION_PULL_TO_REFRESH;
        listener.onRefresh(ACTION_PULL_TO_REFRESH);
    }

    public void onRefreshCompleted() {
        switch (currentState) {
            case ACTION_PULL_TO_REFRESH:
                swipeRefreshLayout.setRefreshing(false);
                break;
            case ACTION_LOAD_MORE_REFRESH:
                adapter.onLoadMoreStateChanged(false);
                if (isPullToRefreshEnabled) {
                    swipeRefreshLayout.setEnabled(true);
                }
                break;
        }
        currentState = ACTION_IDLE;
    }

    public void setSelection(int position) {
        recyclerView.scrollToPosition(position);
    }


    public interface OnRecyclerRefreshListener {
        void onRefresh(int action);
    }
}
