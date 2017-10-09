package br.com.ypc.cloudcarteapp.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.ypc.cloudcarteapp.R
import br.com.ypc.cloudcarteapp.extensions.replaceFragmentInActivity
import br.com.ypc.cloudcarteapp.utils.DependencyInjector

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val fragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
        val loginFragment = fragment as RegisterContract.View? ?: RegisterFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

        DependencyInjector.getRegisterPresenter(loginFragment)
    }
}
