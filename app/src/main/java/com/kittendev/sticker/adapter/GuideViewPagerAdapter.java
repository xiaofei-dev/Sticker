package com.kittendev.sticker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.kittendev.sticker.page.SettingPage;
import com.kittendev.sticker.page.WelcomePage;

public class GuideViewPagerAdapter extends PagerAdapter {

    private WelcomePage welcomePage;
    private SettingPage settingPage;

    public GuideViewPagerAdapter(Context context) {
        welcomePage = new WelcomePage(context);
        settingPage = new SettingPage(context);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view;
        if (position == 0) {
            view = welcomePage.getView();
        } else {
            view = settingPage.getView();
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view;
        if (position == 0) {
            view = welcomePage.getView();
        } else {
            view = settingPage.getView();
        }
        container.removeView(view);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

}