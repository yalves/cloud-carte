package br.com.ypc.cloudcarteapp.home

import br.com.ypc.cloudcarteapp.BasePresenter
import br.com.ypc.cloudcarteapp.BaseView
import br.com.ypc.cloudcarteapp.models.valueobjects.Album

/**
 * Created by caleb on 07/10/2017.
 */
interface HomeContract {
    interface Presenter : BasePresenter {
        fun start()
        fun logout()
        fun loadAlbuns()
    }

    interface View : BaseView<Presenter> {
        fun showAlbuns(albuns: List<Album>)
        fun showLoadAlbunsError(error: String)
        fun startLoading()
        fun finishLoading()
    }
}