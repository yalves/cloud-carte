package br.com.ypc.cloudcarteapp.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by caleb on 07/10/2017.
 */
fun Date.toString(format: String): String = SimpleDateFormat(format).format(this)