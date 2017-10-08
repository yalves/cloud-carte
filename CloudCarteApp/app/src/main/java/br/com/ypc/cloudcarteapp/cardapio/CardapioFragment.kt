package br.com.ypc.cloudcarteapp.cardapio

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ypc.cloudcarteapp.R
import br.com.ypc.cloudcarteapp.extensions.inflate
import br.com.ypc.cloudcarteapp.home.HomeFragment
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.toast

/**
 * Created by caleb on 08/10/2017.
 */
class CardapioFragment : Fragment(), CardapioContract.View {
    override lateinit var presenter: CardapioContract.Presenter
    private var dialog: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            container?.inflate(R.layout.fragment_cardapio)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val option = activity.intent.extras[CardapioActivity.OPTION_PHOTO]
        when (option) {
            HomeFragment.CAMERA_VALUE_OPTION -> showCamera()
            HomeFragment.GALLERY_VALUE_OPTION -> showGallery()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_CANCELED) {
            activity.finish()
            return
        }

        val cardapioImage = getBitmap(requestCode, data)

        cardapioImage?.let {
            presenter.saveImage(it)
        }
    }

    override fun showCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        this.startActivityForResult(intent, HomeFragment.CAMERA_VALUE_OPTION)
    }

    override fun showGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        this.startActivityForResult(intent, HomeFragment.GALLERY_VALUE_OPTION)
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

    companion object {
        fun newInstance() = CardapioFragment()
    }
}