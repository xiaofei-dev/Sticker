package com.kittendev.sticker.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.kittendev.sticker.R;

public class WelcomePage {

    private View view;

    public WelcomePage(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.page_welcome, null, false);
    }

    public View getView() {
        return view;
    }
}
