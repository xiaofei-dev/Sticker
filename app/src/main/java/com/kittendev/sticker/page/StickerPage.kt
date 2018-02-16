package com.kittendev.sticker.page

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View

import com.kittendev.sticker.R
import com.kittendev.sticker.adapter.StickerRecyclerViewAdapter
import com.kittendev.sticker.model.StickerPackageModel
import kotlinx.android.synthetic.main.page_sticker.view.*

class StickerPage(private val context: Context, private val stickerPackageModel: StickerPackageModel) {

    val view: View = LayoutInflater.from(context).inflate(R.layout.page_sticker, null, false)

    init {
        val gridNumAndWidth = calculateGridNumAndWidth()
        val stickerRecyclerViewAdapter = StickerRecyclerViewAdapter(context, stickerPackageModel, gridNumAndWidth[1])
        view.page_sticker_recyclerView.layoutManager = GridLayoutManager(context, gridNumAndWidth[0])
        view.page_sticker_recyclerView.adapter = stickerRecyclerViewAdapter
    }

    private fun calculateGridNumAndWidth(): IntArray {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val totalWidth = displayMetrics.widthPixels
        val minGridSize = context.resources.getDimensionPixelSize(R.dimen.sticker_item_size)
        val num = totalWidth / minGridSize
        return intArrayOf(num, totalWidth / num)
    }

}
