package com.kittendev.sticker

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import com.kittendev.sticker.util.MD5Util
import kotlinx.android.synthetic.main.view_surprise.view.*

class Surprise(context: Context, string: String) {

    companion object {

        // 我的id，测试彩蛋用的
        private val TEST = "C9DEFEAED5137377CC3797A40810604D"

        // 我喜欢的那个女孩子
        private val ILOVEYOU1 = "A4C95CAB8C738EE2C51BAA43C8714D1E"
        private val ILOVEYOU2 = "FC57DF8FD39EB02DC319BA6A61989EF6"
        private val ILOVEYOU3 = "F3B172421164DAA19D1A516B0D50C82A"

        // 酷友，生日快乐
        private val HAPPYBIRTHDAY1 = "855007AF394F40619EB3B95EF3FDA64C"
        private val HAPPYBIRTHDAY2 = "86E81A305C5CB897993CD6F1A92C247A"
        private val HAPPYBIRTHDAY3 = "B4E8A4D2C79ED68EEF7303E385F6519B"
    }

    init {
        // 拿到id之后加密两次
        val id = MD5Util.encryptMD5(MD5Util.encryptMD5(string))

        // 由于不能确定7.0以下返回的是哪个id，所以每个都比较一次
        if ((id.equals(ILOVEYOU1)) or (id.equals(ILOVEYOU2)) or (id.equals(ILOVEYOU3))) {
            // 暖昧的话我不太会说了，我只希望你每天都能开开心心的
            // 就留下一个，只有我俩明白的彩蛋吧
            val builder = AlertDialog.Builder(context)
            val view = LayoutInflater.from(context).inflate(R.layout.view_surprise, null, false)
            builder.setView(view)
            view.surprise_imageView.setImageResource(R.mipmap.ic_launcher)
            view.surprise_textView.text = "009，这里是003，收到请回答 (捂脸)"
            builder.setNegativeButton("009已收到") { dialog, which -> }
            builder.setCancelable(false)
            builder.show()
        }

        // 虽然这位酷友我俩不认识，但刚好发布这天是你的生日
        // 别的不多说，生日快乐
        if ((id.equals(HAPPYBIRTHDAY1)) or (id.equals(HAPPYBIRTHDAY2)) or (id.equals(HAPPYBIRTHDAY3))) {
            val builder = AlertDialog.Builder(context)
            val view = LayoutInflater.from(context).inflate(R.layout.view_surprise, null, false)
            builder.setView(view)
            view.surprise_textView.text = "生日快乐"
            builder.setCancelable(false)
            builder.show()
        }

        if ((id.equals(TEST))) {
            val builder = AlertDialog.Builder(context)
            val view = LayoutInflater.from(context).inflate(R.layout.view_surprise, null, false)
            builder.setView(view)
            view.surprise_imageView.setImageResource(R.mipmap.ic_launcher)
            view.surprise_textView.text = "009，这里是003，收到请回答 (捂脸)"
            builder.setNegativeButton("009已收到") { dialog, which -> }
            builder.setCancelable(false)
            builder.show()
        }
    }

}
