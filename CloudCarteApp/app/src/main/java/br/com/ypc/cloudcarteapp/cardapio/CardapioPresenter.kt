package br.com.ypc.cloudcarteapp.cardapio

import android.graphics.Bitmap
import br.com.ypc.cloudcarteapp.services.interfaces.AlbumService

/**
 * Created by caleb on 08/10/2017.
 */
class CardapioPresenter(val view: CardapioContract.View, val albumService: AlbumService) : CardapioContract.Presenter {

    init {
        view.presenter = this
    }

    override fun saveImage(bitmap: Bitmap) {
        view.startLoading()
        albumService.saveImage(bitmap,
                successFn = { view.showPhotoSaved() },
                errorFn = { view.showSavePhotoError(it) },
                finallyFn = { view.finishLoading() })
    }
}