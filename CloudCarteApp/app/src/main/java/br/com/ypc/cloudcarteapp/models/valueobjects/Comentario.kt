package br.com.ypc.cloudcarteapp.models.valueobjects

import java.util.*

/**
 * Created by caleb on 07/10/2017.
 */
data class Comentario (var usuarioId: String,
                       var conteudo: String,
                       var dataPostagem: Date)