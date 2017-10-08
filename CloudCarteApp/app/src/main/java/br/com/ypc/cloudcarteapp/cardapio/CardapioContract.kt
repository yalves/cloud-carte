package br.com.ypc.cloudcarteapp.cardapio

import android.graphics.Bitmap
import br.com.ypc.cloudcarteapp.BasePresenter
import br.com.ypc.cloudcarteapp.BaseView
import br.com.ypc.cloudcarteapp.models.domain.Estabelecimento

/**
 * Created by caleb on 08/10/2017.
 */
interface CardapioContract {
    interface Presenter : BasePresenter {
        fun realizarPesquisaRestaurantes(restaurante: String)
        fun salvarCardapio(estabelecimento: Estabelecimento, bitmap: Bitmap)
    }

    interface View : BaseView<Presenter> {

        fun showCamera()
        fun showGallery()
        fun startLoading()
        fun finishLoading()
        fun showPhotoSaved()
        fun showSavePhotoError(error: String)
        fun showEstabelecimentos(estabelecimentos: List<Estabelecimento>)
        fun showLoadEstabelecimentosError(error: String)
    }
}