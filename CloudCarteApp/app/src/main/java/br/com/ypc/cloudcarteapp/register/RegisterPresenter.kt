package br.com.ypc.cloudcarteapp.register

import br.com.ypc.cloudcarteapp.auth.interfaces.AuthService

/**
 * Created by caleb on 07/10/2017.
 */
class RegisterPresenter (val registerView: RegisterContract.View, val authService: AuthService) : RegisterContract.Presenter {

    init {
        registerView.presenter = this
    }

    override fun createUser(email: String, password: String) {
        if (email.isEmpty()) {
            registerView.showEmailRequiredMessage()
            return
        }

        if (password.isEmpty()) {
            registerView.showPasswordRequiredMessage()
            return
        }

        authService.createUserWithEmailAndPassword(email, password,
                successFn = {
                    registerView.showUserRegistered()
                },
                errorFn = {
                    registerView.showRegisterError(it)
                })
    }
}