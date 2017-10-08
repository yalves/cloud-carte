package br.com.ypc.cloudcarteapp.register

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ypc.cloudcarteapp.R
import br.com.ypc.cloudcarteapp.extensions.inflate
import br.com.ypc.cloudcarteapp.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_register.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * Created by caleb on 07/10/2017.
 */
class RegisterFragment : Fragment(), RegisterContract.View {

    override lateinit var presenter: RegisterContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            container?.inflate(R.layout.fragment_register)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadEvents()
    }

    private fun loadEvents() {
        button_login.setOnClickListener {
            showLogin()
        }
        button_registra.setOnClickListener {
            presenter.createUser(edit_email.text.toString(), edit_password.text.toString())
        }
    }

    override fun showEmailRequiredMessage() {
        toast(R.string.email_required)
    }

    override fun showPasswordRequiredMessage() {
        toast(R.string.password_required)
    }

    override fun showRegisterError(message: String) {
        toast("Error: $message")
    }

    override fun showUserRegistered() {
        toast(R.string.user_registered)
        showLogin()
    }

    override fun showLogin() {
        startActivity<LoginActivity>()
    }

    companion object {
        fun newInstance(): RegisterFragment = RegisterFragment()
    }
}
