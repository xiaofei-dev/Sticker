package com.kittendev.sticker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.kittendev.sticker.model.StickerPackageManagerModel;
import com.kittendev.sticker.page.StickerPage;

import java.util.ArrayList;
import java.util.List;

public class StickerViewPagerAdapter extends PagerAdapter {

    private StickerPackageManagerModel stickerPackageManagerModel;
    private List<StickerPage> stickerPageList = new ArrayList<>();

    public StickerViewPagerAdapter(Context context, StickerPackageManagerModel stickerPackageManagerModel) {
        this.stickerPackageManagerModel = stickerPackageManagerModel;
        for (int i = 0; i < stickerPackageManagerModel.getStickerPackageNumber(); i++) {
            stickerPageList.add(new StickerPage(context, stickerPackageManagerModel.getStickerPackageModel(i)));
        }
    }

    @Override
    public int getCount() {
        return stickerPageList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        StickerPage stickerPage = stickerPageList.get(position);
        container.addView(stickerPage.getView());
        return stickerPage.getView();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(stickerPageList.get(position).getView());
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stickerPackageManagerModel.getStickerPackageName(position) + "(" + stickerPackageManagerModel.getStickerPackageModel(position).getNumber() + ")";
    }
}
