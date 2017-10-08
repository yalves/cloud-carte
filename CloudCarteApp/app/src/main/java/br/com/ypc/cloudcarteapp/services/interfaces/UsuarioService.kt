package br.com.ypc.cloudcarteapp.services.interfaces

import br.com.ypc.cloudcarteapp.models.domain.Usuario
import com.facebook.AccessToken

/**
 * Created by caleb on 08/10/2017.
 */
interface UsuarioService {
    fun cadastrarUsuario(usuario: Usuario, successFn: () -> Unit = {}, errorFn: (String) -> Unit = {})
    fun checkUsuarioExists(uid: String, successFn: (Boolean) -> Unit, errorFn: (String) -> Unit)
    fun cadastrarUsuarioPeloFacebook(uid: String, accessToken: AccessToken, successFn: () -> Unit, errorFn: (String) -> Unit)
}