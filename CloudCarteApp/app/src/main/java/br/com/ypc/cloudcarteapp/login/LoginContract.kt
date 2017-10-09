package br.com.ypc.cloudcarteapp.login

import br.com.ypc.cloudcarteapp.BasePresenter
import br.com.ypc.cloudcarteapp.BaseView
import com.facebook.AccessToken

/**
 * Created by caleb on 07/10/2017.
 */
interface LoginContract {
    interface Presenter: BasePresenter {
        fun authenticateUser(email: String, password: String)
        fun cancelFacebookLogin()
        fun handleFacebookAccessToken(accessToken: AccessToken)
    }

    interface View : BaseView<Presenter> {
        fun showEmailRequiredMessage()
        fun showPasswordRequiredMessage()
        fun showLoginSuccessful()
        fun showLoginError(error: String)
        fun showErrorLoginFacebook(error: String)
        fun showFacebookLoginCanceled()
    }
}