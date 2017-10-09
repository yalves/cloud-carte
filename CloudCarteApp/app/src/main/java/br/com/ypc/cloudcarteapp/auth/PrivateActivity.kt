package br.com.ypc.cloudcarteapp.auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.ypc.cloudcarteapp.auth.firebase.AuthStateFirebaseListener
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by caleb on 07/10/2017.
 */
open class PrivateActivity :  AppCompatActivity() {
    private val mAuthStateListener: FirebaseAuth.AuthStateListener = AuthStateFirebaseListener(this)
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    protected var isAuthenticated: Boolean = auth.currentUser != null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth.addAuthStateListener(mAuthStateListener)
    }
}