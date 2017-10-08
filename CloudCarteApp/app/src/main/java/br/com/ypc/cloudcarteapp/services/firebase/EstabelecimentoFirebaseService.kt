package br.com.ypc.cloudcarteapp.services.firebase

import android.graphics.Bitmap
import br.com.ypc.cloudcarteapp.models.domain.Estabelecimento
import br.com.ypc.cloudcarteapp.services.interfaces.AlbumService
import br.com.ypc.cloudcarteapp.services.interfaces.EstabelecimentoService
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by caleb on 08/10/2017.
 */
class EstabelecimentoFirebaseService(val albumService: AlbumService) : EstabelecimentoService {

    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

    override fun getEstabelecimentos(successFn: (List<Estabelecimento>) -> Unit, errorFn: (String) -> Unit, finallyFn: () -> Unit) {
        successFn(listOf())
        finallyFn()
    }

    override fun salvarEstabelecimentoECardapio(estabelecimento: Estabelecimento, imagemCardapio: Bitmap, successFn: (Estabelecimento) -> Unit, errorFn: (String) -> Unit, finallyFn: () -> Unit) {
        val estabelecimentosChildDatabase = database.reference.child("estabelecimentos")
        val estabelecimentoId = estabelecimentosChildDatabase.push().key

        estabelecimento.id = estabelecimentoId

        estabelecimentosChildDatabase.child(estabelecimentoId).setValue(estabelecimento)
                .addOnSuccessListener {
                    albumService.saveImage(estabelecimento, imagemCardapio,
                            successFn = {
                                successFn(estabelecimento)
                            },
                            errorFn = errorFn,
                            finallyFn = finallyFn)
                }
                .addOnFailureListener {
                    errorFn(it.toString())
                    finallyFn()
                }
    }
}