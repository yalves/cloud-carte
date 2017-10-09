package br.com.ypc.cloudcarteapp.utils

import br.com.ypc.cloudcarteapp.auth.firebase.AuthFirebaseService
import br.com.ypc.cloudcarteapp.auth.interfaces.AuthService
import br.com.ypc.cloudcarteapp.cardapio.CardapioContract
import br.com.ypc.cloudcarteapp.cardapio.CardapioPresenter
import br.com.ypc.cloudcarteapp.estabelecimento.EstabelecimentoContract
import br.com.ypc.cloudcarteapp.estabelecimento.EstabelecimentoPresenter
import br.com.ypc.cloudcarteapp.home.HomeContract
import br.com.ypc.cloudcarteapp.home.HomePresenter
import br.com.ypc.cloudcarteapp.login.LoginContract
import br.com.ypc.cloudcarteapp.login.LoginPresenter
import br.com.ypc.cloudcarteapp.register.RegisterContract
import br.com.ypc.cloudcarteapp.register.RegisterPresenter
import br.com.ypc.cloudcarteapp.services.interfaces.AlbumService
import br.com.ypc.cloudcarteapp.services.firebase.AlbumFirebaseService
import br.com.ypc.cloudcarteapp.services.firebase.EstabelecimentoFirebaseService
import br.com.ypc.cloudcarteapp.services.firebase.MapAlbumFirebaseService
import br.com.ypc.cloudcarteapp.services.firebase.UsuarioFirebaseService
import br.com.ypc.cloudcarteapp.services.interfaces.EstabelecimentoService
import br.com.ypc.cloudcarteapp.services.interfaces.UsuarioService

/**
 * Created by caleb on 07/10/2017.
 */
object DependencyInjector {
    val usuarioService: UsuarioService by lazy { UsuarioFirebaseService() }
    val authService: AuthService by lazy { AuthFirebaseService(usuarioService) }
    val mapAlbumFirebaseService: MapAlbumFirebaseService by lazy { MapAlbumFirebaseService() }
    val albumService: AlbumService by lazy { AlbumFirebaseService(authService, mapAlbumFirebaseService) }
    val estabelecimentoService: EstabelecimentoService by lazy { EstabelecimentoFirebaseService(albumService, mapAlbumFirebaseService) }

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
        CardapioPresenter(view, albumService, estabelecimentoService)
    }

    fun getEstabelecimentoPresenter(view: EstabelecimentoContract.View) {
        EstabelecimentoPresenter(view, estabelecimentoService)
    }
}