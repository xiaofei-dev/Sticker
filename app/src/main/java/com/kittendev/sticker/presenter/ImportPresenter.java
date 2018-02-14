package com.kittendev.sticker.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.kittendev.sticker.StickerApplication;
import com.kittendev.sticker.util.ZipUtil;
import com.kittendev.sticker.view.ImportView;

import java.io.File;

public class ImportPresenter {

    private ImportView importView;
    private Handler handler;

    public ImportPresenter(final ImportView importView) {
        this.importView = importView;
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    importView.onStickerImportCompleted();
                }
            }
        };
    }

    public void importAssetsSticker(final String file) {
        importView.onStickerImporting();
        new Thread() {
            @Override
            public void run() {
                if (new File(file).exists()) {
                    ZipUtil.Ectract(file, StickerApplication.STICKER_PATH + "/");
                }
                handler.sendEmptyMessage(1);
            }
        }.start();
    }

}
