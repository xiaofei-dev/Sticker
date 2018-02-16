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
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.kittendev.sticker.R
import com.kittendev.sticker.model.StickerPackageModel
import kotlinx.android.synthetic.main.item_sticker.view.*
import kotlinx.android.synthetic.main.view_preview.view.*
import org.jetbrains.anko.defaultSharedPreferences

class StickerRecyclerViewAdapter(private val context: Context, private val stickerPackageModel: StickerPackageModel, private val gridSize: Int) : RecyclerView.Adapter<StickerRecyclerViewAdapter.StickerViewHolder>(), View.OnClickListener, View.OnLongClickListener {

    private var requestManager: RequestManager? = null
    private var priority: Priority? = null

    init {
        var decodeFormat: DecodeFormat? = null
        // 默认使用内存较低的565解码格式
        when (context.defaultSharedPreferences.getInt("decodeFormat", 0)) {
            0 -> decodeFormat = DecodeFormat.PREFER_RGB_565
            1 -> decodeFormat = DecodeFormat.PREFER_ARGB_8888
        }
        // 默认加载正常的图片画质
        when (context.defaultSharedPreferences.getInt("priority", 0)) {
        // 正常画质
            0 -> priority = Priority.NORMAL
        // 低画质
            1 -> priority = Priority.LOW
        // 高画质
            2 -> priority = Priority.HIGH
        // 及时画质
            3 -> priority = Priority.IMMEDIATE
        }
        requestManager = Glide.with(context).setDefaultRequestOptions(RequestOptions().format(decodeFormat!!))
    }

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
                        .priority(priority!!))
                ?.into(view.view_preview_imageView)
        val dialog = builder.show()
        view.view_preview_quit.setOnClickListener {
            dialog.cancel()
        }
        view.view_preview_wx.setOnClickListener {
            sendWX(v?.tag as Int)
            dialog.dismiss()
        }
        view.view_preview_qq.setOnClickListener {
            sendQQ(v?.tag as Int)
            dialog.dismiss()
        }
        view.view_preview_tim.setOnClickListener {
            sendTIM(v?.tag as Int)
            dialog.dismiss()
        }
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
            Toast.makeText(context, "没有可用于发送图片的程序", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendTIM(position: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            val intent = Intent()
            intent.component = ComponentName("com.tencent.tim", "com.tencent.mobileqq.activity.JumpActivity")
            val uri = Uri.fromFile(stickerPackageModel.getStickerFile(position))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.action = "android.intent.action.SEND"
            intent.putExtra("android.intent.extra.STREAM", uri)
            intent.type = "image/*"
            try {
                context.startActivity(intent)
            } catch (exception: Exception) {
                Toast.makeText(context, "未安装TIM", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "对于7.0及以上发送到TIM的功能将在后续的稳定版本中支持", Toast.LENGTH_LONG).show()
        }
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
            Toast.makeText(context, "未安装微信", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(context, "未安装QQ", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLongClick(v: View?): Boolean {
        sendQQ(v?.tag as Int)
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_sticker, parent, false)
        // 为5.0及以上开启水波动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.item_sticker_linearLayout.setBackgroundDrawable(context.resources.getDrawable(R.drawable.anim_water));
        }
        val layoutParams: ViewGroup.LayoutParams = view.layoutParams
        layoutParams.width = gridSize
        layoutParams.height = gridSize
        return StickerViewHolder(view)
    }

    override fun onBindViewHolder(holder: StickerViewHolder, position: Int) {
        requestManager
                ?.load(stickerPackageModel.getStickerFile(position))
                ?.transition(DrawableTransitionOptions.withCrossFade())
                ?.apply(RequestOptions.centerCropTransform()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .priority(priority!!))
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
