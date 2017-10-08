package br.com.ypc.cloudcarteapp.services.firebase

import android.graphics.Bitmap
import br.com.ypc.cloudcarteapp.auth.interfaces.AuthService
import br.com.ypc.cloudcarteapp.extensions.toString
import br.com.ypc.cloudcarteapp.metadata.SituacaoEnum
import br.com.ypc.cloudcarteapp.models.valueobjects.Album
import br.com.ypc.cloudcarteapp.models.valueobjects.AlbumItem
import br.com.ypc.cloudcarteapp.models.valueobjects.Avaliacao
import br.com.ypc.cloudcarteapp.models.valueobjects.Comentario
import br.com.ypc.cloudcarteapp.services.interfaces.AlbumService
import com.google.firebase.database.*
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
        val usersChildDatabase = database.reference.child("albuns")

        usersChildDatabase.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val list = dataSnapshot?.children?.map { mapAlbum(it) }
                successFn(list ?: listOf())
                finallyFn()
            }

            override fun onCancelled(error: DatabaseError?) {
                errorFn(error.toString())
                finallyFn()
            }
        })
    }

    private fun mapAlbum(albumSnapshot: DataSnapshot): Album {

        val id = albumSnapshot.child("id").getValue(String::class.java) ?: ""
        val itens = albumSnapshot.child("itens").children.map {
            mapAlbumItem(it)
        }

        val nome = albumSnapshot.child("nome").getValue(String::class.java) ?: ""
        val nomeArquivo = albumSnapshot.child("nomeArquivo").getValue(String::class.java) ?: ""

        return Album(id = id,
                itens = itens,
                nome = nome,
                nomeArquivo = nomeArquivo)
    }

    private fun mapAlbumItem(albumItem: DataSnapshot): AlbumItem {

        val nome = albumItem.child("nome").getValue(String::class.java) ?: ""
        val situacao = albumItem.child("situacao").getValue(SituacaoEnum::class.java) ?: SituacaoEnum.ATIVO
        val caminhoFoto = albumItem.child("caminhoFoto").getValue(String::class.java) ?: ""
        val avaliacoes = albumItem.child("avaliacoes").children.map { mapAvaliacoes(it) }
        val comentarios = albumItem.child("comentarios").children.map { mapComentarios(it) }
        val deslikes = albumItem.child("deslikes").getValue(Int::class.java) ?: 0
        val likes = albumItem.child("likes").getValue(Int::class.java) ?: 0
        val upload = albumItem.child("upload").getValue(String::class.java) ?: ""

        return AlbumItem(nome = nome,
                situacao = situacao,
                caminhoFoto = caminhoFoto,
                avaliacoes = avaliacoes,
                comentarios = comentarios,
                deslikes = deslikes,
                likes = likes,
                upload = upload)
    }

    private fun mapComentarios(albumItem: DataSnapshot) : Comentario {
        return Comentario(usuarioId = "",
                conteudo = "",
                dataPostagem = Date())
    }

    private fun mapAvaliacoes(albumItem: DataSnapshot) : Avaliacao {
        return Avaliacao(usuarioId = "",
                like = true)
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