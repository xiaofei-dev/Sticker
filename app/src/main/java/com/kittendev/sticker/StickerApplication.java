package com.kittendev.sticker;

import android.app.Application;
import android.os.Environment;

public class StickerApplication extends Application {

    public static final String STICKER_PATH = Environment.getExternalStorageDirectory() + "/Sticker";
    public static final String SERVER_FILE = "http://sticker.kittendev.com/Sticker.zip";
    public static Application instance ;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}