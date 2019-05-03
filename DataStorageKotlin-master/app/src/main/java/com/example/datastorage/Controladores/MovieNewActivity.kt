package com.example.datastorage.Controladores

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.datastorage.Modelos.MovieDataResponse
import com.example.datastorage.Modelos.Pelicula
import com.example.datastorage.R
import com.example.datastorage.Servicios.MovieDBServices

class MovieNewActivity : AppCompatActivity() {

    companion object {
        const val ID_MOVIE = "total_id"
        const val ADULT_MOVIE = "total_adult"
        const val IMAGE_MOVIE = "total_image"
        const val NAME_MOVIE = "total_name"
        const val DESCRIPTION_MOVIE = "total_descripcion"
        const val DATE_MOVIE = "total_date"
        const val POPULAR_MOVIE = "total_popularidad"
        const val VOTES_AVERAGE_MOVIE = "total_votes_average"
        const val VOTES_COUNT_MOVIE = "total_votes_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        showProfile()
    }

    fun showProfile()
    {
        val imgMovie = 0//intent.getIntExtra(IMAGE_MOVIE, 0)
        val nombMovie = intent.getStringExtra(NAME_MOVIE)
        val descMovie = intent.getStringExtra(DESCRIPTION_MOVIE)
        val dateMovie = intent.getStringExtra(DATE_MOVIE)
        val popuMovie = intent.getIntExtra(POPULAR_MOVIE, 0)
        val votesMovie = intent.getIntExtra(VOTES_AVERAGE_MOVIE,0)

        findViewById<ImageView>(R.id.imageMovie).setImageResource(imgMovie)
        findViewById<TextView>(R.id.nameMovie).text = getString(R.string.nombre_Movie, nombMovie)
        findViewById<TextView>(R.id.descrMovie).text = getString(R.string.descripcion_Movie, descMovie)
        findViewById<TextView>(R.id.dateMovie).text = getString(R.string.date_Movie, dateMovie)
        findViewById<TextView>(R.id.popuMovie).text = getString(R.string.popularity_Movie, popuMovie)
        findViewById<TextView>(R.id.votosMovie).text = getString(R.string.vote_Movie, votesMovie)
    }

    fun anadirPelicula(view: View){
        val pel = Pelicula(MovieDataResponse(
            intent.getIntExtra(ID_MOVIE, 0),
            intent.getStringExtra(ADULT_MOVIE),
            intent.getStringExtra(DESCRIPTION_MOVIE),
            intent.getIntExtra(POPULAR_MOVIE, 0),
            intent.getStringExtra(IMAGE_MOVIE),
            intent.getStringExtra(DATE_MOVIE),
            intent.getStringExtra(NAME_MOVIE),
            intent.getIntExtra(VOTES_AVERAGE_MOVIE,0),
            intent.getIntExtra(VOTES_COUNT_MOVIE,0)
        ))

        MovieDBServices(this).savePelicula(pel)
        Toast.makeText(this, "Operacion Exitosa",  Toast.LENGTH_SHORT).show()
        MovieDBServices(this).consultMovies()
    }

    fun goBack(view: View)
    {
        val intent = Intent(this, AddMovieActivity::class.java)
        startActivity(intent)
    }
}
