package br.com.ypc.cloudcarteapp.estabelecimento

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.ypc.cloudcarteapp.R
import br.com.ypc.cloudcarteapp.extensions.replaceFragmentInActivity
import br.com.ypc.cloudcarteapp.utils.DependencyInjector

class EstabelecimentoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estabelecimento)

        val fragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
        val view = fragment as EstabelecimentoContract.View? ?: EstabelecimentoFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

        DependencyInjector.getEstabelecimentoPresenter(view)
    }
}
