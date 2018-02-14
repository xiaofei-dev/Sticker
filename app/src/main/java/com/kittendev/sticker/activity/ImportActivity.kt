package com.kittendev.sticker.activity

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.kittendev.sticker.R
import com.kittendev.sticker.presenter.ImportPresenter
import com.kittendev.sticker.view.ImportView
import kotlinx.android.synthetic.main.activity_import.*

class ImportActivity : AppCompatActivity(), ImportView, View.OnClickListener {

    private var importPresenter: ImportPresenter? = null
    private var isImporting: Boolean? = null
    private var importAlertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import)
        setSupportActionBar(import_toolbar)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        importPresenter = ImportPresenter(this)
        import_button.setOnClickListener(this)
    }

    override fun onStickerImporting() {
        isImporting = true
        importAlertDialog = AlertDialog.Builder(this)
                .setView(LayoutInflater.from(this).inflate(R.layout.view_import, null, false))
                ?.setCancelable(false)
                ?.show()
    }

    override fun onStickerImportCompleted() {
        isImporting = false
        importAlertDialog?.dismiss()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.import_button) {
            importPresenter?.importAssetsSticker(import_editText?.text.toString())
        }
    }
}
