package br.com.ypc.cloudcarteapp.home

import android.graphics.Bitmap
import br.com.ypc.cloudcarteapp.BasePresenter
import br.com.ypc.cloudcarteapp.BaseView
import br.com.ypc.cloudcarteapp.models.valueobjects.Album

/**
 * Created by caleb on 07/10/2017.
 */
interface HomeContract {
    interface Presenter : BasePresenter {
        fun start()
        fun logOut()
        fun loadAlbuns()
        fun saveImage(bitmap: Bitmap)
    }

    interface View : BaseView<Presenter> {
        fun showAlbuns(albuns: List<Album>)
        fun showLoadAlbunsError(error: String)
        fun startLoading()
        fun finishLoading()
        fun showCamera()
        fun showGallery()
        fun showPhotoSaved()
        fun showLoadPhotosError(error: String)
        fun showOptionPhotoDialog()
        fun showSavePhotoError(error: String)
    }
}