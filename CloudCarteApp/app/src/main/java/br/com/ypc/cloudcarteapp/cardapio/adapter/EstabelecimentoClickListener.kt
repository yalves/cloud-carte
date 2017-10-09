package br.com.ypc.cloudcarteapp.cardapio.adapter

import br.com.ypc.cloudcarteapp.models.domain.Estabelecimento

/**
 * Created by caleb on 08/10/2017.
 */
interface EstabelecimentoClickListener {
    fun onItemClick(item: Estabelecimento)
}