package com.example.datastorage.Controladores

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.datastorage.Modelos.User
import com.example.datastorage.R
import com.example.datastorage.Servicios.LoginServices
import com.example.datastorage.Servicios.UserDBServices

class RegisterActivity : AppCompatActivity() {

    private lateinit var loginServices : LoginServices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        loginServices= LoginServices(this)
    }

    fun registrar(view: View)
    {
        val user = findViewById<TextView>(R.id.nombre);
        val email = findViewById<TextView>(R.id.correo);
        val password = findViewById<TextView>(R.id.contrasena);
        val edad = findViewById<TextView>(R.id.edad);
        val usuarioNuevo = User(null, user.text.toString(), email.text.toString(), edad.text.toString().toInt(), password.text.toString())


        if(this.loginServices.verifyUser(usuarioNuevo) == false){
            UserDBServices(this).saveUser(usuarioNuevo)
            Toast.makeText(this, "Operacion Exitosa",  Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Usuario Existente, por favor use otro usuario o correo",  Toast.LENGTH_SHORT).show()
        }


    }

    fun goMainActivity(view: View)
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
