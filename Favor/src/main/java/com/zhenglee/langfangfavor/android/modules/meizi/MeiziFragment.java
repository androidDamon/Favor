package com.zhenglee.langfangfavor.android.modules.meizi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhenglee.framework.network.OkRequest;
import com.zhenglee.framework.network.cache.CacheMode;
import com.zhenglee.framework.network.callback.StringCallback;
import com.zhenglee.framework.res.annotation.DrawableResource;
import com.zhenglee.framework.res.annotation.IdResource;
import com.zhenglee.framework.res.annotation.LayoutResource;
import com.zhenglee.framework.res.annotation.StringResource;
import com.zhenglee.framework.ui.BaseFragment;
import com.zhenglee.framework.ui.widget.pull.BaseListAdapter;
import com.zhenglee.framework.ui.widget.pull.BaseViewHolder;
import com.zhenglee.framework.ui.widget.pull.PullToRefreshRecyclerView;
import com.zhenglee.framework.ui.widget.pull.layoutmanager.ILayoutManager;
import com.zhenglee.framework.ui.widget.pull.layoutmanager.MyLinearLayoutManager;
import com.zhenglee.langfangfavor.R;

import java.util.ArrayList;

import okhttp3.Request;
import okhttp3.Response;

@DrawableResource(R.drawable.ic_launcher)
@StringResource(R.string.meizi)
@LayoutResource(R.layout.fragment_meizi)
public class MeiziFragment extends BaseFragment implements PullToRefreshRecyclerView.OnRecyclerRefreshListener {

    @IdResource(R.id.fragment_meizi_recyclerview)
    private PullToRefreshRecyclerView pullToRefreshRecyclerView;

    private ArrayList<String> meiziList;

    private ListAdapter adapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullToRefreshRecyclerView.setRefreshing();
        adapter = new ListAdapter();
        pullToRefreshRecyclerView.setOnRefreshListener(this);
        pullToRefreshRecyclerView.setLayoutManager(getLayoutManager());
//        pullRecycler.addItemDecoration(getItemDecoration());
        pullToRefreshRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getContext());
    }

//    protected RecyclerView.ItemDecoration getItemDecoration() {
//        return new DividerItemDecoration(getContext(), R.drawable.list_divider);
//    }

    private class ListAdapter extends BaseListAdapter {

        @Override
        protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(parent, viewType);
        }

        @Override
        protected int getDataCount() {
            return meiziList != null ? meiziList.size() : 0;
        }

        @Override
        protected int getDataViewType(int position) {
            return getItemType(position);
        }

        @Override
        public boolean isSectionHeader(int position) {
            return this.isSectionHeader(position);
        }
    }

    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_meizi_list_item, parent, false);
        return new SampleViewHolder(view);
    }

    protected int getItemType(int position) {
        return 0;
    }


    @Override
    public void onRefresh(final int action) {
//        OkRequest.get("http://c.m.163.com/nc/article/headline/T1348647909107/20-20.html").tag(this).execute(new StringCallback() {
//            @Override
//            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
//
//            }
//        });
        if (meiziList == null) {
            meiziList = new ArrayList<>();
        } else {
            OkRequest ok = OkRequest.getInstance();
            ok.get("http://server.jeasonlzy.com/OkHttpUtils/method").tag(this).cacheKey("adad").cacheMode(CacheMode.DEFAULT) .execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {

            }
        });
        }

        pullToRefreshRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (action == PullToRefreshRecyclerView.ACTION_PULL_TO_REFRESH) {
                    meiziList.clear();
                }
                int size = meiziList.size();
                for (int i = size; i < size + 20; i++) {
                    meiziList.add(MeiziValues.images[i]);
                }
                adapter.notifyDataSetChanged();
                pullToRefreshRecyclerView.onRefreshCompleted();
                if (meiziList.size() < 100) {
                    pullToRefreshRecyclerView.enableLoadMore(true);
                } else {
                    pullToRefreshRecyclerView.enableLoadMore(false);
                }
            }
        }, 2000);
    }

    private final class SampleViewHolder extends BaseViewHolder {

        private ImageView image;
        private TextView label;

        public SampleViewHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.mSampleListItemLabel);
            image = (ImageView) itemView.findViewById(R.id.mSampleListItemImg);
        }

        @Override
        public void onBindViewHolder(int position) {
            label.setVisibility(View.GONE);
            Glide.with(image.getContext()).load(meiziList.get(position)).centerCrop().placeholder(R.color.colorPrimary).crossFade().into(image);
        }

        @Override
        public void onItemClick(View view, int position) {

        }

    }
}
