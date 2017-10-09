package br.com.ypc.cloudcarteapp.models.domain

import br.com.ypc.cloudcarteapp.models.valueobjects.Comentario

/**
 * Created by caleb on 07/10/2017.
 */
data class Estabelecimento (var id: String,
                            var nome: String,
                            var descricao: String,
                            var comentarios: MutableList<Comentario> = mutableListOf())