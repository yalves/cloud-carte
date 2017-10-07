package br.com.ypc.cloudcarteapp.extensions

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 * Created by caleb on 07/10/2017.
 */
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, @IdRes frameIdRes: Int) {
    supportFragmentManager.transact {
        replace(frameIdRes, fragment)
    }
}