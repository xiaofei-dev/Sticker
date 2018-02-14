package com.kittendev.sticker.page

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View

import com.kittendev.sticker.R
import com.kittendev.sticker.adapter.StickerRecyclerViewAdapter
import com.kittendev.sticker.model.StickerPackageModel
import kotlinx.android.synthetic.main.page_sticker.view.*

class StickerPage(private val context: Context, private val stickerPackageModel: StickerPackageModel) {

    val view: View = LayoutInflater.from(context).inflate(R.layout.page_sticker, null, false)

    init {
        val stickerRecyclerViewAdapter = StickerRecyclerViewAdapter(context, stickerPackageModel)
        view.page_sticker_recyclerView.layoutManager = GridLayoutManager(context, 5)
        view.page_sticker_recyclerView.adapter = stickerRecyclerViewAdapter
    }

}
