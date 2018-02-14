package com.kittendev.sticker.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.kittendev.sticker.R;

public class SettingPage {

    private View view;

    public SettingPage(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.page_setting, null, false);
    }

    public View getView() {
        return view;
    }

}
