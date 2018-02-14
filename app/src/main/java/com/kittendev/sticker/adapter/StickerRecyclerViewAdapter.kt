package com.kittendev.sticker.adapter

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

import com.kittendev.sticker.R
import com.kittendev.sticker.model.StickerPackageModel
import kotlinx.android.synthetic.main.item_sticker.view.*
import kotlinx.android.synthetic.main.view_preview.view.*

class StickerRecyclerViewAdapter(private val context: Context, private val stickerPackageModel: StickerPackageModel) : RecyclerView.Adapter<StickerRecyclerViewAdapter.StickerViewHolder>(), View.OnClickListener, View.OnLongClickListener {

    private var requestManager: RequestManager? = null

    override fun onClick(v: View?) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view: View = LayoutInflater.from(context).inflate(R.layout.view_preview, null, false)
        builder.setTitle("预览")
        builder.setView(view)
        requestManager
                ?.load(stickerPackageModel.getStickerFile(v?.tag as Int))
                ?.transition(DrawableTransitionOptions.withCrossFade())
                ?.apply(RequestOptions.centerCropTransform()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .priority(Priority.HIGH))
                ?.into(view.view_preview_imageView)
        builder.show()
        view.view_preview_wx.setOnClickListener(View.OnClickListener {
            sendWX(v?.tag as Int)
        })
        view.view_preview_qq.setOnClickListener(View.OnClickListener {
            sendQQ(v?.tag as Int)
        })
        view.view_preview_tim.setOnClickListener(View.OnClickListener {
            sendTIM(v?.tag as Int)
        })
    }

    fun sendQT(position: Int) {
        val intent = Intent()
        var uri: Uri?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.flags = Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
            uri = FileProvider.getUriForFile(context, "com.kittendev.sticker.fileprovider", stickerPackageModel.getStickerFile(position))
        } else {
            uri = Uri.fromFile(stickerPackageModel.getStickerFile(position))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        intent.action = "android.intent.action.SEND"
        intent.putExtra("android.intent.extra.STREAM", uri)
        intent.type = "image/*"
        try {
            context.startActivity(intent)
        } catch (exception: Exception) {
        }
    }

    fun sendTIM(position: Int) {
        Toast.makeText(context, "TIM将在后续的稳定版本中支持", Toast.LENGTH_LONG).show()
    }

    fun sendWX(position: Int) {
        val intent = Intent()
        intent.component = ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI")

        var uri: Uri?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.flags = Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
            uri = FileProvider.getUriForFile(context, "com.kittendev.sticker.fileprovider", stickerPackageModel.getStickerFile(position))
        } else {
            uri = Uri.fromFile(stickerPackageModel.getStickerFile(position))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        intent.action = "android.intent.action.SEND"
        intent.putExtra("android.intent.extra.STREAM", uri)
        intent.type = "image/*"
        try {
            context.startActivity(intent)
        } catch (exception: Exception) {
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendQQ(position: Int) {
        val intent = Intent()
        intent.component = ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity")
        var uri: Uri?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.flags = Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
            uri = FileProvider.getUriForFile(context, "com.kittendev.sticker.fileprovider", stickerPackageModel.getStickerFile(position))
        } else {
            uri = Uri.fromFile(stickerPackageModel.getStickerFile(position))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        intent.action = "android.intent.action.SEND"
        intent.putExtra("android.intent.extra.STREAM", uri)
        intent.type = "image/*"
        try {
            context.startActivity(intent)
        } catch (exception: Exception) {
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLongClick(v: View?): Boolean {
        sendQQ(v?.getTag() as Int)
        return true
    }

    init {
        requestManager = Glide.with(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_sticker, parent, false)
        return StickerViewHolder(view)
    }

    override fun onBindViewHolder(holder: StickerViewHolder, position: Int) {
        requestManager
                ?.load(stickerPackageModel.getStickerFile(position))
                ?.transition(DrawableTransitionOptions.withCrossFade())
                ?.apply(RequestOptions.centerCropTransform()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .priority(Priority.HIGH))
                ?.into(holder.imageView)
        holder.itemView.tag = position
        holder.itemView.setOnClickListener(this)
        holder.itemView.setOnLongClickListener(this)
    }

    override fun getItemCount(): Int {
        return stickerPackageModel.number
    }

    inner class StickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.item_sticker_imageView
    }
}
