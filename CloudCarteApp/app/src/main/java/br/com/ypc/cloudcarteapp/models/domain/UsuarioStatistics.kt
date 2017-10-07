package br.com.ypc.cloudcarteapp.models.domain

/**
 * Created by caleb on 07/10/2017.
 */
data class UsuarioStatistics (var id: String,
                              var pontuacao: Int,
                              var upload: Int,
                              var quantidadeVotos: Int,
                              var quantidadeEndereco: Int,
                              var indicacoesFeitas: Int,
                              var compartilhamentos: Int,
                              var quantidadeBookmarks: Int)