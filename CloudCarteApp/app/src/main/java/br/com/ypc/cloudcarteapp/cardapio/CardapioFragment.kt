package br.com.ypc.cloudcarteapp.cardapio

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ypc.cloudcarteapp.R
import br.com.ypc.cloudcarteapp.cardapio.adapter.EstabelecimentoClickListener
import br.com.ypc.cloudcarteapp.cardapio.adapter.RestauranteRecyclerViewAdapter
import br.com.ypc.cloudcarteapp.estabelecimento.EstabelecimentoActivity
import br.com.ypc.cloudcarteapp.extensions.inflate
import br.com.ypc.cloudcarteapp.home.HomeFragment
import br.com.ypc.cloudcarteapp.models.domain.Estabelecimento
import kotlinx.android.synthetic.main.fragment_cardapio.*
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.selector
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import br.com.ypc.cloudcarteapp.R.mipmap.ic_launcher
import android.graphics.BitmapFactory
import br.com.ypc.cloudcarteapp.utils.ShareBitmap
import java.io.ByteArrayOutputStream


/**
 * Created by caleb on 08/10/2017.
 */
class CardapioFragment : Fragment(), CardapioContract.View {

    override lateinit var presenter: CardapioContract.Presenter

    private lateinit var adapter: RestauranteRecyclerViewAdapter

    private var dialog: ProgressDialog? = null
    private var cardapioImage: Bitmap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            container?.inflate(R.layout.fragment_cardapio)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val option = activity.intent.extras[CardapioActivity.OPTION_PHOTO]
        when (option) {
            HomeFragment.CAMERA_VALUE_OPTION -> showCamera()
            HomeFragment.GALLERY_VALUE_OPTION -> showGallery()
        }

        loadEvents()
        loadAdapter()
        loadRecyclerView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_CANCELED) {
            activity.finish()
            return
        }

        cardapioImage = getBitmap(requestCode, data)
    }

    override fun showCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        this.startActivityForResult(intent, HomeFragment.CAMERA_VALUE_OPTION)
    }

    override fun showGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        this.startActivityForResult(intent, HomeFragment.GALLERY_VALUE_OPTION)
    }

    override fun showEstabelecimentos(estabelecimentos: List<Estabelecimento>) {
        adapter.estabelecimentos = estabelecimentos

        if (estabelecimentos.isEmpty()) {
            showOptionToAddNewEstabelecimento()
        }
    }

    private fun showOptionToAddNewEstabelecimento() {
        val options = listOf("Sim", "Não")
        selector("Restaurante não encontrado.\nDeseja cadastrar um novo restaurante?", options, { _, i ->
            when(i) {
                0 -> showAddEstabelecimento()
            }
        })
    }

    private fun showAddEstabelecimento() {
        cardapioImage?.let {
//            val stream = ByteArrayOutputStream()
//            it.compress(Bitmap.CompressFormat.PNG, 100, stream)
//            val byteArray = stream.toByteArray()
            ShareBitmap.bitmap = it
            startActivity<EstabelecimentoActivity>()
        }
    }

    override fun showLoadEstabelecimentosError(error: String) {
        if (activity != null)
            toast("Error: $error")
    }

    override fun startLoading() {
        dialog = dialog ?: indeterminateProgressDialog(message = "Please wait a bit…", title = "Loading")
        dialog?.show()
    }

    override fun finishLoading() {
        dialog?.dismiss()
    }

    override fun showPhotoSaved() {
        toast(R.string.photo_saved_successful)
    }

    override fun showSavePhotoError(error: String) {
        toast("Error: $error")
    }

    private fun getBitmap(requestCode: Int, data: Intent?): Bitmap? = when (requestCode) {
        HomeFragment.GALLERY_VALUE_OPTION -> {
            data?.let {
                MediaStore.Images.Media.getBitmap(activity.contentResolver, it.data)
            }
        }
        HomeFragment.CAMERA_VALUE_OPTION -> {
            data?.let {
                it.extras["data"] as Bitmap
            }
        }
        else -> null
    }

    private fun loadEvents() {
        button_pesquisa.setOnClickListener {
            presenter.realizarPesquisaRestaurantes(edit_restaurante.text.toString())
        }
    }

    private fun loadAdapter() {
        adapter = RestauranteRecyclerViewAdapter(activity, listOf(), EstabelecimentoCardapioClickListener())
    }

    private fun loadRecyclerView() {
        listview_restaurantes.setHasFixedSize(true)
        listview_restaurantes.layoutManager = LinearLayoutManager(activity)
        listview_restaurantes.adapter = adapter
    }

    companion object {
        fun newInstance() = CardapioFragment()
    }

    inner class EstabelecimentoCardapioClickListener : EstabelecimentoClickListener {
        override fun onItemClick(item: Estabelecimento) {
            cardapioImage?.let {
                presenter.salvarCardapio(item, it)
            }
        }

    }
}