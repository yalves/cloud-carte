package br.com.ypc.cloudcarteapp.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import br.com.ypc.cloudcarteapp.R
import br.com.ypc.cloudcarteapp.extensions.replaceFragmentInActivity
import br.com.ypc.cloudcarteapp.utils.DependencyInjector

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val fragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
        val loginFragment = fragment as LoginContract.View? ?: LoginFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

        DependencyInjector.getLoginPresenter(loginFragment)
    }
}
