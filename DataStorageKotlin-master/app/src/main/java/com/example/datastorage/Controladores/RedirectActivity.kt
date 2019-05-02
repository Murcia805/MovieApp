package com.example.datastorage.Controladores

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.datastorage.R

class RedirectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redirect)
    }

    fun redirectUsers(view: View){
        val intent = Intent(this, UsersListActivity::class.java)
        startActivity(intent)
    }

    fun redirectMovies(view: View){
        val intent = Intent(this, MovieActivity::class.java)
        startActivity(intent)
    }

}