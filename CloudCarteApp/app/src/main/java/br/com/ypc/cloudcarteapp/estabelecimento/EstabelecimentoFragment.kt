package br.com.ypc.cloudcarteapp.estabelecimento

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ypc.cloudcarteapp.R
import br.com.ypc.cloudcarteapp.cardapio.CardapioActivity
import br.com.ypc.cloudcarteapp.extensions.inflate
import br.com.ypc.cloudcarteapp.home.HomeActivity
import br.com.ypc.cloudcarteapp.models.domain.Estabelecimento
import br.com.ypc.cloudcarteapp.utils.ShareBitmap
import kotlinx.android.synthetic.main.fragment_estabelecimento.*
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


/**
 * Created by caleb on 08/10/2017.
 */
class EstabelecimentoFragment : Fragment(), EstabelecimentoContract.View {
    private var dialog: ProgressDialog? = null

    override lateinit var presenter: EstabelecimentoContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            container?.inflate(R.layout.fragment_estabelecimento)

    private var imagemCardapio: Bitmap? = null

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val byteArray = activity.intent.extras[CardapioActivity.IMAGEM_CARDAPIO] as ByteArray
//        imagemCardapio = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        imagemCardapio = ShareBitmap.bitmap

        loadEvents()
    }

    override fun showEstabelecimentoSaveSuccessful(estabelecimento: Estabelecimento) {
        toast("Cardápio salvo!")
        startActivity<HomeActivity>()
        activity.finish()
    }

    override fun startLoading() {
        dialog = dialog ?: indeterminateProgressDialog(message = "Please wait a bit…", title = "Loading")
        dialog?.show()
    }

    override fun finishLoading() {
        dialog?.dismiss()
    }

    override fun showSaveEstabelecimentoError(error: String) {
        toast("Error: $error")
    }

    private fun loadEvents() {
        button_salva.setOnClickListener {
            imagemCardapio?.let {
                val estabelecimento = getEstabelecimento()
                presenter.salvarEstabelecimentoECardapio(it, estabelecimento)
            }
        }
    }

    private fun getEstabelecimento(): Estabelecimento =
            Estabelecimento(id = "",
            nome = edit_nome.text.toString(),
            descricao = edit_descricao.text.toString())

    companion object {
        fun newInstance() = EstabelecimentoFragment()
    }
}