package br.com.ypc.cloudcarteapp.services.interfaces

import android.graphics.Bitmap
import br.com.ypc.cloudcarteapp.models.domain.Estabelecimento
import br.com.ypc.cloudcarteapp.models.valueobjects.Album
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

/**
 * Created by caleb on 08/10/2017.
 */
interface AlbumService {

    companion object {
        val SHOW_PUBLIC_PHOTOS_FALSE: Boolean = false
        val SHOW_PUBLIC_PHOTOS_TRUE: Boolean = true
    }

    //fun changePhotoVisibility(imageUploadInfo: Album, successFn: (Album) -> Unit = {}, errorFn: (String) -> Unit = {}, finallyFn: () -> Unit = {})
    fun getStorageReferenceToSave(imageUploadInfo: Album, userUid: String): StorageReference
    fun generateImageNameFile(albumId: String): String
    fun saveImage(estabelecimento: Estabelecimento, bitmap: Bitmap, successFn: (Album) -> Unit = {}, errorFn: (String) -> Unit = {}, finallyFn: () -> Unit = {})
    fun getImageUploadInfoList(showPublic: Boolean, successFn: (List<Album>) -> Unit = {}, errorFn: (String) -> Unit = {}, finallyFn: () -> Unit = {})
}