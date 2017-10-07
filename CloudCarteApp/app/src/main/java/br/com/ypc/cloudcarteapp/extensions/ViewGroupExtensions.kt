package br.com.ypc.cloudcarteapp.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by caleb on 07/10/2017.
 */
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View? =
        LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)