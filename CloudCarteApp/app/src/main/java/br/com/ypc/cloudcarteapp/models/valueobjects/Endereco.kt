package br.com.ypc.cloudcarteapp.models.valueobjects

/**
 * Created by caleb on 07/10/2017.
 */
data class Endereco (var logradouro: String,
                     var tipo: String,
                     var cep: String,
                     var complemento: String,
                     var numero: Int,
                     var referencia: String)