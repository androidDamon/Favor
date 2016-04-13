package com.zhenglee.langfangfavor.android.modules.news;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhenglee.framework.res.annotation.DrawableResource;
import com.zhenglee.framework.res.annotation.LayoutResource;
import com.zhenglee.framework.res.annotation.StringResource;
import com.zhenglee.framework.ui.BaseFragment;
import com.zhenglee.framework.ui.OnPageSelectedListener;
import com.zhenglee.langfangfavor.R;

@DrawableResource(R.drawable.ic_launcher)
@StringResource(R.string.app_name)
@LayoutResource(R.layout.fragment_news)
public class NewsFragment extends BaseFragment implements OnPageSelectedListener{

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPageSelected() {
//        Toasts.show(this.getActivity(),"aaaaa", Toast.LENGTH_SHORT);
    }
}
