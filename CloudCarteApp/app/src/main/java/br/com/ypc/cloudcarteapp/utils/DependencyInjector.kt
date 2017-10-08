package br.com.ypc.cloudcarteapp.utils

import br.com.ypc.cloudcarteapp.auth.firebase.AuthFirebaseService
import br.com.ypc.cloudcarteapp.auth.interfaces.AuthService
import br.com.ypc.cloudcarteapp.cardapio.CardapioContract
import br.com.ypc.cloudcarteapp.cardapio.CardapioPresenter
import br.com.ypc.cloudcarteapp.home.HomeContract
import br.com.ypc.cloudcarteapp.home.HomePresenter
import br.com.ypc.cloudcarteapp.login.LoginContract
import br.com.ypc.cloudcarteapp.login.LoginPresenter
import br.com.ypc.cloudcarteapp.register.RegisterContract
import br.com.ypc.cloudcarteapp.register.RegisterPresenter
import br.com.ypc.cloudcarteapp.services.interfaces.AlbumService
import br.com.ypc.cloudcarteapp.services.firebase.AlbumFirebaseService
import br.com.ypc.cloudcarteapp.services.firebase.MapAlbumFirebaseService

/**
 * Created by caleb on 07/10/2017.
 */
object DependencyInjector {
    val authService: AuthService by lazy { AuthFirebaseService() }
    val mapAlbumFirebaseService: MapAlbumFirebaseService by lazy { MapAlbumFirebaseService() }
    val albumService: AlbumService by lazy { AlbumFirebaseService(authService, mapAlbumFirebaseService) }

    fun getHomePresenter(view: HomeContract.View) {
        HomePresenter(view, authService, albumService)
    }

    fun getLoginPresenter(view: LoginContract.View) {
        LoginPresenter(view, authService)
    }

    fun getRegisterPresenter(view: RegisterContract.View) {
        RegisterPresenter(view, authService)
    }

    fun getCardapioPresenter(view: CardapioContract.View) {
        CardapioPresenter(view, albumService)
    }
}