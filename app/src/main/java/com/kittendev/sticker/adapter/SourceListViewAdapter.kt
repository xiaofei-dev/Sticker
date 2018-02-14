package com.kittendev.sticker.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import com.kittendev.sticker.R
import kotlinx.android.synthetic.main.item_source.view.*
import java.util.*

class SourceListViewAdapter(private val context: Context) : BaseAdapter() {

    private val title = arrayOf("Glide")
    private val subTitle = arrayOf("Glide is a fast and efficient open source media management and image loading framework for Android that wraps media decoding,memory and disk caching, and resource pooling into a simple and easy to use interface.")

    override fun getCount(): Int {
        return title.size
    }

    override fun getItem(position: Int): Any? {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position as Long
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View, parent: ViewGroup): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.item_source, parent, false)
        view.item_source_title.text = title[position]
        view.item_source_subtitle.text = subTitle[position]
        return null
    }
}
