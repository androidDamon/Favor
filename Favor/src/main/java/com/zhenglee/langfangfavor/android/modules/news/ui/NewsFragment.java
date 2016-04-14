package com.zhenglee.langfangfavor.android.modules.news.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhenglee.framework.network.OkRequest;
import com.zhenglee.framework.network.callback.StringCallback;
import com.zhenglee.framework.res.annotation.DrawableResource;
import com.zhenglee.framework.res.annotation.LayoutResource;
import com.zhenglee.framework.res.annotation.StringResource;
import com.zhenglee.framework.ui.BaseFragment;
import com.zhenglee.framework.ui.OnPageSelectedListener;
import com.zhenglee.langfangfavor.R;

import okhttp3.Request;
import okhttp3.Response;

@DrawableResource(R.drawable.ic_launcher)
@StringResource(R.string.news)
@LayoutResource(R.layout.fragment_news)
public class NewsFragment extends BaseFragment implements OnPageSelectedListener{

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        OkRequest.get("http://c.m.163.com/nc/article/headline/T1348647909107/20-20.html").tag(this).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPageSelected() {
//        Toasts.show(this.getActivity(),"aaaaa", Toast.LENGTH_SHORT);

    }

    private class NewsCallback extends StringCallback{
        @Override
        public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {

        }
//        public NewsCallback(Fragment fragment, Class<T> clazz){
//            super(fragment.getActivity(),clazz);
//        }
//
//        @Override
//        public void onResponse(boolean isFromCache, T t, Request request, @Nullable Response response) {
////            List<News> list = (List<News>) t;
//
//        }
    }
}
