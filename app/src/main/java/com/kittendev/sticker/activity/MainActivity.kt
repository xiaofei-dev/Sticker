package com.kittendev.sticker.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import com.kittendev.sticker.R
import com.kittendev.sticker.adapter.StickerViewPagerAdapter
import com.kittendev.sticker.model.StickerPackageManagerModel
import com.kittendev.sticker.presenter.MainPresenter
import com.kittendev.sticker.view.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView, NavigationView.OnNavigationItemSelectedListener {

    private var mainPresenter: MainPresenter? = null
    private var isLoading: Boolean? = null
    private var loadAlertDialog: AlertDialog? = null
    private var isDownloading: Boolean? = null
    private var downloadDialog: AlertDialog? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, main_drawerLayout, main_toolbar, 0, 0)
        setSupportActionBar(main_toolbar)
        main_drawerLayout.setDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        main_navigationView.setNavigationItemSelectedListener(this)
        // 如果Android版本大于6.0，需要申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
            ActivityCompat.requestPermissions(this, permissions, 0)
        } else {
            // 如果Android版本低于6.0
            mainPresenter = MainPresenter(this, this)
        }
    }

    override fun onStickerDownloadReady() {
        val readyAlertDialog = AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("您曾经未安装过旧版本，需要从服务器下载49.31MB的数据包")
                .setCancelable(false)
                .setNegativeButton("取消", DialogInterface.OnClickListener { dialog, which -> })
                .setPositiveButton("下载", DialogInterface.OnClickListener { dialog, which -> mainPresenter?.downloadSticker() })
                .show()
    }

    override fun onStickerDownloading(progress: Int) {
        isDownloading = true
        downloadDialog = AlertDialog.Builder(this)
                .setTitle("下载")
                .setView(LayoutInflater.from(this).inflate(R.layout.view_download, null, false))
                ?.setCancelable(false)
                ?.show()
    }

    override fun onStickerDownloadCompleted() {
        isDownloading = false
        downloadDialog?.dismiss()
    }

    override fun onStickerDownloadFailed() {
        isDownloading = false
        downloadDialog?.dismiss()
        Toast.makeText(this, "Download Failed", Toast.LENGTH_LONG).show()
    }

    // 正在加载表情
    @SuppressLint("InflateParams")
    override fun onStickerLoading() {
        isLoading = true
        loadAlertDialog = AlertDialog.Builder(this)
                .setView(LayoutInflater.from(this).inflate(R.layout.view_load, null, false))
                ?.setCancelable(false)
                ?.show()
    }

    // 表情加载完成
    @SuppressLint("MissingPermission", "HardwareIds")
    override fun onStickerLoadingComplete(stickerPackageManagerModel: StickerPackageManagerModel?) {
        isLoading = false
        loadAlertDialog?.dismiss()
        if (stickerPackageManagerModel?.stickerPackageNumber != 0) {
            main_viewPager.adapter = StickerViewPagerAdapter(this, stickerPackageManagerModel)
        }
        main_tabLayout.setupWithViewPager(main_viewPager)
    }

//    通过拦截Back键防止解压过程被打断
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (KeyEvent.KEYCODE_BACK == keyCode) {
//            return this.isLoading!!
//        }
//        return super.onKeyDown(keyCode, event)
//    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
        // 点击左上角图标打开抽屉
            android.R.id.home -> {
                main_drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_import -> {
                startActivity(Intent(this, ImportActivity::class.java))
            }
            R.id.menu_share -> {
            }
            R.id.menu_about -> {
                startActivity(Intent(this, SettingActivity::class.java))
            }
        }
        main_drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode != 0) {
            return
        }
        grantResults
                .filter { it != PackageManager.PERMISSION_GRANTED }
                .forEach {
                    // 缺少所需权限
                    return
                }
        mainPresenter = MainPresenter(this, this)
    }

}