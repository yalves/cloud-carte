package br.com.ypc.cloudcarteapp.cardapio

import android.graphics.Bitmap
import br.com.ypc.cloudcarteapp.BasePresenter
import br.com.ypc.cloudcarteapp.BaseView

/**
 * Created by caleb on 08/10/2017.
 */
interface CardapioContract {
    interface Presenter : BasePresenter {

        fun saveImage(bitmap: Bitmap)
    }

    interface View : BaseView<Presenter> {

        fun showCamera()
        fun showGallery()
        fun startLoading()
        fun finishLoading()
        fun showPhotoSaved()
        fun showSavePhotoError(error: String)
    }
}