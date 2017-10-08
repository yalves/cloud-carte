package br.com.ypc.cloudcarteapp.models.valueobjects

/**
 * Created by caleb on 07/10/2017.
 */
data class Album (var id: String,
                  var nome: String,
                  var itens: List<AlbumItem>,
                  var nomeArquivo: String,
                  var estabelecimentoId: String,
                  var userId: String)