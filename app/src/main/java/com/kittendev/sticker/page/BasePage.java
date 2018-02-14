package com.kittendev.sticker.page;

import android.view.View;

import com.kittendev.sticker.model.StickerPackageModel;

abstract class BasePage {

    protected View view;
    protected StickerPackageModel stickerPackageModel;

    BasePage(StickerPackageModel stickerPackageModel, View view) {
        this.stickerPackageModel = stickerPackageModel;
        this.view = view;
        onInitialize(view);
    }

    public View getView() {
        return view;
    }

    protected abstract void onInitialize(View view);

}
