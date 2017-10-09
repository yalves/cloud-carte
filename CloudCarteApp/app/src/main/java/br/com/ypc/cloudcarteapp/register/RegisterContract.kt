package br.com.ypc.cloudcarteapp.register

import br.com.ypc.cloudcarteapp.BasePresenter
import br.com.ypc.cloudcarteapp.BaseView
import br.com.ypc.cloudcarteapp.models.domain.Usuario

/**
 * Created by caleb on 07/10/2017.
 */
interface RegisterContract {
    interface Presenter : BasePresenter {
        fun createUser(usuario: Usuario, password: String)
    }

    interface View : BaseView<Presenter> {
        fun showEmailRequiredMessage()
        fun showPasswordRequiredMessage()
        fun showNomeRequiredMessage()
        fun showRegisterError(message: String)
        fun showUserRegistered()
        fun showLogin()
    }
}