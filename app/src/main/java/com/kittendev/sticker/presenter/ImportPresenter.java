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
                } else if (msg.what == 1) {
                    importView.onStickerImportFailed();
                }
            }
        };
    }

    public void importAssetsSticker(final String path) {
        final File file = new File(path);
        if (file.exists()) {
            importView.onStickerImporting();
            new Thread() {
                @Override
                public void run() {
                    ZipUtil.Ectract(file.getAbsolutePath(), StickerApplication.STICKER_PATH + "/");
                    handler.sendEmptyMessage(0);
                }
            }.start();
        } else {
            handler.sendEmptyMessage(1);
        }

    }

}
