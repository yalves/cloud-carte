package br.com.ypc.cloudcarteapp.home.adapter

import br.com.ypc.cloudcarteapp.models.valueobjects.Album

/**
 * Created by caleb on 08/10/2017.
 */
interface AlbumClickListener {
    fun onItemClick(album: Album)
}