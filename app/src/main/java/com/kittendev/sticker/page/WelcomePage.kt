package com.kittendev.sticker.page

import android.content.Context
import android.view.LayoutInflater
import android.view.View

import com.kittendev.sticker.R

class WelcomePage(context: Context) {

    val view: View

    init {
        view = LayoutInflater.from(context).inflate(R.layout.page_welcome, null, false)
    }
}
