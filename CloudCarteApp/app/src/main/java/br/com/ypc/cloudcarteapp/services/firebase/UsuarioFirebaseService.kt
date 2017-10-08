package br.com.ypc.cloudcarteapp.services.firebase

import android.os.Bundle
import br.com.ypc.cloudcarteapp.models.domain.Usuario
import br.com.ypc.cloudcarteapp.services.interfaces.UsuarioService
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONObject

/**
 * Created by caleb on 08/10/2017.
 */
class UsuarioFirebaseService : UsuarioService {

    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

    override fun checkUsuarioExists(uid: String, successFn: (Boolean) -> Unit, errorFn: (String) -> Unit) {
        val usersChildDatabase = database.reference.child("users/$uid")

        usersChildDatabase.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                successFn(dataSnapshot?.exists() ?: false)
            }

            override fun onCancelled(error: DatabaseError?) {
                errorFn(error.toString())
            }
        })
    }

    override fun cadastrarUsuarioPeloFacebook(uid: String, accessToken: AccessToken, successFn: () -> Unit, errorFn: (String) -> Unit) {
        val request = GraphRequest.newMeRequest(accessToken) { jsonObject, response ->
            val usuario = mapUsuarioByFacebook(uid, jsonObject)
            cadastrarUsuario(usuario,
                    successFn = successFn,
                    errorFn = {
                        errorFn(it)
                    })
        }

        val parameters = Bundle()
        parameters.putString("fields", "id,name,email")
        request.parameters = parameters
        request.executeAsync()
    }

    private fun mapUsuarioByFacebook(uid: String, jsonObject: JSONObject): Usuario {
        return Usuario(id = uid,
                nome = jsonObject.getString("name"),
                email = jsonObject.getString("email"))
    }

    override fun cadastrarUsuario(usuario: Usuario, successFn: () -> Unit, errorFn: (error: String) -> Unit) {
        val usersChildDatabase = database.reference.child("users")

        if (usuario.id.isEmpty())
            usuario.id = usersChildDatabase.push().key

        usersChildDatabase.child(usuario.id).setValue(usuario)
                .addOnSuccessListener {
                    successFn()
                }
                .addOnFailureListener {
                    errorFn(it.toString())
                }
    }
}