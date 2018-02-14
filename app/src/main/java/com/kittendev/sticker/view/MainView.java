package com.kittendev.sticker.view;

import com.kittendev.sticker.model.StickerPackageManagerModel;

public interface MainView {

    void onStickerImporting();

    void onStickerImportCompleted();

    void onStickerLoading();

    void onStickerLoadingComplete(StickerPackageManagerModel stickerPackageManagerModel);

}
