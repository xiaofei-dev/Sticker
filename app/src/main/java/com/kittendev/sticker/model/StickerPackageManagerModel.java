package com.kittendev.sticker.model;

import com.kittendev.sticker.StickerApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StickerPackageManagerModel extends File {

    private List<StickerPackageModel> stickerPackageModelList = new ArrayList<>();

    public StickerPackageManagerModel() {
        super(StickerApplication.Companion.getSTICKER_PATH());
        File[] files = listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                stickerPackageModelList.add(new StickerPackageModel(file));
            }
        }
    }

    public StickerPackageModel getStickerPackageModel(int position) {
        return stickerPackageModelList.get(position);
    }

    public String getStickerPackageName(int position) {
        return stickerPackageModelList.get(position).getName();
    }

    public int getStickerPackageNumber() {
        return stickerPackageModelList.size();
    }

}
