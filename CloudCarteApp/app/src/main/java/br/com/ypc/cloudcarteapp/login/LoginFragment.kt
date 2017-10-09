package br.com.ypc.cloudcarteapp.login

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ypc.cloudcarteapp.R
import br.com.ypc.cloudcarteapp.extensions.inflate
import br.com.ypc.cloudcarteapp.home.HomeActivity
import br.com.ypc.cloudcarteapp.register.RegisterActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * Created by caleb on 07/10/2017.
 */
class LoginFragment : Fragment(), LoginContract.View {
    override lateinit var presenter: LoginContract.Presenter
    private lateinit var mCallbackManagerFacebook: CallbackManager

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mCallbackManagerFacebook = CallbackManager.Factory.create()
        return container?.inflate(R.layout.fragment_login)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadEvents()
        initFacebookLoginButton()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbackManagerFacebook.onActivityResult(requestCode, resultCode, data)
    }

    override fun showEmailRequiredMessage() {
        toast(R.string.email_required)
    }

    override fun showPasswordRequiredMessage() {
        toast(R.string.password_required)
    }

    override fun showLoginSuccessful() {
        startActivity<HomeActivity>()
    }

    override fun showLoginError(error: String) {
        toast(error)
    }

    override fun showErrorLoginFacebook(error: String) {
        toast(error)
    }

    override fun showFacebookLoginCanceled() {
        toast(R.string.facebook_login_canceled)
    }

    private fun initFacebookLoginButton() {
        login_button_facebook.setReadPermissions("email", "public_profile")
        login_button_facebook.fragment = this
        login_button_facebook.registerCallback(mCallbackManagerFacebook, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                presenter.handleFacebookAccessToken(result.accessToken)
            }

            override fun onCancel() {
                presenter.cancelFacebookLogin()
            }

            override fun onError(error: FacebookException?) {
                showErrorLoginFacebook(error.toString())
            }
        })
    }

    private fun loadEvents() {
        button_login.setOnClickListener {
            presenter.authenticateUser(edit_email.text.toString(), edit_password.text.toString())
        }
        button_register.setOnClickListener {
            showRegister()
        }
    }

    private fun showRegister() {
        startActivity<RegisterActivity>()
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}