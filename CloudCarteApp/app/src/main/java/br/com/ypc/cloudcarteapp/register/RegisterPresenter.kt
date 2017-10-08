package br.com.ypc.cloudcarteapp.register

import br.com.ypc.cloudcarteapp.auth.interfaces.AuthService
import br.com.ypc.cloudcarteapp.models.domain.Usuario

/**
 * Created by caleb on 07/10/2017.
 */
class RegisterPresenter (val registerView: RegisterContract.View, val authService: AuthService) : RegisterContract.Presenter {

    init {
        registerView.presenter = this
    }

    override fun createUser(usuario: Usuario, password: String) {
        if (usuario.email.isEmpty()) {
            registerView.showEmailRequiredMessage()
            return
        }

        if (password.isEmpty()) {
            registerView.showPasswordRequiredMessage()
            return
        }

        if (usuario.nome.isEmpty()) {
            registerView.showNomeRequiredMessage()
            return
        }

        authService.createUser(usuario, password,
                successFn = {
                    registerView.showUserRegistered()
                },
                errorFn = {
                    registerView.showRegisterError(it)
                })
    }
}