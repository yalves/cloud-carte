package br.com.ypc.cloudcarteapp.estabelecimento

import android.graphics.Bitmap
import br.com.ypc.cloudcarteapp.BasePresenter
import br.com.ypc.cloudcarteapp.BaseView
import br.com.ypc.cloudcarteapp.models.domain.Estabelecimento

/**
 * Created by caleb on 08/10/2017.
 */
interface EstabelecimentoContract {
    interface Presenter : BasePresenter {
        fun salvarEstabelecimentoECardapio(imagemCardapio: Bitmap, estabelecimento: Estabelecimento)
    }

    interface View : BaseView<Presenter> {
        fun showEstabelecimentoSaveSuccessful(estabelecimento: Estabelecimento)
        fun showSaveEstabelecimentoError(error: String)
        fun startLoading()
        fun finishLoading()
    }
}