package br.com.ypc.cloudcarteapp.utils

import br.com.ypc.cloudcarteapp.auth.firebase.AuthFirebaseService
import br.com.ypc.cloudcarteapp.auth.interfaces.AuthService
import br.com.ypc.cloudcarteapp.home.HomeContract
import br.com.ypc.cloudcarteapp.home.HomePresenter
import br.com.ypc.cloudcarteapp.login.LoginContract
import br.com.ypc.cloudcarteapp.login.LoginPresenter
import br.com.ypc.cloudcarteapp.register.RegisterContract
import br.com.ypc.cloudcarteapp.register.RegisterPresenter

/**
 * Created by caleb on 07/10/2017.
 */
object DependencyInjector {
    val authService: AuthService by lazy { AuthFirebaseService() }

    fun getHomePresenter(view: HomeContract.View) {
        HomePresenter(view, authService)
    }

    fun getLoginPresenter(view: LoginContract.View) {
        LoginPresenter(view, authService)
    }

    fun getRegisterPresenter(view: RegisterContract.View) {
        RegisterPresenter(view, authService)
    }
}