package br.com.ypc.cloudcarteapp.cardapio

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.ypc.cloudcarteapp.R
import br.com.ypc.cloudcarteapp.extensions.replaceFragmentInActivity
import br.com.ypc.cloudcarteapp.utils.DependencyInjector

class CardapioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardapio)

        val fragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
        val view = fragment as CardapioContract.View? ?: CardapioFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

        DependencyInjector.getCardapioPresenter(view)
    }

    companion object {
        val IMAGEM_CARDAPIO: String = "IMAGEM_CARDAPIO"
        val OPTION_PHOTO: String = "OPTION_PHOTO"
    }
}
