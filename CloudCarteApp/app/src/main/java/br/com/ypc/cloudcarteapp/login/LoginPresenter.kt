package br.com.ypc.cloudcarteapp.login

import br.com.ypc.cloudcarteapp.auth.interfaces.AuthService
import com.facebook.AccessToken

/**
 * Created by caleb on 07/10/2017.
 */
class LoginPresenter(val loginView: LoginContract.View, val authService: AuthService) : LoginContract.Presenter {

    init {
        loginView.presenter = this
    }

    override fun authenticateUser(email: String, password: String) {
        if (email.isEmpty()) {
            loginView.showEmailRequiredMessage()
            return
        }

        if (password.isEmpty()) {
            loginView.showPasswordRequiredMessage()
            return
        }

        authService.signInWithEmailAndPassword(email, password,
                successFn = {
                    loginView.showLoginSuccessful()
                },
                errorFn = {
                    loginView.showLoginError(it)
                })
    }

    override fun handleFacebookAccessToken(accessToken: AccessToken) {
        authService.handleFacebookAccess(accessToken,
                successFn = {
                    loginView.showLoginSuccessful()
                },
                errorFn = {
                    loginView.showLoginError(it)
                })
    }

    override fun cancelFacebookLogin() {
        loginView.showFacebookLoginCanceled()
    }
}