package com.kittendev.sticker.view

import com.kittendev.sticker.model.StickerPackageManagerModel

interface MainView {

    fun onStickerDownloadReady()

    fun onStickerDownloading(progress: Int)

    fun onStickerDownloadCompleted()

    fun onStickerDownloadFailed()

    fun onStickerLoading()

    fun onStickerLoadingComplete(stickerPackageManagerModel: StickerPackageManagerModel?)

}
