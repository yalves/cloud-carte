package br.com.ypc.cloudcarteapp.models.domain

import br.com.ypc.cloudcarteapp.metadata.VisibilidadeEnum
import br.com.ypc.cloudcarteapp.models.valueobjects.BookmarkItem

/**
 * Created by caleb on 07/10/2017.
 */
data class Bookmark (var id: String,
                     var nome: String,
                     var visibilidade: VisibilidadeEnum,
                     var itens: List<BookmarkItem>)