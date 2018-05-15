package br.com.ypc.cloudcarteapp.utils

import android.support.v4.app.Fragment
import android.app.ProgressDialog
import org.jetbrains.anko.support.v4.indeterminateProgressDialog

object DialogProgressUtils {
    private var dialog: ProgressDialog? = null
    fun show(fragment: Fragment) {
        dialog = dialog ?: fragment.indeterminateProgressDialog(message = "Por favor aguarde um momento…", title = "Carregando")
        dialog?.show()
    }

    fun hide() {
        dialog?.dismiss()
    }
}