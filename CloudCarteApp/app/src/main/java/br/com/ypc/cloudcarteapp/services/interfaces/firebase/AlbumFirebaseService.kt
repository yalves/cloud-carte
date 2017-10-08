package br.com.ypc.cloudcarteapp.services.interfaces.firebase

import android.graphics.Bitmap
import br.com.ypc.cloudcarteapp.auth.interfaces.AuthService
import br.com.ypc.cloudcarteapp.extensions.toString
import br.com.ypc.cloudcarteapp.metadata.SituacaoEnum
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
class AlbumFirebaseService(val authService: AuthService) : AlbumService {

    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    private val storage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }

    override fun getImageUploadInfoList(showPublic: Boolean, successFn: (List<Album>) -> Unit, errorFn: (String) -> Unit, finallyFn: () -> Unit) {
        val usersChildDatabase = database.reference.child("users")

        val query = if (showPublic)
            usersChildDatabase.orderByChild("public").equalTo(true)
        else
            usersChildDatabase.orderByChild("userUid").equalTo(authService.getUserUid()!!)

        query.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val list = dataSnapshot?.children?.map { it.getValue(Album::class.java)!! }
                successFn(list ?: listOf())
                finallyFn()
            }

            override fun onCancelled(error: DatabaseError?) {
                errorFn(error.toString())
                finallyFn()
            }
        })
    }

    override fun saveImage(bitmap: Bitmap, successFn: (Album) -> Unit, errorFn: (String) -> Unit, finallyFn: () -> Unit) {

        val albunsChildDatabase = database.reference.child("albuns")

        val storageRefUserChild = storage.reference.child("albuns")

        val currentDate = Calendar.getInstance().time

        val imageUploadInfoId = albunsChildDatabase.push().key
        val nameFile = generateImageNameFile(imageUploadInfoId)
        val storageRefFile = storageRefUserChild.child("$imageUploadInfoId/$nameFile")

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val data = baos.toByteArray()

        storageRefFile.putBytes(data)
                .addOnSuccessListener {
                    val nomeAlbum = currentDate.toString("yyyy-MM-dd")
                    val imageUploadInfo = Album(
                            id = imageUploadInfoId,
                            nome = nomeAlbum,
                            nomeArquivo = nameFile,
                            itens = generateAlbumItemList(nomeAlbum, it)
                    )

                    albunsChildDatabase.child(imageUploadInfoId).setValue(imageUploadInfo)

                    successFn(imageUploadInfo)
                    finallyFn()
                }
                .addOnFailureListener {
                    errorFn(it.toString())
                    finallyFn()
                }
    }

    override fun generateImageNameFile(albumId: String): String {
        val currentDate = Calendar.getInstance().time
        val date = currentDate.toString("yyMMddHHmmssZ")
        return "${albumId}_$date.jpg"
    }

    override fun getStorageReferenceToSave(imageUploadInfo: Album, userUid: String): StorageReference =
            storage.reference.child("album")

    private fun generateAlbumItemList(nomeAlbum: String, it: UploadTask.TaskSnapshot): List<AlbumItem> {
        return listOf(AlbumItem(nome = nomeAlbum,
                caminhoFoto = it.downloadUrl?.toString() ?: "",
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