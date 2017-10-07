package br.com.ypc.cloudcarteapp.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import br.com.ypc.cloudcarteapp.R
import br.com.ypc.cloudcarteapp.extensions.replaceFragmentInActivity
import br.com.ypc.cloudcarteapp.utils.DependencyInjector

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
        val galleryView = fragment as HomeContract.View? ?: HomeFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

        DependencyInjector.getHomePresenter(galleryView)
    }
}
