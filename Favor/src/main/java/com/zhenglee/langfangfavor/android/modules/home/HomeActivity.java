package com.zhenglee.langfangfavor.android.modules.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhenglee.framework.res.annotation.DrawableResource;
import com.zhenglee.framework.res.annotation.IdResource;
import com.zhenglee.framework.res.annotation.LayoutResource;
import com.zhenglee.framework.res.annotation.StringResource;
import com.zhenglee.framework.ui.BaseActivity;
import com.zhenglee.framework.ui.OnPageSelectedListener;
import com.zhenglee.framework.utils.DimensionUtils;
import com.zhenglee.langfangfavor.R;
import com.zhenglee.langfangfavor.android.modules.home.annotation.Tag;
import com.zhenglee.langfangfavor.android.modules.news.NewsFragment;

import java.util.ArrayList;
import java.util.List;

@LayoutResource(R.layout.activity_home)
public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "HomeActivity";
    private Class<?>[] TABS;

    private static boolean promptUpdateDialog = true;

    @IdResource(R.id.home_activity_toolbar)
    private Toolbar toolbar;

    @IdResource(R.id.home_activity_tab_bar)
    private LinearLayout tabBar;

    @IdResource(R.id.home_activity_tab_content)
    private ViewPager tabContent;

    private TabbedFragmentAdapter adapter;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        TABS = new Class[4];
        TABS[0] = NewsFragment.class;
        TABS[1] = NewsFragment.class;
        TABS[2] = NewsFragment.class;
        TABS[3] = NewsFragment.class;

        final Resources res = getResources();
        final LayoutInflater inflater = getLayoutInflater();
        final FragmentManager fm = getSupportFragmentManager();

        this.sp = getSharedPreferences("Favor", 0);

        this.adapter = new TabbedFragmentAdapter(this, fm);

        this.tabContent.setAdapter(this.adapter);
        this.tabContent.setOnPageChangeListener(this);
        this.tabContent.setOffscreenPageLimit(TABS.length);

        for (int i = 0; i < TABS.length; i++)

        {
            final Class<?> clazz = TABS[i];
            final DrawableResource dr = clazz.getAnnotation(DrawableResource.class);
            final StringResource sr = clazz.getAnnotation(StringResource.class);
            final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.home_activity_tab_bar_item, null);
            final TextView item = (TextView) view.findViewById(R.id.home_activity_tab_bar_name);
            final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
            final TextView dot = (TextView) view.findViewById(R.id.home_activity_tab_bar_dot);
            final Tag tag = clazz.getAnnotation(Tag.class);

            if (null != tag) {
                view.setTag(tag.value());
                int count = sp.getInt(tag.value(), 0);
                LayoutParams dotLp = dot.getLayoutParams();
                if (tag.value().equals(NewsFragment.class)) {
                    if (count < 0) {
                        dotLp.width = DimensionUtils.dip2px(this, 8);
                        dotLp.height = DimensionUtils.dip2px(this, 8);
                        dot.setLayoutParams(dotLp);
                    }

                    if (count > 0) {
                        dotLp.width = LayoutParams.WRAP_CONTENT;
                        dotLp.height = LayoutParams.WRAP_CONTENT;
                        dot.setLayoutParams(dotLp);
                    }
                } else {
                    dotLp.width = LayoutParams.WRAP_CONTENT;
                    dotLp.height = LayoutParams.WRAP_CONTENT;
                    dot.setLayoutParams(dotLp);
                }

                if (count > 0) {
                    if (count > 99) {
                        dot.setText("...");
                    } else {
                        dot.setText(String.valueOf(count));
                    }
                }

                dot.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
            }
            final int id = i;
            final View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setSelected(true);
                    tabContent.setCurrentItem(id, false);
                }

            };
            item.setCompoundDrawablesWithIntrinsicBounds(null, res.getDrawable(dr.value()), null, null);
            item.setText(res.getString(sr.value()));
            item.setOnClickListener(listener);
            view.setOnClickListener(listener);
            view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    item.setSelected(hasFocus);
                }
            });

            this.tabBar.addView(view, lp);
        }

        this.onPageSelected(0);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        final int n = this.tabBar.getChildCount();

        for (int i = 0; i < n; i++) {
            this.tabBar.getChildAt(i).setSelected(i == position);
        }

        final Fragment f = this.adapter.getItem(position);
        if (f instanceof OnPageSelectedListener) {
            ((OnPageSelectedListener) f).onPageSelected();
        }

        final StringResource sr = f.getClass().getAnnotation(StringResource.class);
        this.setTitle(sr.value());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private final class TabbedFragmentAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragments = new ArrayList<>();

        public TabbedFragmentAdapter(Context ctx, FragmentManager fm) {
            super(fm);

            for (int i = 0; i < TABS.length; i++) {
                this.fragments.add(Fragment.instantiate(ctx, TABS[i].getName()));
            }
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }

    }
}
