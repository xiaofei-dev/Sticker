package com.kittendev.sticker.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;

import com.kittendev.sticker.StickerApplication;
import com.kittendev.sticker.model.StickerPackageManagerModel;
import com.kittendev.sticker.util.ZipUtil;
import com.kittendev.sticker.view.MainView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainPresenter {

    private MainView mainView;
    private Handler handler;
    private StickerPackageManagerModel stickerPackageManagerModel;
    private int progress;

    public MainPresenter(final MainView mainView, Context context) {
        this.mainView = mainView;
        // 如果Sticker文件夹存在
        if (new File(StickerApplication.Companion.getSTICKER_PATH()).exists()) {
            // 加载表情包
            File nomediaFile = new File(StickerApplication.Companion.getSTICKER_PATH() + "/.nomedia");
            // 如果.nomedia文件不存在，创建文件
            if (!nomediaFile.exists()) {
                try {
                    // 创建失败
                    if (!nomediaFile.createNewFile()) {
                        // TODO 文件创建失败
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            loadSticker();
        } else {
            // 如果Sticker文件夹不存在，创建Sticker文件夹
            if (new File(StickerApplication.Companion.getSTICKER_PATH()).mkdir()) {
                File nomediaFile = new File(StickerApplication.Companion.getSTICKER_PATH() + "/.nomedia");
                // 如果.nomedia文件不存在，创建文件
                if (!nomediaFile.exists()) {
                    try {
                        // 创建失败
                        if (!nomediaFile.createNewFile()) {
                            // TODO 文件创建失败
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // 文件夹创建成功，导入表情包
                mainView.onStickerDownloadReady();
            } else {
                // TODO 文件夹创建失败
            }
        }
        // 一个用于接收执行状态的线程
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    // 表情包加载完毕，通知View层
                    mainView.onStickerLoadingComplete(stickerPackageManagerModel);
                } else if (msg.what == 1) {
                    // 表情包下载完毕，通知View层
                    mainView.onStickerDownloadCompleted();
                    // 加载表情包
                    loadSticker();
                } else if (msg.what == 2) {
                    mainView.onStickerDownloadFailed();
                } else if (msg.what == 3) {
                    mainView.onStickerDownloading(progress);
                }
            }
        };
    }

    public void downloadSticker() {
        // 通知View层正在导入表情包
        mainView.onStickerDownloading(0);
        new Thread() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(StickerApplication.Companion.getSERVER_FILE()).build();
                OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        handler.sendEmptyMessage(2);
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.code() == 200) {
                            InputStream inputStream = null;
                            byte[] bytes = new byte[2048];
                            int len;
                            FileOutputStream fileOutputStream = null;
                            try {
                                inputStream = response.body().byteStream();
                                long size = response.body().contentLength();
                                File file = new File(StickerApplication.Companion.getSTICKER_PATH(), "Sticker.zip");
                                fileOutputStream = new FileOutputStream(file);
                                long sum = 0;
                                while ((len = inputStream.read(bytes)) != -1) {
                                    fileOutputStream.write(bytes, 0, len);
                                    sum += len;
                                    progress = (int) (sum * 1.0f / size * 100);
                                }
                                fileOutputStream.flush();
                            } catch (Exception e) {
                                e.printStackTrace();
                                handler.sendEmptyMessage(2);
                            } finally {
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                            }
                            if (new File(StickerApplication.Companion.getSTICKER_PATH() + "/Sticker.zip").exists()) {
                                ZipUtil.Ectract(StickerApplication.Companion.getSTICKER_PATH() + "/Sticker.zip", StickerApplication.Companion.getSTICKER_PATH() + "/");
                                handler.sendEmptyMessage(1);
                            }
                        } else {
                            // handler.sendEmptyMessage(2);
                            handler.sendEmptyMessage(2);
                        }
                    }
                });
            }
        }.start();
    }

    private void loadSticker() {
        mainView.onStickerLoading();
        new Thread() {
            @Override
            public void run() {
                // 保证Dialog停留时间不低于1200毫秒，防止闪屏
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
