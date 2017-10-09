package br.com.ypc.cloudcarteapp.estabelecimento

import android.graphics.Bitmap
import br.com.ypc.cloudcarteapp.models.domain.Estabelecimento
import br.com.ypc.cloudcarteapp.services.interfaces.EstabelecimentoService

/**
 * Created by caleb on 08/10/2017.
 */
class EstabelecimentoPresenter(val view: EstabelecimentoContract.View, val estabelecimentoService: EstabelecimentoService) : EstabelecimentoContract.Presenter {

    init {
        view.presenter = this
    }

    override fun salvarEstabelecimentoECardapio(imagemCardapio: Bitmap, estabelecimento: Estabelecimento) {
        view.startLoading()
        estabelecimentoService.salvarEstabelecimentoECardapio(estabelecimento, imagemCardapio,
                successFn = { view.showEstabelecimentoSaveSuccessful(it) },
                errorFn = { view.showSaveEstabelecimentoError(it) },
                finallyFn = { view.finishLoading() })
    }
}