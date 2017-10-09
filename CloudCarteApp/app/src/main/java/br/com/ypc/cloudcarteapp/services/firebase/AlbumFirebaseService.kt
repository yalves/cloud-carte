package br.com.ypc.cloudcarteapp.services.firebase

import android.graphics.Bitmap
import br.com.ypc.cloudcarteapp.auth.interfaces.AuthService
import br.com.ypc.cloudcarteapp.extensions.toString
import br.com.ypc.cloudcarteapp.metadata.SituacaoEnum
import br.com.ypc.cloudcarteapp.models.domain.Estabelecimento
import br.com.ypc.cloudcarteapp.models.valueobjects.Album
import br.com.ypc.cloudcarteapp.models.valueobjects.AlbumItem
import br.com.ypc.cloudcarteapp.services.interfaces.AlbumService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.util.*


/**
 * Created by caleb on 08/10/2017.
 */
class AlbumFirebaseService(val authService: AuthService, val mapAlbumFirebaseService: MapAlbumFirebaseService) : AlbumService {

    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    private val storage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }

    override fun getImageUploadInfoList(showPublic: Boolean, successFn: (List<Album>) -> Unit, errorFn: (String) -> Unit, finallyFn: () -> Unit) {
        val usersChildDatabase = database.reference.child("albuns")

        usersChildDatabase.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val list = dataSnapshot?.children?.map { mapAlbumFirebaseService.mapAlbum(it) }
                successFn(list ?: listOf())
                finallyFn()
            }

            override fun onCancelled(error: DatabaseError?) {
                errorFn(error.toString())
                finallyFn()
            }
        })
    }

    override fun saveImage(estabelecimento: Estabelecimento, bitmap: Bitmap, successFn: (Album) -> Unit, errorFn: (String) -> Unit, finallyFn: () -> Unit) {

        val userUid = authService.getUserUid()
        if (userUid == null) {
            errorFn("User is null")
            finallyFn()
            return
        }

        val albunsChildDatabase = database.reference.child("albuns")
        val imageUploadInfoId = albunsChildDatabase.push().key

        val storageRefUserChild = storage.reference.child("albuns")
        val nameFile = generateImageNameFile(imageUploadInfoId)
        val storageRefFile = storageRefUserChild.child("$imageUploadInfoId/$nameFile")

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val data = baos.toByteArray()

        storageRefFile.putBytes(data)
                .addOnSuccessListener {

                    saveAlbumInDatabase(imageUploadInfoId, nameFile, estabelecimento.id, userUid, it,
                            successFn = { imageUploadInfo ->
                                successFn(imageUploadInfo)
                                finallyFn()
                            },
                            errorFn = {
                                storageRefFile.delete()
                                errorFn(it.toString())
                                finallyFn()
                            })
                }
                .addOnFailureListener {
                    errorFn(it.toString())
                    finallyFn()
                }
    }

//Old method
//    private fun addAlbumToUserAndEstabelecimento(userUid: String, estabelecimento: Estabelecimento, imageUploadInfo: Album, successFn: () -> Unit, errorFn: (Exception?) -> Unit) {
//        val userChildRef = database.getReference("users/$userUid")
//
//        userChildRef.addListenerForSingleValueEvent(object : ValueEventListener {
//
//            override fun onDataChange(dataSnapshot: DataSnapshot?) {
//                if (dataSnapshot == null) {
//                    errorFn(Exception("User is null"))
//                    return
//                }
//
//                val usuario = mapAlbumFirebaseService.mapUsuario(dataSnapshot)
////                usuario.albuns.add(imageUploadInfo.id)
////                estabelecimento.abuns.add(imageUploadInfo.id)
//
//                val childUpdates = HashMap<String, Any>().apply {
//                    put("estabelecimentos/${estabelecimento.id}", estabelecimento)
//                    put("users/${userUid}", usuario)
//                }
//
//                database.reference.updateChildren(childUpdates)
//                        .addOnSuccessListener {
//                            successFn()
//                        }
//                        .addOnFailureListener {
//                            errorFn(it)
//                        }
//            }
//
//            override fun onCancelled(error: DatabaseError?) {
//                errorFn(error?.toException() ?: Exception("Operation cancelled"))
//            }
//        })
//
//    }

    private fun saveAlbumInDatabase(imageUploadInfoId: String, nameFile: String, estabelecimentoId: String, userId: String, taskSnapshot: UploadTask.TaskSnapshot, successFn: (Album) -> Unit, errorFn: (Exception?) -> Unit) {
        val albunsChildDatabase = database.reference.child("albuns")

        val currentDate = Calendar.getInstance().time
        val nomeAlbum = currentDate.toString("yyyy-MM-dd")
        val imageUploadInfo = Album(
                id = imageUploadInfoId,
                nome = nomeAlbum,
                nomeArquivo = nameFile,
                estabelecimentoId = estabelecimentoId,
                userId = userId,
                itens = generateAlbumItemList(nomeAlbum, taskSnapshot)
        )

        albunsChildDatabase.child(imageUploadInfoId).setValue(imageUploadInfo)
                .addOnSuccessListener {
                    successFn(imageUploadInfo)
                }
                .addOnFailureListener {
                    errorFn(it)
                }
    }

    override fun generateImageNameFile(albumId: String): String {
        val currentDate = Calendar.getInstance().time
        val date = currentDate.toString("yyMMddHHmmssZ")
        return "${albumId}_$date.jpg"
    }

    override fun getStorageReferenceToSave(imageUploadInfo: Album, userUid: String): StorageReference =
            storage.reference.child("album")

    private fun generateAlbumItemList(nomeAlbum: String, taskSnapshot: UploadTask.TaskSnapshot): List<AlbumItem> {
        return listOf(AlbumItem(nome = nomeAlbum,
                caminhoFoto = taskSnapshot.downloadUrl?.toString() ?: "",
                situacao = SituacaoEnum.ATIVO))
    }

    /**
     * Future feature
     */
    /*override fun  changePhotoVisibility(imageUploadInfo: Album, successFn: (Album) -> Unit, errorFn: (String) -> Unit, finallyFn: () -> Unit) {

        moveImage(imageUploadInfo,
                successFn = {

                    imageUploadInfo.url = it.downloadUrl.toString()
                    val childUpdates = HashMap<String, Any>().apply {
                        put("users/${imageUploadInfo.key}", imageUploadInfo)
                    }

                    database.reference.updateChildren(childUpdates)

                    successFn(imageUploadInfo)
                    finallyFn()
                },
                errorFn = {
                    errorFn(it.toString())
                    finallyFn()
                })
    }

    fun moveImage(imageUploadInfo: Album, successFn: (UploadTask.TaskSnapshot) -> Unit, errorFn: (Exception?) -> Unit) {
        val userUid = authService.getUserUid() ?: return

        val imageReference = storage.getReferenceFromUrl(imageUploadInfo.url)
        val storageReferenceToSave = getStorageReferenceToSave(imageUploadInfo, userUid)

        var imageArray: ByteArray
        val ONE_MEGABYTE = 2048 * 2048
        imageReference.getBytes(ONE_MEGABYTE.toLong())
                .addOnSuccessListener {
                    imageArray = it
                    imageReference.delete()
                            .addOnSuccessListener {
                                storageReferenceToSave.child(imageUploadInfo.nameFile).putBytes(imageArray)
                                        .addOnSuccessListener { successFn(it) }
                                        .addOnFailureListener { errorFn(it) }
                            }
                            .addOnFailureListener { errorFn(it) }
                }.addOnFailureListener { errorFn(it) }
    }*/
}