package com.kittendev.sticker

import android.app.Application
import android.os.Environment

class StickerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        val STICKER_PATH = Environment.getExternalStorageDirectory().toString() + "/Sticker"
        val SERVER_FILE = "http://sticker.kittendev.com/Sticker.zip"
        lateinit var instance: StickerApplication
    }

}