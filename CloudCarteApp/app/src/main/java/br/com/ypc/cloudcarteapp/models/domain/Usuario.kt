package br.com.ypc.cloudcarteapp.models.domain

import br.com.ypc.cloudcarteapp.models.valueobjects.Endereco
import java.util.*

/**
 * Created by caleb on 07/10/2017.
 */
data class Usuario (var id: String,
                    var nome: String,
                    var email: String,
                    var dataNascimento: Date? = null,
                    var telefone: String = "",
                    var endereco: Endereco = Endereco(),
                    var conquistas: MutableList<String> = mutableListOf())