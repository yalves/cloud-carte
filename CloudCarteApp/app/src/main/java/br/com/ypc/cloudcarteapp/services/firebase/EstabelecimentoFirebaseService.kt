package br.com.ypc.cloudcarteapp.services.firebase

import android.graphics.Bitmap
import br.com.ypc.cloudcarteapp.models.domain.Estabelecimento
import br.com.ypc.cloudcarteapp.services.interfaces.AlbumService
import br.com.ypc.cloudcarteapp.services.interfaces.EstabelecimentoService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by caleb on 08/10/2017.
 */
class EstabelecimentoFirebaseService(val albumService: AlbumService, val mapAlbumFirebaseService: MapAlbumFirebaseService) : EstabelecimentoService {

    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

    override fun getEstabelecimentosByNome(nome: String, successFn: (List<Estabelecimento>) -> Unit, errorFn: (String) -> Unit, finallyFn: () -> Unit) {
        val usersChildDatabase = database.reference.child("estabelecimentos")

        val query = usersChildDatabase.orderByChild("nome")
                .startAt(nome)
                .endAt(nome + "\uf8ff")

        query.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val list = dataSnapshot?.children?.map { mapAlbumFirebaseService.mapEstabelecimento(it) }
                successFn(list ?: listOf())
                finallyFn()
            }

            override fun onCancelled(error: DatabaseError?) {
                errorFn(error.toString())
                finallyFn()
            }
        })
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