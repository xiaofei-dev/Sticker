package com.kittendev.sticker.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import com.kittendev.sticker.R
import com.kittendev.sticker.Surprise
import com.kittendev.sticker.adapter.StickerViewPagerAdapter
import com.kittendev.sticker.model.StickerPackageManagerModel
import com.kittendev.sticker.presenter.MainPresenter
import com.kittendev.sticker.view.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView, NavigationView.OnNavigationItemSelectedListener {

    private var mainPresenter: MainPresenter? = null
    private var isLoading: Boolean? = null
    private var isImporting: Boolean? = null
    private var loadAlertDialog: AlertDialog? = null
    private var importAlertDialog: AlertDialog? = null

    fun score() {
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, main_drawerLayout, main_toolbar, 0, 0)
        setSupportActionBar(main_toolbar)

        /*val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }*/
        main_drawerLayout.setDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        main_navigationView.setNavigationItemSelectedListener(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_PHONE_STATE),
                            321)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mainPresenter = MainPresenter(this, this)
        } else {
            Toast.makeText(this, "请先为app打开所需要的权限", Toast.LENGTH_LONG).show()
            finish()
        }
    }


    override fun onStickerImportCompleted() {
        isImporting = false
        importAlertDialog?.dismiss()
    }

    override fun onStickerImporting() {
        isImporting = true
        importAlertDialog = AlertDialog.Builder(this)
                .setView(LayoutInflater.from(this).inflate(R.layout.view_import, null, false))
                ?.setCancelable(false)
                ?.show()
    }

    override fun onStickerLoading() {
        isLoading = true
        loadAlertDialog = AlertDialog.Builder(this)
                .setView(LayoutInflater.from(this).inflate(R.layout.view_load, null, false))
                ?.setCancelable(false)
                ?.show()
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    override fun onStickerLoadingComplete(stickerPackageManagerModel: StickerPackageManagerModel?) {
        isLoading = false
        loadAlertDialog?.dismiss()
        if (stickerPackageManagerModel?.stickerPackageNumber != 0) {
            main_viewPager.adapter = StickerViewPagerAdapter(this, stickerPackageManagerModel)
        }
        main_tabLayout.setupWithViewPager(main_viewPager)
        val sharedPreferences: SharedPreferences = getSharedPreferences("setting", Context.MODE_PRIVATE)
        // 是否是第一次运行
        if (sharedPreferences.getBoolean("firstRun", true)) {
            // 彩蛋入口
            // 因为她是6.0系统诶，那位过生日酷友是5.0，直接走这条逻辑
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Surprise(this, (getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).getDeviceId(0))
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // 这是我用来测试彩蛋的
                Surprise(this, (getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).meid)
            }
            val editor = sharedPreferences.edit()
            editor.putBoolean("firstRun", false)
            editor.apply()
        }
    }

    //你覆写这个方法是想干嘛？
    /*override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            return this.isLoading!!
        }
        return super.onKeyDown(keyCode, event)
    }*/

    //点击左上角图标打开抽屉
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            android.R.id.home ->{
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

}