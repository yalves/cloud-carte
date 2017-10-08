package br.com.ypc.cloudcarteapp.models.valueobjects

import br.com.ypc.cloudcarteapp.metadata.SituacaoEnum

/**
 * Created by caleb on 07/10/2017.
 */
data class AlbumItem (var nome: String,
                      var caminhoFoto: String,
                      var upload: String = "",
                      var comentarios: List<Comentario> = listOf(),
                      var likes: Int = 0,
                      var deslikes: Int = 0,
                      var situacao: SituacaoEnum,
                      var avaliacoes: List<Avaliacao> = listOf())
