package br.com.ypc.cloudcarteapp.services.firebase

import br.com.ypc.cloudcarteapp.metadata.SituacaoEnum
import br.com.ypc.cloudcarteapp.models.valueobjects.Album
import br.com.ypc.cloudcarteapp.models.valueobjects.AlbumItem
import br.com.ypc.cloudcarteapp.models.valueobjects.Avaliacao
import br.com.ypc.cloudcarteapp.models.valueobjects.Comentario
import com.google.firebase.database.DataSnapshot
import java.util.*

/**
 * Created by caleb on 08/10/2017.
 */
class MapAlbumFirebaseService {

    fun mapAlbum(albumSnapshot: DataSnapshot): Album {

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

    fun mapAlbumItem(albumItem: DataSnapshot): AlbumItem {

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

    fun mapComentarios(albumItem: DataSnapshot) : Comentario {
        val usuarioId = albumItem.child("usuarioId").getValue(String::class.java) ?: ""
        val conteudo = albumItem.child("conteudo").getValue(String::class.java) ?: ""
        val dataPostagem = albumItem.child("dataPostagem").getValue(Date::class.java) ?: Date()

        return Comentario(usuarioId = usuarioId,
                conteudo = conteudo,
                dataPostagem = dataPostagem)
    }

    fun mapAvaliacoes(albumItem: DataSnapshot) : Avaliacao {
        val usuarioId = albumItem.child("usuarioId").getValue(String::class.java) ?: ""
        val like = albumItem.child("like").getValue(Boolean::class.java) ?: true

        return Avaliacao(usuarioId = usuarioId,
                like = like)
    }
}