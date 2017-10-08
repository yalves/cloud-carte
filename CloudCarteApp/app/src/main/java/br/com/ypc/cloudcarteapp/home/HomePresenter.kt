package br.com.ypc.cloudcarteapp.home

import android.graphics.Bitmap
import br.com.ypc.cloudcarteapp.auth.interfaces.AuthService
import br.com.ypc.cloudcarteapp.services.interfaces.AlbumService

/**
 * Created by caleb on 07/10/2017.
 */
class HomePresenter(val view: HomeContract.View, val authService: AuthService, val albumService: AlbumService) : HomeContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
        loadAlbuns()
    }

    override fun logOut() {
        authService.logOut()
    }

    override fun loadAlbuns() {
        view.startLoading()
        albumService.getImageUploadInfoList(AlbumService.SHOW_PUBLIC_PHOTOS_TRUE,
                successFn = { view.showAlbuns(it) },
                errorFn = { view.showLoadPhotosError(it) },
                finallyFn = { view.finishLoading() })
    }

    override fun saveImage(bitmap: Bitmap) {
        view.startLoading()
        albumService.saveImage(bitmap,
                successFn = { view.showPhotoSaved() },
                errorFn = { view.showSavePhotoError(it) },
                finallyFn = { view.finishLoading() })
    }
}