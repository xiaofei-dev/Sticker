package com.kittendev.sticker.view;

import com.kittendev.sticker.model.StickerPackageManagerModel;

public interface MainView {

    void onStickerDownloadReady();

    void onStickerDownloading(int progress);

    void onStickerDownloadCompleted();

    void onStickerDownloadFailed();

    void onStickerLoading();

    void onStickerLoadingComplete(StickerPackageManagerModel stickerPackageManagerModel);

}
