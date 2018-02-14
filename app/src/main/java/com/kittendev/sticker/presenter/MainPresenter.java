package com.kittendev.sticker.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.kittendev.sticker.StickerApplication;
import com.kittendev.sticker.model.StickerPackageManagerModel;
import com.kittendev.sticker.util.ZipUtil;
import com.kittendev.sticker.view.MainView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainPresenter {

    private MainView mainView;
    private Handler handler;
    private StickerPackageManagerModel stickerPackageManagerModel;

    public MainPresenter(final MainView mainView, Context context) {
        this.mainView = mainView;
        if (new File(StickerApplication.STICKER_PATH).exists()) {
            loadSticker();
        } else {
            new File(StickerApplication.STICKER_PATH).mkdir();
            importAssetsSticker(context);
        }
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    mainView.onStickerLoaded(stickerPackageManagerModel);
                } else if (msg.what == 1) {
                    mainView.onStickerImportCompleted();
                    loadSticker();
                }
            }
        };
    }

    private void importAssetsSticker(final Context context) {
        mainView.onStickerImporting();
        new Thread() {
            @Override
            public void run() {
                try {
                    InputStream inputStream;
                    OutputStream outputStream = new FileOutputStream(StickerApplication.STICKER_PATH + "/Sticker.zip");
                    inputStream = context.getAssets().open("Sticker.zip");
                    byte[] bytes = new byte[1024];
                    int i = inputStream.read(bytes);
                    while (i > 0) {
                        outputStream.write(bytes, 0, i);
                        i = inputStream.read(bytes);
                    }
                    outputStream.flush();
                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ZipUtil.Ectract(StickerApplication.STICKER_PATH + "/Sticker.zip", StickerApplication.STICKER_PATH + "/");
                handler.sendEmptyMessage(1);
            }
        }.start();
    }


    private void loadSticker() {
        mainView.onStickerLoading();
        new Thread() {
            @Override
            public void run() {
                long timeA = System.currentTimeMillis();
                stickerPackageManagerModel = new StickerPackageManagerModel();
                long timeB = System.currentTimeMillis();
                if (timeB - timeA <= 1200) {
                    try {
                        sleep(1200 - (timeB - timeA));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

}
