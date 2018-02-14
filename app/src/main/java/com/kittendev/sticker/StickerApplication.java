package com.kittendev.sticker;

import android.app.Application;
import android.os.Environment;

public class StickerApplication extends Application {

    public static final String STICKER_PATH = Environment.getExternalStorageDirectory() + "/Sticker";

    @Override
    public void onCreate() {
        super.onCreate();
    }

}