package br.com.ypc.cloudcarteapp.services.interfaces

import android.graphics.Bitmap
import br.com.ypc.cloudcarteapp.models.domain.Estabelecimento

/**
 * Created by caleb on 08/10/2017.
 */
interface EstabelecimentoService {
    fun getEstabelecimentos(successFn: (List<Estabelecimento>) -> Unit = {}, errorFn: (String) -> Unit = {}, finallyFn: () -> Unit)
    fun salvarEstabelecimentoECardapio(estabelecimento: Estabelecimento, imagemCardapio: Bitmap, successFn: (Estabelecimento) -> Unit = {}, errorFn: (String) -> Unit = {}, finallyFn: () -> Unit = {})
}