package br.com.ypc.cloudcarteapp.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ypc.cloudcarteapp.R
import br.com.ypc.cloudcarteapp.extensions.inflate
import br.com.ypc.cloudcarteapp.models.valueobjects.Album

/**
 * Created by caleb on 07/10/2017.
 */
class HomeFragment : Fragment(), HomeContract.View {

    override lateinit var presenter: HomeContract.Presenter

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            container?.inflate(R.layout.fragment_home)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun showAlbuns(albuns: List<Album>) {
    }

    override fun showLoadAlbunsError(error: String) {
    }

    override fun startLoading() {
    }

    override fun finishLoading() {
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}