package br.com.ypc.cloudcarteapp.auth.interfaces

import br.com.ypc.cloudcarteapp.models.domain.Usuario
import com.facebook.AccessToken

/**
 * Created by caleb on 07/10/2017.
 */
interface AuthService {
    fun signInWithEmailAndPassword(email: String, password: String, successFn: () -> Unit = {}, errorFn: (String) -> Unit = {})
    fun handleFacebookAccess(accessToken: AccessToken, successFn: () -> Unit = {}, errorFn: (String) -> Unit = {})
    fun getUserUid(): String?
    fun logOut()
    fun createUser(usuario: Usuario, password: String, successFn: () -> Unit, errorFn: (String) -> Unit = {})
}