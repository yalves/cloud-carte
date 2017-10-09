package br.com.ypc.cloudcarteapp.extensions

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

/**
 * Created by caleb on 07/10/2017.
 */
fun FragmentManager.transact(function: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        function()
    }.commit()
}