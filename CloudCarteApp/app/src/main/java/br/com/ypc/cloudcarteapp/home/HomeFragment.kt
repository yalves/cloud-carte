package br.com.ypc.cloudcarteapp.home

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
import br.com.ypc.cloudcarteapp.cardapio.CardapioActivity
import br.com.ypc.cloudcarteapp.extensions.inflate
import br.com.ypc.cloudcarteapp.home.adapter.HomeRecyclerViewAdapter
import br.com.ypc.cloudcarteapp.models.valueobjects.Album
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.selector
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * Created by caleb on 07/10/2017.
 */
class HomeFragment : Fragment(), HomeContract.View {

    override lateinit var presenter: HomeContract.Presenter
    private lateinit var adapter: HomeRecyclerViewAdapter
    private var dialog: ProgressDialog? = null
    private var cardapioImage:Bitmap? = null

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            container?.inflate(R.layout.fragment_home)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadEvents()
        loadAdapter()
        loadRecyclerView()
    }

    override fun showAlbuns(albuns: List<Album>) {
        adapter.albuns = albuns
    }

    override fun showLoadAlbunsError(error: String) {
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

    override fun showOptionPhotoDialog() {
        val options = listOf("Gallery", "Camera")
        selector("Select", options, { _, i ->
            when(i) {
                0 -> showGallery()
                1 -> showCamera()
            }
        })
    }

    override fun showCamera() {
        startActivity<CardapioActivity>(CardapioActivity.OPTION_PHOTO to Companion.CAMERA_VALUE_OPTION)
    }

    override fun showGallery() {
        startActivity<CardapioActivity>(CardapioActivity.OPTION_PHOTO to Companion.GALLERY_VALUE_OPTION)
    }

    override fun showPhotoSaved() {
        toast(R.string.photo_saved_successful)
    }

    override fun showSavePhotoError(error: String) {
        toast("Error: $error")
    }

    override fun showLoadPhotosError(error: String) {
        if (activity != null)
            toast("Error: $error")
    }


    private fun loadEvents() {
        button_logout.setOnClickListener {
            presenter.logOut()
        }
        button_photo.setOnClickListener {
            showOptionPhotoDialog()
        }
    }

    private fun loadAdapter() {
        adapter = HomeRecyclerViewAdapter(activity, listOf())
    }

    private fun loadRecyclerView() {
        recyclerView_photos.setHasFixedSize(true)
        recyclerView_photos.layoutManager = LinearLayoutManager(activity)
        recyclerView_photos.adapter = adapter
    }

    companion object {
        fun newInstance() = HomeFragment()

        val CAMERA_VALUE_OPTION: Int = 1
        val GALLERY_VALUE_OPTION: Int = 2
    }
}