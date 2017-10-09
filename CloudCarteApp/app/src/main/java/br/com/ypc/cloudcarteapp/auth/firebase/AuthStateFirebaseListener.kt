package br.com.ypc.cloudcarteapp.auth.firebase

import android.app.Activity
import br.com.ypc.cloudcarteapp.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.startActivity

/**
 * Created by caleb on 07/10/2017.
 */
class AuthStateFirebaseListener(val activity: Activity) : FirebaseAuth.AuthStateListener {
    override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
        if (firebaseAuth.currentUser != null)
            return

        activity.startActivity<LoginActivity>()
        activity.finish()
    }
}