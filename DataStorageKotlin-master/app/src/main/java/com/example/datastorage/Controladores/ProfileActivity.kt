package com.example.datastorage.Controladores

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.datastorage.R

class ProfileActivity : AppCompatActivity() {

    companion object {
        const val IMAGE_PROFILE = "total_image"
        const val NAME_PROFILE = "total_name"
        const val AGE_PROFILE = "total_age"
        const val EMAIL_PROFILE = "total_email"
        const val PASSW_PROFILE = "total_password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        showProfile()
    }

    fun showProfile()
    {
        val imgProf = intent.getIntExtra(IMAGE_PROFILE, 0)
        val nombProf = intent.getStringExtra(NAME_PROFILE)
        val ageProf = intent.getIntExtra(AGE_PROFILE, 0)
        val emailProf = intent.getStringExtra(EMAIL_PROFILE)
        val passwProf = intent.getStringExtra(PASSW_PROFILE)

        findViewById<ImageView>(R.id.imageProfile).setImageResource(imgProf)
        findViewById<TextView>(R.id.nameProfile).text = getString(R.string.nombre_Prof, nombProf)
        findViewById<TextView>(R.id.ageProfile).text = getString(R.string.edad_Prof, ageProf)
        findViewById<TextView>(R.id.emailProfile).text = getString(R.string.correo_Prof, emailProf)
        findViewById<TextView>(R.id.passwordProfile).text = getString(R.string.contra_Prof, passwProf)
    }

    fun goBack(view: View)
    {
        val intent = Intent(this, UsersListActivity::class.java)
        startActivity(intent)
    }
}
