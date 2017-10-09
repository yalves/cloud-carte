package br.com.ypc.cloudcarteapp.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.ypc.cloudcarteapp.R
import br.com.ypc.cloudcarteapp.extensions.inflate
import br.com.ypc.cloudcarteapp.models.valueobjects.Album
import com.bumptech.glide.Glide

/**
 * Created by caleb on 08/10/2017.
 */
class HomeRecyclerViewAdapter(val context: Context, albuns: List<Album>, val listener: AlbumClickListener? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var albuns: List<Album> = albuns
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    /**
     * CR - Create instance from GalleryViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = parent.inflate(R.layout.album_item)
        return GalleryViewHolder(view!!)
    }

    /**
     * CR - Bind each item in RecyclerView
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val galleryHolder = holder as GalleryViewHolder
        val item = albuns[position]
        galleryHolder.bind(item, listener)
    }

    /**
     * CR - Return items count in list
     */
    override fun getItemCount(): Int = albuns.size

    /**
     * CR - Inner class to show in gallery item layout in list
     */
    internal inner class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.ImageNameTextView)
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
        var textVisibility: TextView = itemView.findViewById(R.id.text_visibility)

        fun bind(item: Album, listener: AlbumClickListener? = null) {
            textView.text = item.nome
            textVisibility.text = ""
            //Glide.with(context).load(item.url).into(imageView)
            itemView.setOnClickListener {
                listener?.onItemClick(item)
            }
        }

    }
}