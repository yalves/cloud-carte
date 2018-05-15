package br.com.ypc.cloudcarteapp.auth.firebase

import br.com.ypc.cloudcarteapp.auth.interfaces.AuthService
import br.com.ypc.cloudcarteapp.models.domain.Usuario
import br.com.ypc.cloudcarteapp.services.interfaces.UsuarioService
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.facebook.GraphRequest



/**
 * Created by caleb on 07/10/2017.
 */
class AuthFirebaseService(val usuarioService: UsuarioService) : AuthService {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val facebookAuth: LoginManager by lazy {  LoginManager.getInstance() }


    override fun getUserUid(): String? = auth.currentUser?.uid


    override fun logOut() {
        facebookAuth.logOut()
        auth.signOut()
    }

    override fun signInWithEmailAndPassword(email: String, password: String, successFn: () -> Unit, errorFn: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { successFn() }
                .addOnFailureListener { errorFn(it.toString()) }
    }

    override fun handleFacebookAccess(accessToken: AccessToken, successFn: () -> Unit, errorFn: (error: String) -> Unit) {
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
            auth.signInWithCredential(credential)
                    .addOnSuccessListener {
                        usuarioService.checkUsuarioExists(it.user.uid,
                                successFn = { userExists ->
                                    if (userExists)
                                        successFn()
                                    else
                                        usuarioService.cadastrarUsuarioPeloFacebook(it.user.uid, accessToken,
                                                successFn = successFn,
                                                errorFn = errorFn)
                                },
                                errorFn = errorFn)
                    }
                    .addOnFailureListener { errorFn(it.toString()) }
    }

    override fun createUser(usuario: Usuario, password: String, successFn: () -> Unit, errorFn: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(usuario.email, password)
                .addOnSuccessListener {
                    usuarioService.cadastrarUsuario(usuario,
                            successFn = successFn,
                            errorFn = errorFn)
                }
                .addOnFailureListener { errorFn(it.toString()) }
    }
}