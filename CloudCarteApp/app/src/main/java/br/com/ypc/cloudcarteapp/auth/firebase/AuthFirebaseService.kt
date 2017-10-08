package br.com.ypc.cloudcarteapp.auth.firebase

import br.com.ypc.cloudcarteapp.auth.interfaces.AuthService
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by caleb on 07/10/2017.
 */
class AuthFirebaseService : AuthService {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val fbAuth: LoginManager by lazy {  LoginManager.getInstance() }


    override fun getUserUid(): String? = auth.currentUser?.uid


    override fun logOut() {
        fbAuth.logOut()
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
                .addOnSuccessListener { successFn() }
                .addOnFailureListener { errorFn(it.toString()) }
    }

    override fun createUserWithEmailAndPassword(email: String, password: String, successFn: () -> Unit, errorFn: (error: String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { successFn() }
                .addOnFailureListener { errorFn(it.toString()) }
    }
}