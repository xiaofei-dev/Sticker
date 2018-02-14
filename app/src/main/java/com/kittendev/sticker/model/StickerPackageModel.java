package com.kittendev.sticker.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StickerPackageModel extends File {

    private List<File> stickerList = new ArrayList<>();

    StickerPackageModel(File files) {
        super(files.getAbsolutePath());
        for (File file : files.listFiles()) {
            if (file.isFile()) {
                stickerList.add(file);
            }
        }
    }

    public File getStickerFile(int position) {
        return stickerList.get(position);
    }

    public int getNumber() {
        return stickerList.size();
    }

}
