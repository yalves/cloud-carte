package br.com.ypc.cloudcarteapp.cardapio.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.ypc.cloudcarteapp.R
import br.com.ypc.cloudcarteapp.extensions.inflate
import br.com.ypc.cloudcarteapp.models.domain.Estabelecimento

/**
 * Created by caleb on 08/10/2017.
 */
class RestauranteRecyclerViewAdapter(val context: Context, estabelecimentos: List<Estabelecimento>, val listener: EstabelecimentoClickListener? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var estabelecimentos: List<Estabelecimento> = estabelecimentos
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = parent.inflate(R.layout.album_item)
        return GalleryViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val galleryHolder = holder as GalleryViewHolder
        val item = estabelecimentos[position]
        galleryHolder.bind(item, listener)
    }

    override fun getItemCount(): Int = estabelecimentos.size

    internal inner class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.ImageNameTextView)
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
        var textVisibility: TextView = itemView.findViewById(R.id.text_visibility)

        fun bind(item: Estabelecimento, listener: EstabelecimentoClickListener? = null) {
            textView.text = item.nome
            textVisibility.text = ""
            //Glide.with(context).load(item.url).into(imageView)
            itemView.setOnClickListener {
                listener?.onItemClick(item)
            }
        }

    }
}