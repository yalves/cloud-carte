package br.com.ypc.cloudcarteapp.cardapio

import android.graphics.Bitmap
import br.com.ypc.cloudcarteapp.models.domain.Estabelecimento
import br.com.ypc.cloudcarteapp.services.interfaces.AlbumService
import br.com.ypc.cloudcarteapp.services.interfaces.EstabelecimentoService

/**
 * Created by caleb on 08/10/2017.
 */
class CardapioPresenter(val view: CardapioContract.View, val albumService: AlbumService, val estabelecimentoService: EstabelecimentoService) : CardapioContract.Presenter {

    init {
        view.presenter = this
    }

    override fun realizarPesquisaRestaurantes(restaurante: String) {
        view.startLoading()
        estabelecimentoService.getEstabelecimentosByNome(restaurante,
                successFn = { view.showEstabelecimentos(it) },
                errorFn = { view.showLoadEstabelecimentosError(it) },
                finallyFn = { view.finishLoading() })
    }

    override fun salvarCardapio(estabelecimento: Estabelecimento, bitmap: Bitmap) {
        view.startLoading()
        albumService.saveImage(estabelecimento, bitmap,
                successFn = { view.showPhotoSaved() },
                errorFn = { view.showSavePhotoError(it) },
                finallyFn = { view.finishLoading() })
    }
}