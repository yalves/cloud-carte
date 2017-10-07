package br.com.ypc.cloudcarteapp.models.valueobjects

import br.com.ypc.cloudcarteapp.metadata.SituacaoEnum

/**
 * Created by caleb on 07/10/2017.
 */
data class AlbumItem (var nome: String,
                      var caminhoFoto: String,
                      var upload: String,
                      var comentarios: List<Comentario>,
                      var likes: Int,
                      var deslikes: Int,
                      var situacao: SituacaoEnum,
                      var avaliacoes: List<Avaliacao>)
