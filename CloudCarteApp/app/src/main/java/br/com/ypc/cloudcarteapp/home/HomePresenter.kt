package br.com.ypc.cloudcarteapp.home

import br.com.ypc.cloudcarteapp.auth.interfaces.AuthService

/**
 * Created by caleb on 07/10/2017.
 */
class HomePresenter(val view: HomeContract.View, val authService: AuthService) : HomeContract.Presenter {

    init {
        view.presenter = this;
    }

    override fun start() {
    }

    override fun logout() {
        authService.logout()
    }

    override fun loadAlbuns() {
    }
}